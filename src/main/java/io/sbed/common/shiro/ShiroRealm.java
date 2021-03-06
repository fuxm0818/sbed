package io.sbed.common.shiro;

import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.sbed.common.Constant;
import io.sbed.common.utils.JWTUtil;
import io.sbed.modules.sys.entity.SysUser;
import io.sbed.modules.sys.entity.SysUserActive;
import io.sbed.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Description: 身份校验核心类 <br>
 * Copyright:DATANG SOFTWARE CO.LTD<br>
 *
 * @author fuxiangming
 * @date 2018/6/14 下午4:09
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    private static final Log log = LogFactory.getLog(ShiroRealm.class);

    @Autowired
    private SysUserService sysUserService;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 认证信息(身份验证)
     * Authentication 是用来验证用户身份
     *
     * @param auth
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        log.info("认证配置-->MyShiroRealm.doGetAuthenticationInfo()");
        JWTToken jwtToken = (JWTToken) auth;
        SysUserActive sysUserActive = this.toLogin(auth);
        SysUser user = sysUserActive.getSysUser();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(sysUserActive, sysUserActive.getSysUser().getPassword(), getName());
        return info;
    }

    private SysUserActive toLogin(AuthenticationToken auth) {
        String username = (String) auth.getPrincipal();
        if (StringUtils.isBlank(username)) {
            throw new IncorrectCredentialsException("用户名为空");
        }
        String password = (String) auth.getCredentials();
        if (StringUtils.isBlank(password)) {
            throw new IncorrectCredentialsException("密码为空");
        }
        //用户信息
        SysUser user = sysUserService.queryByUserName(username);

        //账号不存在
        if (user == null) {
            throw new UnknownAccountException();
        }

        //账号锁定
        if (Constant.UserStatus.DISABLE.getValue() == user.getStatus()) {
            throw new LockedAccountException();
        }

        //生成token
        String token = JWTUtil.sign(user.getUsername() + "", user.getSalt() + "");

        //保存到数据库redis
        SysUserActive sysUserActive = new SysUserActive();
        sysUserActive.setToken(token);
        //最新活动时间
//        sysUserActive.setLastActiveTime(System.currentTimeMillis());
        sysUserActive.setUsername(username);
        sysUserActive.setSysUser(user);
//        RedisUtils.set(Constant.prefix.SYSUSER_USERNAME + user.getUsername(), sysUserActive);

        //用户登录后,清除用户缓存,以便重新加载用户权限
        clearAuthorizationInfoCache(user);

        return sysUserActive;
    }

    /**
     * 此方法调用hasRole,hasPermission的时候才会进行回调.
     * <p>
     * 权限信息.(授权):
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     * （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        /*
         * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行，
         * 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
         * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了，
         * 缓存过期之后会再次执行。
         */
        log.info("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        try {
            SysUser user = ((SysUserActive) principals.getPrimaryPrincipal()).getSysUser();
            Long userId = user.getId();

            // 下面的可以使用缓存提升速度
            //用户权限列表
            Set<String> permsSet = sysUserService.getUserPermissions(userId);
            info.setStringPermissions(permsSet);
            return info;
        } catch (Exception ex) {
            log.error(ex);
        }

        return info;

    }

    /**
     * 清除指定用户的权限缓存
     *
     * @param user
     */
    public void clearAuthorizationInfoCache(SysUser user) {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        //key必须是String类型，参考ShiroRedisCache类
        cache.remove(user.getUsername() + "");
    }

    public void clearCached(String username) {
        List<String> list = new ArrayList<String>(1);
        list.add(username);
        this.clearCached(list);
    }


    // 清除缓存(修改权限后调用此方法)
    public void clearCached(List<String> usernames) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection();
        for (String username : usernames) {
            principals.add(username, getName());
        }
        super.clearCache(principals);
    }

    @Override
    protected Object getAuthenticationCacheKey(AuthenticationToken token) {
        if (null == token) {
            return null;
        }
        JWTToken jwtToken = (JWTToken) token;
        if (jwtToken.isLoginRequest() && StringUtils.isNotBlank(jwtToken.getUsername())) {
            Cache<Object, AuthenticationInfo> cache = getAuthenticationCache();
            SimpleAuthenticationInfo info = (SimpleAuthenticationInfo) cache.get(jwtToken.getUsername());
            if (null != info) {
                SysUserActive sysUserActive = (SysUserActive) info.getPrincipals().getPrimaryPrincipal();
                if (null != sysUserActive) {
                    cache.remove(jwtToken.getUsername());
                }
            }
            //登录，直接返回用户名
            return token.getPrincipal();
        } else {
            //非登录，判断token有效性
            String tokenInHeader = ((JWTToken) token).getToken();
            if (StringUtils.isBlank(tokenInHeader)) {
                throw new InvalidClaimException("token无效异常，空");
            }
            String usernameInToken = JWTUtil.getUsername(jwtToken.getToken());
            Cache<Object, AuthenticationInfo> cache = getAuthenticationCache();
            SimpleAuthenticationInfo info = (SimpleAuthenticationInfo) cache.get(usernameInToken);
            if (null != info) {
                SysUserActive sysUserActive = (SysUserActive) info.getPrincipals().getPrimaryPrincipal();
                if (null == sysUserActive || StringUtils.isBlank(sysUserActive.getToken()) || !tokenInHeader.equalsIgnoreCase(sysUserActive.getToken())) {
                    throw new InvalidClaimException("token无效异常");
                } else {
                    // 判断token过期
                    if (!JWTUtil.verifyExpire(tokenInHeader, sysUserActive.getSysUser().getSalt())) {
                        throw new ExpiredCredentialsException("token过期");
                    }
                    if (!JWTUtil.verify(tokenInHeader, sysUserActive.getSysUser().getUsername(), sysUserActive.getSysUser().getSalt())) {
                        throw new JWTVerificationException("token校验异常");
                    }
                }
            }
            return token.getPrincipal();
        }
    }
}
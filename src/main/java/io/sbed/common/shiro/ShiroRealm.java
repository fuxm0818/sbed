package io.sbed.common.shiro;

/**
 * Description: 身份校验核心类 <br>
 * Copyright:DATANG SOFTWARE CO.LTD<br>
 *
 * @author fuxiangming
 * @date 2018/6/14 下午4:09
 */

import io.sbed.common.Constant;
import io.sbed.common.cache.RedisUtils;
import io.sbed.common.utils.JWTUtil;
import io.sbed.modules.sys.entity.SysUser;
import io.sbed.modules.sys.entity.SysUserActive;
import io.sbed.modules.sys.service.SysUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ShiroRealm extends AuthorizingRealm {

    private static final Log _logger = LogFactory.getLog(ShiroRealm.class);

    @Autowired
    private SysUserService sysUserService;

//    /**
//     * 必须重写此方法，不然Shiro会报错
//     */
//    @Override
//    public boolean supports(AuthenticationToken token) {
//        return token instanceof ShiroToken;
//    }

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
        _logger.info("认证配置-->MyShiroRealm.doGetAuthenticationInfo()");
//
//        String token = (String) auth.getCredentials();
//        String username = JWTUtil.getUsername(token);
//
//        //通过username从数据库中查找 ManagerInfo对象
//        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
//        SysUser user = sysUserService.queryByUserName(username);
//        if (user == null || !JWTUtil.verify(token, username, user.getPassword())) {
//            throw new AuthenticationException("token无效，请重新登录");
//        }
//
//        //账号锁定
//        if (Constant.UserStatus.DISABLE.getValue() == user.getStatus()) {
//            throw new LockedAccountException("账号已被锁定,请联系管理员");
//        }



//        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)auth;

        String username = (String)auth.getPrincipal();
        //用户信息
        SysUser user = sysUserService.queryByUserName(username);

        //账号不存在
        if (user == null) {
            throw new UnknownAccountException();
        }

//        //密码错误,使用密码当做加密的盐
//        if (!user.getPassword().equals(new Sha256Hash(auth.getCredentials(), user.getSalt()).toHex())) {
//            throw new IncorrectCredentialsException();
//        }

        //账号锁定
        if (Constant.UserStatus.DISABLE.getValue() == user.getStatus()) {
            throw new LockedAccountException();
        }

        //生成token
        String token = JWTUtil.sign(user.getUsername() + "", user.getPassword() + "");

        //保存到数据库redis
        SysUserActive sysUserActive = new SysUserActive();
        sysUserActive.setToken(token);
        sysUserActive.setLastActiveTime(System.currentTimeMillis());
        sysUserActive.setSysUser(user);
        RedisUtils.set(Constant.prefix.SYSUSER_USERNAME + user.getUsername(), sysUserActive);

        //用户登录后,清除用户缓存,以便重新加载用户权限
        clearAuthorizationInfoCache(user);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(sysUserActive, user.getPassword(), getName());
        return info;

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
        _logger.info("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        try{
            SysUser user = ((SysUserActive) principals.getPrimaryPrincipal()).getSysUser();
            Long userId = user.getId();

            // 下面的可以使用缓存提升速度
            //用户权限列表
            Set<String> permsSet = sysUserService.getUserPermissions(userId);
            info.addStringPermissions(permsSet);
            return info;
        }catch (Exception ex){
            _logger.error(ex);
        }

        return info;

    }


    /**
     * 清除所有用户的缓存
     */
    public void clearAuthorizationInfoCache() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if(cache!=null) {
            cache.clear();
        }
    }

    /**
     * 清除指定用户的缓存
     * @param user
     */
    private void clearAuthorizationInfoCache(SysUser user) {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        //key必须是String类型，参考ShiroRedisCache类
        cache.remove(user.getId()+"");
    }

}
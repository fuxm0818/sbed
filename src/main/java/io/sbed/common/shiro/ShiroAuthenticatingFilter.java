package io.sbed.common.shiro;

import com.google.gson.Gson;
import io.sbed.common.Constant;
import io.sbed.common.cache.RedisUtils;
import io.sbed.common.utils.JWTUtil;
import io.sbed.common.utils.ResponseUtil;
import io.sbed.common.utils.Result;
import io.sbed.modules.sys.entity.SysUserActive;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: 基于jwt的token开发 <br>
 * Copyright:DATANG SOFTWARE CO.LTD<br>
 *
 * @author fuxiangming
 * @date 2018/6/14 下午4:10
 */
public class ShiroAuthenticatingFilter extends AuthenticatingFilter {

    private static final Log log = LogFactory.getLog(ShiroAuthenticatingFilter.class);

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader(Constant.TOKEN_IN_HEADER);
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter(Constant.TOKEN_IN_HEADER);
        }
        return token;
    }

    /**
     * 创建shiro认证的token
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String token = getRequestToken((HttpServletRequest) request);
        if (StringUtils.isNotBlank(token)) {
            if (log.isDebugEnabled()) {
                log.debug("Attempting to execute login with headers [" + token + "]");
            }
            return new ShiroToken(token);
        } else {
            return null;
        }
    }

    /**
     * 是否尝试登录
     * 检测header里面是否包含Authorization字段即可
     */
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) throws Exception {
        String tokenInHeader = this.getRequestToken((HttpServletRequest) request);
        SysUserActive sysUserActive = null;

        //token失效
        if (StringUtils.isBlank(tokenInHeader)) {
            return unauthorized((HttpServletResponse) response, "token失效，请重新登录");
        } else {
            // 解密获得username，用于和数据库进行对比
            String usernameInToken = JWTUtil.getUsername(tokenInHeader);
            if (StringUtils.isBlank(usernameInToken)) {
                return unauthorized((HttpServletResponse) response, "token失效，请重新登录");
            }
            sysUserActive = RedisUtils.get(Constant.prefix.SYSUSER_USERNAME + usernameInToken, SysUserActive.class);
            if (null == sysUserActive || StringUtils.isBlank(sysUserActive.getToken()) || !tokenInHeader.equalsIgnoreCase(sysUserActive.getToken())) {
                return unauthorized((HttpServletResponse) response, "token失效，请重新登录");
            }else{
                //token超时
                if (System.currentTimeMillis() > sysUserActive.getLastActiveTime() + Constant.Time.Millisecond.MINUTE_30) {
                    RedisUtils.delete(Constant.prefix.SYSUSER_USERNAME + usernameInToken);
                    return unauthorized((HttpServletResponse) response, "token超时失效，请重新登录");
                }
            }
        }
        return true;
    }

    /**
     * 拒绝访问的出来
     * @param request
     * @param response
     * @return true允许访问，false拒绝访问
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        boolean loggedIn = false; //false by default or we wouldn't be in this method
        if (isLoginAttempt(request, response)) {
            loggedIn = executeLogin(request, response);
        }
        return loggedIn;
    }

    private boolean unauthorized(ServletResponse response, String errorMsg) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String json = new Gson().toJson(Result.error(HttpStatus.SC_UNAUTHORIZED, errorMsg));
        ResponseUtil.write((HttpServletResponse) response, json);
        return false;
    }

}
package io.sbed.common.shiro;

import io.sbed.common.Constant;
import io.sbed.common.cache.RedisUtils;
import io.sbed.common.utils.JWTUtil;
import io.sbed.modules.sys.entity.SysUserActive;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ShiroFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final Log log = LogFactory.getLog(ShiroFormAuthenticationFilter.class);


    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = this.getUsername(request);
        String password = this.getPassword(request);
        boolean rememberMe = this.isRememberMe(request);
        String host = this.getHost(request);
        SysUserActive sysUserActive = (SysUserActive) request.getAttribute("sysUserActive");

        ShiroUsernamePasswordToken usernamePasswordToken = new ShiroUsernamePasswordToken(username, password, rememberMe, host, sysUserActive);

        return usernamePasswordToken;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        SysUserActive sysUserActive = null;
        String captcha = httpServletRequest.getParameter("captcha");
        String captchaT = httpServletRequest.getParameter("captchaT");

        Object _login_errors = 3;
        //redis中获取登录错误次数
        _login_errors = NumberUtils.toInt(RedisUtils.get(Constant.prefix.CAPTCHA_ERROR_TIMES + captchaT), 0);
        if (_login_errors == null) {
            _login_errors = 3;
        }
        long errorTimes = Long.valueOf(_login_errors.toString());
        request.setAttribute("captchaErrorTimes", errorTimes + "");

        //验证码
        if (errorTimes >= 3) {
            String kaptcha = RedisUtils.get(Constant.prefix.CAPTCHA_TEXT + captchaT);
            ;
            if (StringUtils.isNotBlank(kaptcha)) {
                RedisUtils.delete(Constant.prefix.CAPTCHA_TEXT + captchaT);
            }
            if (!captcha.equalsIgnoreCase(kaptcha)) {
                httpServletRequest.setAttribute("shiroLoginFailure", "captchaError");
                return true;
            }
        }

        if (isLoginRequest(request, response)) {
//            if (isLoginSubmission(request, response)) {
//                if (log.isTraceEnabled()) {
//                    log.trace("Login submission detected.  Attempting to execute login.");
//                }
                return executeLogin(request, response);
//            } else {
//                if (log.isTraceEnabled()) {
//                    log.trace("Login page view.");
//                }
//                //allow them to see the login page ;)
//                return true;
//            }
        } else {
            String tokenInHeader = this.getRequestToken((HttpServletRequest) request);
            //token失效
            if (!"null".equalsIgnoreCase(tokenInHeader) && StringUtils.isNotBlank(tokenInHeader)) {
                // 解密获得username，用于和数据库进行对比
                String usernameInToken = JWTUtil.getUsername(tokenInHeader);
                sysUserActive = RedisUtils.get(Constant.prefix.SYSUSER_USERNAME + usernameInToken, SysUserActive.class);
                if (null == sysUserActive || StringUtils.isBlank(sysUserActive.getToken()) || !tokenInHeader.equalsIgnoreCase(sysUserActive.getToken())) {
                    log.error("token无效，进行登录操作");
                    return executeLogin(request, response);
//                throw new AuthenticationException();
//                httpServletRequest.setAttribute("shiroLoginFailure", "tokenError");
//                return true;
                } else {
                    //token超时
                    if (System.currentTimeMillis() > sysUserActive.getLastActiveTime() + Constant.Time.Millisecond.MINUTE_30) {
                        RedisUtils.delete(Constant.prefix.SYSUSER_USERNAME + usernameInToken);
                        log.error("token超时失效，进行登录操作");
                        return executeLogin(request, response);
//                    throw new AuthenticationException();
//                    httpServletRequest.setAttribute("shiroLoginFailure", "tokenError");
//                    return true;
                    } else {
                        request.setAttribute("sysUserActive", sysUserActive);
                        return executeLogin(request, response);
                    }
                }
            } else {
//                request.setAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, "AuthenticationException");
                setFailureAttribute(request, new AuthenticationException());
                return true;

            }

//            if (log.isTraceEnabled()) {
//                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
//                        "Authentication url [" + getLoginUrl() + "]");
//            }

//            redirectToLogin(request, response);
//            return false;
        }

    }

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
}

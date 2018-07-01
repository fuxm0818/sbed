package io.sbed.common.shiro;

import io.sbed.common.Constant;
import io.sbed.common.cache.RedisUtils;
import io.sbed.common.exception.CaptchaException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class JWTAuthenticatingFilter extends AuthenticatingFilter {

    private static final Log log = LogFactory.getLog(JWTAuthenticatingFilter.class);


    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = StringUtils.clean(request.getParameter("username"));
        String password = StringUtils.clean(request.getParameter("password"));
        String token = this.getRequestToken((HttpServletRequest) request);
        boolean isLogin = isLoginRequest(request, response);

        JWTToken jwtToken = new JWTToken(username, password, token, isLogin);
        return jwtToken;
    }

    /**
     * @param request
     * @param response
     * @return true：继续执行其他拦截器，false：自己已经处理完成了
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (isLoginRequest(request, response)) {
            String captcha = StringUtils.clean(httpServletRequest.getParameter("captcha"));
            String captchaT = StringUtils.clean(httpServletRequest.getParameter("captchaT"));
            Object _login_errors = 3;
            //redis中获取登录错误次数
            _login_errors = NumberUtils.toInt(RedisUtils.get(Constant.prefix.CAPTCHA_ERROR_TIMES + captchaT), 0);
            if (_login_errors == null) {
                _login_errors = 3;
            }
            long errorTimes = Long.valueOf(_login_errors.toString());
            //验证码
            if (errorTimes >= 3) {
                String kaptcha = RedisUtils.get(Constant.prefix.CAPTCHA_TEXT + captchaT);
                if (StringUtils.isNotBlank(kaptcha)) {
                    RedisUtils.delete(Constant.prefix.CAPTCHA_TEXT + captchaT);
                }
                if (!captcha.equalsIgnoreCase(kaptcha)) {
                    return this.onLoginFailure(null,new CaptchaException(),request,response);
                }
            }
            return executeLogin(request, response);
        }else{
            if(executeLogin(request, response)){
                redirectToLogin(request, response);
                return false;
            }
            return true;
        }
    }


    /**
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
                                     ServletRequest request, ServletResponse response) {
        log.debug("Authentication exception", e);
        String className = e.getClass().getName();
        request.setAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, className);
        //login failed, let request continue back to the login page:
        return true;
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
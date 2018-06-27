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
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;

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
public class ShiroAuthenticatingFilter extends BasicHttpAuthenticationFilter {

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

    //创建shiro认证的token
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
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        return StringUtils.isNotBlank(this.getRequestToken((HttpServletRequest) request));
    }

    //拒绝访问的出来
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String token = this.getRequestToken((HttpServletRequest) request);
        SysUserActive sysUserActive = null;

        //token失效
        if (StringUtils.isBlank(token)) {
            return unauthorized((HttpServletResponse) response,"token失效，请重新登录");
        } else {
            // 解密获得username，用于和数据库进行对比
            String usernameInToken = JWTUtil.getUsername(token);
            if (StringUtils.isBlank(usernameInToken)) {
                return unauthorized((HttpServletResponse) response,"token失效，请重新登录");
            }

            sysUserActive = RedisUtils.get(Constant.prefix.SYSUSER_USERNAME + usernameInToken, SysUserActive.class);
            if (null == sysUserActive || StringUtils.isBlank(sysUserActive.getToken()) || !token.equalsIgnoreCase(sysUserActive.getToken())) {
                return unauthorized((HttpServletResponse) response,"token失效，请重新登录");
            }

            //token超时
            if (System.currentTimeMillis() > sysUserActive.getLastActiveTime() + Constant.Time.Millisecond.MINUTE_30) {
                RedisUtils.delete(Constant.prefix.SYSUSER_USERNAME + usernameInToken);
                return unauthorized((HttpServletResponse) response,"token超时失效，请重新登录");
            }
        }

        return this.executeLogin(request, response);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = this.createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken must be created in order to execute a login attempt.";
            return unauthorized((HttpServletResponse) response,"token缺失");
        } else {
            try {
                Subject subject = this.getSubject(request, response);
                subject.login(token);
                return this.onLoginSuccess(token, subject, request, response);
            } catch (AuthenticationException e) {
                log.error("登录异常-->ShiroAuthenticatingFilter.executeLogin()",e);
                return unauthorized((HttpServletResponse) response,"登录异常");
            }
        }
    }

    private boolean unauthorized(ServletResponse response,String errorMsg) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String json = new Gson().toJson(Result.error(HttpStatus.SC_UNAUTHORIZED, errorMsg));
        ResponseUtil.write((HttpServletResponse) response, json);
        return false;
    }


    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.SC_OK);
            return false;
        }
        return super.preHandle(request, response);
    }

}
package io.sbed.common.shiro;

import com.google.gson.Gson;
import io.sbed.common.Constant;
import io.sbed.common.utils.ResponseUtil;
import io.sbed.common.utils.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description: 退出过滤器 <br>
 * Copyright:DATANG SOFTWARE CO.LTD<br>
 *
 * @author fuxiangming
 * @date 2018/6/14 下午4:09
 */
public class ShiroLogoutFilter extends LogoutFilter {

    private static final Log log = LogFactory.getLog(ShiroLogoutFilter.class);

    private static final Gson gson = new Gson();

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        String redirectUrl = getRedirectUrl(request, response, subject);
        //try/catch added for SHIRO-298:
        try {
            subject.logout();
            // redis中删除token信息
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String token = httpServletRequest.getHeader(Constant.TOKEN_IN_HEADER);
//            RedisUtils.delete(Constant.prefix.SYSUSER_USERNAME + JWTUtil.getUsername(token));
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            ResponseUtil.writeJSON((HttpServletResponse) response, gson.toJson(Result.ok()));
        } catch (SessionException ise) {
            log.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        }
//        issueRedirect(request, response, redirectUrl);
        return false;
    }

}

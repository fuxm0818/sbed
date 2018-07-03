package io.sbed.modules.sys.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.code.kaptcha.Producer;
import io.sbed.common.Constant;
import io.sbed.common.cache.RedisUtils;
import io.sbed.common.exception.CaptchaException;
import io.sbed.common.utils.Result;
import io.sbed.modules.sys.entity.SysUserActive;
import io.sbed.modules.sys.service.SysUserService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @Description: (登录相关)
 * @date 2017-6-23 15:07
 */
@RestController
public class SysLoginController extends AbstractController {

    @Autowired
    private Producer producer;
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/captcha.jpg")
    public void captcha(HttpServletRequest request, HttpServletResponse response, String captchaT) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //redis中保存（用于其他地方判断是否输入正确）
        RedisUtils.set(Constant.prefix.CAPTCHA_TEXT + captchaT, text, Constant.Time.Second.MINUTE_5);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 获取登录错误次数
     */
    @RequestMapping(value = "/sys/getLoginErrorTimes", method = RequestMethod.GET)
    public Result getLoginErrorTimes(String captchaT) {
        int errorTimes = NumberUtils.toInt(RedisUtils.get(Constant.prefix.CAPTCHA_ERROR_TIMES + captchaT), 0);
        //redis中获取登录错误次数
        return Result.ok().put("errorTimes", errorTimes);
    }


    @RequestMapping(value = "/sys/login")
    public Result login(HttpServletRequest request, String captchaT) throws Exception {
        Map<String, Object> result = new HashMap<>();
        //shiro在认证通过后出现错误后将异常类路径通过request返回
        //如果登陆失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
        if (StringUtils.isNotBlank(exceptionClassName)) {
            long errorTimes = NumberUtils.toInt(RedisUtils.get(Constant.prefix.CAPTCHA_ERROR_TIMES + captchaT), 0);
            RedisUtils.set(Constant.prefix.CAPTCHA_ERROR_TIMES + captchaT, ++errorTimes, Constant.Time.Second.MINUTE_5);
            if (AuthenticationException.class.getName().equals(exceptionClassName)) {
                throw new AuthenticationException();
            } else if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                throw new UnknownAccountException();
            } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
                throw new IncorrectCredentialsException();
            } else if (LockedAccountException.class.getName().equals(exceptionClassName)) {
                throw new LockedAccountException();
            } else if ("tokenError".equals(exceptionClassName)) {
                throw new AuthenticationException();
            } else if (CaptchaException.class.getName().equals(exceptionClassName)) {
                throw new CaptchaException();
            } else if (ExpiredCredentialsException.class.getName().equals(exceptionClassName)) {
                throw new ExpiredCredentialsException();
            }else if (JWTVerificationException.class.getName().equals(exceptionClassName)) {
                throw new JWTVerificationException("token校验无效");
            } else {
                throw new Exception(); //最终在设置的异常处理器中生成未知错误
            }
        }

        //获取token放入result中
        SysUserActive sysUserActive = (SysUserActive) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        result.put(Constant.TOKEN_IN_HEADER, sysUserActive.getToken());

        //此方法不处理登录成功(认证成功)的情况
        //如果登录失败还到login页面
        Result r = Result.ok().put(result);
        return r;
    }

}

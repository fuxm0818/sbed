package io.sbed.modules.sys.controller;

import com.google.code.kaptcha.Producer;
import io.sbed.common.Constant;
import io.sbed.common.cache.RedisUtils;
import io.sbed.common.exception.CaptchaException;
import io.sbed.common.utils.Result;
import io.sbed.modules.sys.service.SysUserService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
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

//    /**
//     * 登录
//     */
//    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
//    public Result login(String username, String password, String captcha, String captchaT) throws IOException {
//        Object _login_errors = 3;
//        //redis中获取登录错误次数
//        _login_errors = NumberUtils.toInt(RedisUtils.get(Constant.prefix.CAPTCHA_ERROR_TIMES + captchaT), 0);
//        if (_login_errors == null) {
//            _login_errors = 3;
//        }
//        long errorTimes = Long.valueOf(_login_errors.toString());
//
//        //用户信息
//        SysUser user = sysUserService.queryByUserName(username);
//
//        //账号不存在
//        if (user == null) {
//            //redis中获取登录错误次数
//            RedisUtils.set(Constant.prefix.CAPTCHA_ERROR_TIMES + captchaT, ++errorTimes, Constant.Time.Second.MINUTE_5);
//            return Result.error("用户名或密码错误").put("errorTimes", errorTimes);
//        }
//
//        //密码错误,使用密码当做加密的盐
//        if (!user.getPassword().equals(new Sha256Hash(password, user.getSalt()).toHex())) {
//            //redis中获取登录错误次数
//            RedisUtils.set(Constant.prefix.CAPTCHA_ERROR_TIMES + captchaT, ++errorTimes, Constant.Time.Second.MINUTE_5);
//            return Result.error("用户名或密码错误").put("errorTimes", errorTimes);
//        }
//
//        //验证码
//        String kaptcha = getKaptcha(captchaT);
//        if (errorTimes >= 3) {
//            if (!captcha.equalsIgnoreCase(kaptcha)) {
//                // redis中获取登录错误次数
//                RedisUtils.set(Constant.prefix.CAPTCHA_ERROR_TIMES + captchaT, ++errorTimes, Constant.Time.Second.MINUTE_5);
//                return Result.error("验证码错误").put("errorTimes", errorTimes);
//            }
//        }
//
//        //账号锁定
//        if (Constant.UserStatus.DISABLE.getValue() == user.getStatus()) {
//            // redis中获取登录错误次数
//            RedisUtils.set(Constant.prefix.CAPTCHA_ERROR_TIMES + captchaT, ++errorTimes, Constant.Time.Second.MINUTE_5);
//            return Result.error("账号已被锁定,请联系管理员").put("errorTimes", errorTimes);
//        }
//
//        //生成token
//        String token = JWTUtil.sign(user.getUsername() + "", user.getPassword() + "");
//
//        //保存到数据库redis
//        SysUserActive sysUserActive = new SysUserActive();
//        sysUserActive.setToken(token);
//        sysUserActive.setLastActiveTime(System.currentTimeMillis());
//        RedisUtils.set(Constant.prefix.SYSUSER_USERNAME + user.getUsername(), sysUserActive);
//
//        Map<String, Object> result = new HashMap<>();
//        result.put(Constant.TOKEN_IN_HEADER, token);
//        Result r = Result.ok().put(result);
//        return r;
//    }


    @RequestMapping(value = "/sys/login")
    public Result login(HttpServletRequest request, String captchaT) throws Exception {
//        long errorTimes = NumberUtils.toInt(RedisUtils.get(Constant.prefix.CAPTCHA_ERROR_TIMES + captchaT), 0);
//
//        //验证码
//        if (errorTimes >= 3) {
//            String kaptcha = getKaptcha(captchaT);
//            if (!captcha.equalsIgnoreCase(kaptcha)) {
//                // redis中获取登录错误次数
//                RedisUtils.set(Constant.prefix.CAPTCHA_ERROR_TIMES + captchaT, ++errorTimes, Constant.Time.Second.MINUTE_5);
////                return Result.error("验证码错误").put("errorTimes", errorTimes);
//                throw new SbedException("验证码错误");
//            }
//        }
//        RedisUtils.delete(Constant.prefix.CAPTCHA_TEXT + captchaT);
//
//        Subject subject = SecurityUtils.getSubject();
//
//        AuthenticationToken token = new UsernamePasswordToken(username, password);
//
        Map<String, Object> result = new HashMap<>();
//        try {
//            // 登录，即身份验证
//            subject.login(token);
//            SysUserActive sysUserActive = (SysUserActive)subject.getPrincipal();
//            result.put(Constant.TOKEN_IN_HEADER, sysUserActive.getToken());
//
//        } catch (IncorrectCredentialsException e) {
//            RedisUtils.set(Constant.prefix.CAPTCHA_ERROR_TIMES + captchaT, ++errorTimes, Constant.Time.Second.MINUTE_5);
//            return Result.error("用户名或密码错误").put("errorTimes", errorTimes);
//        } catch (UnauthenticatedException e) {
//            RedisUtils.set(Constant.prefix.CAPTCHA_ERROR_TIMES + captchaT, ++errorTimes, Constant.Time.Second.MINUTE_5);
//            return Result.error("用户名或密码错误").put("errorTimes", errorTimes);
//        } catch (LockedAccountException e) {
//            RedisUtils.set(Constant.prefix.CAPTCHA_ERROR_TIMES + captchaT, ++errorTimes, Constant.Time.Second.MINUTE_5);
//            return Result.error("登录失败，该用户已被冻结").put("errorTimes", errorTimes);
//        }


        long errorTimes = NumberUtils.toLong((String)request.getAttribute("captchaErrorTimes"),0);
        //shiro在认证通过后出现错误后将异常类路径通过request返回
        //如果登陆失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
        if (StringUtils.isNotBlank(exceptionClassName)) {
            RedisUtils.set(Constant.prefix.CAPTCHA_ERROR_TIMES + captchaT, ++errorTimes, Constant.Time.Second.MINUTE_5);
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                throw new UnknownAccountException();
            } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
                throw new IncorrectCredentialsException();
            } else if (LockedAccountException.class.getName().equals(exceptionClassName)) {
                throw new LockedAccountException();
            } else if ("tokenError".equals(exceptionClassName)) {
                throw new AuthenticationException();
            } else if ("captchaError".equals(exceptionClassName)) {
                throw new CaptchaException("验证码错误", errorTimes);
            } else {
                throw new Exception(); //最终在设置的异常处理器中生成未知错误
            }
        }

//        //获取token放入result中
//        SysUserActive sysUserActive = (SysUserActive) SecurityUtils.getSubject().getPrincipal();
//        result.put(Constant.TOKEN_IN_HEADER, sysUserActive.getToken());

        //此方法不处理登录成功(认证成功)的情况
        //如果登录失败还到login页面
        Result r = Result.ok().put(result);
        return r;
    }

//    /**
//     * 从session中获取记录的验证码
//     */
//    private String getKaptcha(String captchaT) {
//        String kaptcha = RedisUtils.get(Constant.prefix.CAPTCHA_TEXT + captchaT);
//        if (StringUtils.isBlank(kaptcha)) {
//            throw new SbedException("验证码已失效");
//        }
//        RedisUtils.delete(Constant.prefix.CAPTCHA_TEXT + captchaT);
//        return kaptcha;
//    }

}

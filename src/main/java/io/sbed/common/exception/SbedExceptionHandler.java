package io.sbed.common.exception;

import io.sbed.common.utils.Result;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author
 * @Description: (异常处理器)
 * @date 2017-6-23 15:07
 */
@RestControllerAdvice
public class SbedExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 自定义异常
	 */
	@ExceptionHandler(SbedException.class)
	public Result handleSbedException(SbedException e){
		logger.error(e.getMessage(), e);
		return Result.error(e.getCode(), e.getMessage());
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public Result handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return Result.error("数据库中已存在该记录");
	}

	@ExceptionHandler(AuthorizationException.class)
	public Result handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return Result.error("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(CaptchaException.class)
	public Result handleCaptchaException(CaptchaException e){
		logger.error(e.getMessage(), e);
		return Result.error("验证码错误").put("errorTimes",e.getErrorTimes());
	}

	@ExceptionHandler(UnknownAccountException.class)
	public Result handleUnknownAccountException(UnknownAccountException e){
		logger.error(e.getMessage(), e);
		return Result.error("账号不存在");
	}

	@ExceptionHandler(IncorrectCredentialsException.class)
	public Result handleIncorrectCredentialsException(IncorrectCredentialsException e){
		logger.error(e.getMessage(), e);
		return Result.error("用户名/密码错误");
	}

	@ExceptionHandler(LockedAccountException.class)
	public Result handleLockedAccountException(LockedAccountException e){
		logger.error(e.getMessage(), e);
		return Result.error("账号已被锁定");
	}

	@ExceptionHandler(AuthenticationException.class)
	public Result handleAuthorizationException(AuthenticationException e){
		logger.error(e.getMessage(), e);
		return Result.error(HttpStatus.SC_UNAUTHORIZED,"认证失败");
	}

	@ExceptionHandler(Exception.class)
	public Result handleException(Exception e){
		logger.error(e.getMessage(), e);
		return Result.error();
	}

}

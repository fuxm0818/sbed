package io.sbed.common.exception;

import io.sbed.common.utils.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author heguoliang
 * @Description: TODO(异常处理器)
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

	@ExceptionHandler(Exception.class)
	public Result handleException(Exception e){
		logger.error(e.getMessage(), e);
		return Result.error();
	}

}

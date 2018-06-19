package io.sbed.modules.sys.controller;

import io.sbed.common.utils.JWTUtil;
import io.sbed.modules.sys.entity.SysUser;
import io.sbed.modules.sys.service.SysUserService;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author
 * @Description: (Controller公共组件)
 * @date 2017-6-23 15:07
 */
public abstract class AbstractController {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
//	protected SysUser getUser() {
//		return (SysUser) SecurityUtils.getSubject().getPrincipal();
//	}

	protected SysUser getUser(SysUserService sysUserService) {
		return sysUserService.queryObject(getUserId());
	}



	protected Long getUserId() {
//		return getUser().getId();
		return NumberUtils.toLong(JWTUtil.getUsername((String)SecurityUtils.getSubject().getPrincipal()),-1);
	}

//	protected Long getDeptId() {
//		return getUser().getDeptId();
//	}

}

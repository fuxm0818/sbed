package io.sbed.modules.sys.controller;

import io.sbed.modules.sys.entity.SysUser;
import io.sbed.modules.sys.entity.SysUserActive;
import io.sbed.modules.sys.service.SysUserService;
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
	
	protected SysUser getUser() {
		return (((SysUserActive) SecurityUtils.getSubject().getPrincipal()).getSysUser());
	}

	protected SysUser getUser(SysUserService sysUserService) {
		return sysUserService.queryObject(getUserId());
	}



	protected Long getUserId() {
		return getUser().getId();
	}

	protected Long getDeptId() {
		return getUser().getDeptId();
	}

}

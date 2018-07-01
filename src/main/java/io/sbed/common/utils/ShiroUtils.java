package io.sbed.common.utils;

import io.sbed.modules.sys.entity.SysUser;
import io.sbed.modules.sys.entity.SysUserActive;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

/**
 * @author
 * @Description: (Shiro工具类)
 * @date 2017-6-23 15:07
 */
@Component
public class ShiroUtils {

//	public static Session getSession() {
//		return SecurityUtils.getSubject().getSession();
//	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static SysUser getUserEntity() {
		return ((SysUserActive)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal()).getSysUser();
	}

	public static Long getUserId() {
		return getUserEntity().getId();
	}
	
//	public static void setSessionAttribute(Object key, Object value) {
//		getSession().setAttribute(key, value);
//	}

//	public static Object getSessionAttribute(Object key) {
//		return getSession().getAttribute(key);
//	}

	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}

	public static void logout() {
		SecurityUtils.getSubject().logout();
	}

}

package io.sbed.common.shiro;

import io.sbed.modules.sys.entity.SysUserActive;
import org.apache.shiro.authc.UsernamePasswordToken;

public class ShiroUsernamePasswordToken extends UsernamePasswordToken {
    private SysUserActive sysUserActive;

    public ShiroUsernamePasswordToken(String username, String password, boolean rememberMe, String host, SysUserActive sysUserActive) {
        super(username, password != null ? password.toCharArray() : null, rememberMe, host);
        this.sysUserActive = sysUserActive;
    }

    public SysUserActive getSysUserActive() {
        return sysUserActive;
    }

    public void setSysUserActive(SysUserActive sysUserActive) {
        this.sysUserActive = sysUserActive;
    }
}

package io.sbed.modules.sys.entity;


import java.io.Serializable;

/**
 * Description:  <br>
 * Copyright:DATANG SOFTWARE CO.LTD<br>
 *
 * @author fuxiangming
 * @date 2018/6/26 09:27
 */
public class SysUserActive implements Serializable {


    private static final long serialVersionUID = 7637115479953903208L;

    private long lastActiveTime;
    private String token;
    private String username;
    private SysUser sysUser;

    public long getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(long lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public SysUserActive setLastActiveTimeAndReturn(long lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
        return this;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return this.username;
    }
}

package io.sbed.common.shiro;

import io.sbed.modules.sys.entity.SysUserActive;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha256Hash;


public class CredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken utoken=(UsernamePasswordToken) token;
        SysUserActive sysUserActive = (SysUserActive) info.getPrincipals().getPrimaryPrincipal();
        //密码错误,使用密码当做加密的盐
        return sysUserActive.getSysUser().getPassword().equals(new Sha256Hash(utoken.getPassword(), sysUserActive.getSysUser().getSalt()).toHex());
    }

}
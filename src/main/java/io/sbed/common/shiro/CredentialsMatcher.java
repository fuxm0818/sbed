package io.sbed.common.shiro;

import io.sbed.modules.sys.entity.SysUserActive;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Component;

/**
 * Description: 凭证匹配器，验证密码 <br>
 * Copyright:DATANG SOFTWARE CO.LTD<br>
 *
 * @author fuxiangming
 * @date 2018/6/14 下午4:09
 */
@Component
public class CredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        JWTToken utoken = (JWTToken) token;
        SysUserActive sysUserActive = (SysUserActive) info.getPrincipals().getPrimaryPrincipal();
        //是否是登录请求
        if (utoken.isLoginRequest()) {
            //登录，验证密码是否匹配
            return sysUserActive.getSysUser().getPassword().equals(new Sha256Hash(utoken.getCredentials(), sysUserActive.getSysUser().getSalt()).toHex());
        } else {
            if(StringUtils.isBlank(utoken.getToken())){
                return false;
            }
            //验证token是否一致
            return utoken.getToken().equals(sysUserActive.getToken());
        }

    }

}
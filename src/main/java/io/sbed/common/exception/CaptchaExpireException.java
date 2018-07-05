package io.sbed.common.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 验证码过期异常
 */
public class CaptchaExpireException extends AuthenticationException {


    private long loginErrorTimes = 0;

    private String msg;

    private int code = 500;

    public CaptchaExpireException() {
        super();
    }

    public CaptchaExpireException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public CaptchaExpireException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public CaptchaExpireException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public CaptchaExpireException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getLoginErrorTimes() {
        return loginErrorTimes;
    }

    public void setLoginErrorTimes(long loginErrorTimes) {
        this.loginErrorTimes = loginErrorTimes;
    }
}

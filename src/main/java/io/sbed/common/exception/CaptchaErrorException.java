package io.sbed.common.exception;

import org.apache.shiro.authc.AuthenticationException;

public class CaptchaErrorException extends AuthenticationException {


    private long errorTimes = 0;

    private String msg;

    private int code = 500;

    public CaptchaErrorException() {
        super();
    }

    public CaptchaErrorException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public CaptchaErrorException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public CaptchaErrorException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public CaptchaErrorException(String msg, int code, Throwable e) {
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

    public long getErrorTimes() {
        return errorTimes;
    }

    public void setErrorTimes(long errorTimes) {
        this.errorTimes = errorTimes;
    }
}

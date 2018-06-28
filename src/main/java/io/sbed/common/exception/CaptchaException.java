package io.sbed.common.exception;

public class CaptchaException extends SbedException {


    private long errorTimes = 0;

    public CaptchaException(String msg) {
        super(msg);
    }

    public CaptchaException(String msg, Throwable e) {
        super(msg, e);
    }

    public CaptchaException(String msg, int code) {
        super(msg, code);
    }

    public CaptchaException(String msg, long errorTimes) {
        super(msg);
        this.errorTimes = errorTimes;
    }

    public CaptchaException(String msg, int code, Throwable e) {
        super(msg, code, e);
    }


    public long getErrorTimes() {
        return errorTimes;
    }

    public void setErrorTimes(long errorTimes) {
        this.errorTimes = errorTimes;
    }
}

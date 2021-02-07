package com.backstage.common.exception;

/**
 * @author yangfeng
 * @date 2019-12-31
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private int status;

    public BaseException() {
        this(-1);
    }

    public BaseException(int status) {
        this(status, null);
    }

    public BaseException(int status, String message) {
        super(message);
        this.status = status;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(int status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return getMessage();
    }

}

package com.lzb.www.exception;

public class WebException extends RuntimeException {
    public WebException(String msg) {
        super(msg);
    }

    public WebException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public WebException(Throwable cause) {
        super(cause);
    }

}

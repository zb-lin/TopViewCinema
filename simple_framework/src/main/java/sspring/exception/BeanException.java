package sspring.exception;

/**
 * 定义 Bean 异常
 */
public class BeanException extends RuntimeException {
    public BeanException(String msg) {
        super(msg);
    }

    public BeanException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BeanException(Throwable cause) {
        super(cause);
    }
}

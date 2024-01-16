package lorm.exception;

/**
 * 定义 dao 异常
 */
public class DaoException extends RuntimeException {
    public DaoException(String msg) {
        super(msg);
    }

    public DaoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

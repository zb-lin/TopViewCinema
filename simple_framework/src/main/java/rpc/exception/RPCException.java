package rpc.exception;

public class RPCException extends RuntimeException{
    public RPCException(String msg) {
        super(msg);
    }

    public RPCException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public RPCException(Throwable cause) {
        super(cause);
    }
}

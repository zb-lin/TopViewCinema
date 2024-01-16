package rpc.core.provider;


import rpc.exception.RPCException;

import static rpc.constants.Constants.ERROR_MSG;

public class DefaultCallBack implements CallBack {

    private Object result;

    public Object getResult() {
        return result;
    }

    @Override
    public void saveResult(Object result) {
        if (ERROR_MSG.equals(result)) {
            throw new RPCException("error: Request repeated execution");
        }
        this.result = result;
    }
}

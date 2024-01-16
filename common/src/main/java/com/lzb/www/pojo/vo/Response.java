package com.lzb.www.pojo.vo;

import java.util.function.Supplier;

public class Response<T> {
    Object data;
    String successMsg;
    String errorMas;
    boolean result;
    Supplier<T> supplier;

    public Response(Object data, String successMsg, String errorMas, boolean result, Supplier<T> supplier) {
        this.data = data;
        this.successMsg = successMsg;
        this.errorMas = errorMas;
        this.result = result;
        this.supplier = supplier;
    }

    public static <T> Response<T> response(Object data, String successMsg, String errorMas, boolean result, Supplier<T> supplier) {
        return new Response<>(data, successMsg, errorMas, result, supplier);
    }

    public static <T> Response<T> response(String successMsg, String errorMas, boolean result) {
        return new Response<>(null, successMsg, errorMas, result, null);
    }
    public static <T> Response<T> response(String errorMas, boolean result) {
        return new Response<>(null, null, errorMas, result, null);
    }

    public static <T> Response<T> response(Object data, String successMsg, String errorMas, boolean result) {
        return new Response<>(data, successMsg, errorMas, result, null);
    }

    public Object getData() {
        return data;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public String getErrorMas() {
        return errorMas;
    }

    public boolean isResult() {
        return result;
    }

    public Supplier<T> getSupplier() {
        return supplier;
    }

    @Override
    public String toString() {
        return "Response{" +
                "data=" + data +
                ", successMsg='" + successMsg + '\'' +
                ", errorMas='" + errorMas + '\'' +
                ", result=" + result +
                ", supplier=" + supplier +
                '}';
    }
}

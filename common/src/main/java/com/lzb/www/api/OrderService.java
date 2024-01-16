package com.lzb.www.api;

public interface OrderService {
    byte[] insertOrder(byte[] bytes);

    byte[] listOrders(byte[] bytes);

    byte[] returnTicket(byte[] bytes);

    byte[] deleteOrder(byte[] bytes);
}

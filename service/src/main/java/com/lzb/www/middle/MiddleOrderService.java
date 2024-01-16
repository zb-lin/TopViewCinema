package com.lzb.www.middle;

import com.google.protobuf.InvalidProtocolBufferException;

public interface MiddleOrderService {
    byte[] insertOrder(byte[] bytes) throws InvalidProtocolBufferException;

    byte[] listOrders(byte[] bytes) throws InvalidProtocolBufferException;

    byte[] returnTicket(byte[] bytes) throws InvalidProtocolBufferException;

    byte[] deleteOrder(byte[] bytes) throws InvalidProtocolBufferException;
}

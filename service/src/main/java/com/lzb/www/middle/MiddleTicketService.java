package com.lzb.www.middle;

import com.google.protobuf.InvalidProtocolBufferException;

public interface MiddleTicketService {

    byte[] insertTicket(byte[] bytes) throws InvalidProtocolBufferException;

    byte[] listTicket(byte[] bytes) throws InvalidProtocolBufferException;

    byte[] deleteTicket(byte[] bytes) throws InvalidProtocolBufferException;

    byte[] updateTicket(byte[] bytes) throws InvalidProtocolBufferException;

    byte[] updateSeat(byte[] bytes) throws InvalidProtocolBufferException;

}

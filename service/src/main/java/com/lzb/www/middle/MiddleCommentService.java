package com.lzb.www.middle;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.ServiceException;

public interface MiddleCommentService {
    byte[]  listComment(byte[] bytes) throws ServiceException, InvalidProtocolBufferException;

}

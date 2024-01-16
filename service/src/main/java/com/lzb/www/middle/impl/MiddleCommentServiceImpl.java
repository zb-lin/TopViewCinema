package com.lzb.www.middle.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.ServiceException;
import com.lzb.www.middle.MiddleCommentService;
import com.lzb.www.pojo.po.Comment;
import com.lzb.www.pojo.vo.PageBean;
import com.lzb.www.proto.RPCProto;
import com.lzb.www.service.CommentService;
import sspring.bean.annotation.Hosted;
import sspring.bean.annotation.LoggerProxy;

@Hosted
public class MiddleCommentServiceImpl implements MiddleCommentService {
    @LoggerProxy
    private CommentService commentService;

    @Override
    public byte[] listComment(byte[] bytes) throws ServiceException, InvalidProtocolBufferException {
        RPCProto.CommentRequest commentRequest = RPCProto.CommentRequest.parseFrom(bytes);
        RPCProto.CommentRequest.ListCommentInfo listCommentInfo = commentRequest.getListCommentInfo();
        int currentPage = listCommentInfo.getCurrentPage();
        int pageSize = listCommentInfo.getPageSize();
        int movieId = listCommentInfo.getMovieId();
        // 不存在
        if (currentPage == 0 || pageSize == 0 || movieId == 0) {
            throw new ServiceException("listComment: currentPage pageSize movieId not exist");
        }
        PageBean<Object> pageBean = commentService.listComment(currentPage, pageSize, movieId);

        RPCProto.CommentPageBean.Builder commentPageBean = RPCProto.CommentPageBean.newBuilder();
        for (Object comment : pageBean.getRows()) {
            Comment comment1 = (Comment) comment;
            RPCProto.Comment comment2 = RPCProto.Comment.newBuilder()
                    .setComment(comment1.getComment())
                    .setId(comment1.getId())
                    .setMovieId(comment1.getMovieId())
                    .setEvaluate(comment1.getEvaluate())
                    .setUserId(comment1.getUserId()).build();
            commentPageBean.addComment(comment2);
        }
        commentPageBean.setTotalCount(Integer.parseInt(String.valueOf(pageBean.getTotalCount())));

        RPCProto.CommentResponse.ListCommentResult listCommentResult = RPCProto.CommentResponse.ListCommentResult.newBuilder()
                .setCommentPageBean(commentPageBean).build();
        return RPCProto.CommentResponse.newBuilder().setListCommentResult(listCommentResult).build().toByteArray();
    }

}

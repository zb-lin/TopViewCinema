package com.lzb.www.controller.mock;

import com.lzb.www.api.CommentService;
import com.lzb.www.pojo.po.Comment;
import com.lzb.www.proto.RPCProto;
import sspring.bean.annotation.Hosted;

@Hosted
public class MockCommentServiceImpl implements CommentService {

    @Override
    public byte[] listComment(byte[] bytes) {

        Comment comment = new Comment(1, 1, 1, 1, "1");

        RPCProto.CommentPageBean.Builder commentPageBean = RPCProto.CommentPageBean.newBuilder();
        RPCProto.Comment comment2 = RPCProto.Comment.newBuilder()
                .setComment(comment.getComment())
                .setId(comment.getId())
                .setMovieId(comment.getMovieId())
                .setEvaluate(comment.getEvaluate())
                .setUserId(comment.getUserId()).build();
        commentPageBean.addComment(comment2);
        commentPageBean.setTotalCount(1);

        RPCProto.CommentResponse.ListCommentResult listCommentResult = RPCProto.CommentResponse.ListCommentResult.newBuilder()
                .setCommentPageBean(commentPageBean).build();
        return RPCProto.CommentResponse.newBuilder().setListCommentResult(listCommentResult).build().toByteArray();
    }

}

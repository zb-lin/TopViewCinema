package com.lzb.www.controller;


import com.lzb.www.api.CommentService;
import com.lzb.www.pojo.po.Comment;
import com.lzb.www.pojo.vo.PageBean;
import com.lzb.www.pojo.vo.Response;
import com.lzb.www.proto.RPCProto;
import com.lzb.www.util.ProtoBufUtil;
import sspring.bean.annotation.Hosted;
import com.lzb.www.annotation.Param;
import sspring.bean.annotation.MockBean;
import sspring.bean.annotation.RpcProxy;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;

import static com.lzb.www.constant.WebConstant.*;

@Hosted
@WebServlet("/comment/*")
public class CommentServlet extends BaseServlet {
    @RpcProxy
//    @MockBean
    private CommentService commentService;

    public Response<?> listComment(@Param(CURRENT_PAGE) Integer currentPage, @Param(PAGE_SIZE) Integer pageSize,
                                   @Param(ID) Integer movieId) throws IOException {
        RPCProto.CommentRequest commentRequest = ProtoBufUtil.listCommentRequest(currentPage, pageSize, movieId);
        byte[] bytes = commentService.listComment(commentRequest.toByteArray());
        PageBean<Comment> commentPageBean = ProtoBufUtil.listCommentResponse(bytes);
        return Response.response(commentPageBean, QUERY_SUCCESS, QUERY_ERROR, bytes != null);
    }

}

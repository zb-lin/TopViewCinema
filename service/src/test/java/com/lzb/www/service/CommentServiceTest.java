package com.lzb.www.service;

import com.lzb.www.pojo.vo.PageBean;
import com.lzb.www.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;

public class CommentServiceTest {
    private final CommentService commentService = new CommentServiceImpl();

    @Test
    public void test01() {
        PageBean<Object> pageBean = commentService.listComment(1, 5, 2);
        System.out.println(pageBean);
    }
}

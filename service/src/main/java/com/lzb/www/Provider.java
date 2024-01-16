package com.lzb.www;


import com.lzb.www.service.*;
import com.lzb.www.service.impl.*;
import rpc.core.provider.TokenBucket;
import rpc.core.provider.Server;
import rpc.core.register.Register;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static rpc.constants.Constants.*;


@WebServlet("/*")
public class Provider extends HttpServlet {

    private final Register register =Register.getInstance();

    @Override
    public void init() throws ServletException {
        new Thread(() -> {
            TokenBucket tokenBucket = TokenBucket.getInstance();
            tokenBucket.init();
            synchronized (register) {
                while (true) {
                    try {
                        if (!tokenBucket.poll()) {
                            // 200 毫秒
                            register.wait(200);
                        }
                        register.register(UserService.class.getSimpleName(), UserServiceImpl.class, PORT, LOCALHOST);
                        register.register(MovieService.class.getSimpleName(), MovieServiceImpl.class, PORT, LOCALHOST);
                        register.register(OrderService.class.getSimpleName(), OrderServiceImpl.class, PORT, LOCALHOST);
                        register.register(TicketService.class.getSimpleName(), TicketServiceImpl.class, PORT, LOCALHOST);
                        register.register(CommentService.class.getSimpleName(), CommentServiceImpl.class, PORT, LOCALHOST);
                        register.register(RoleService.class.getSimpleName(), RoleServiceImpl.class, PORT, LOCALHOST);
                        // 5分钟
                        register.wait(300000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
        new Thread(() -> {
            new Server().server();
        }).start();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        new Server().server(req, resp);
    }
}
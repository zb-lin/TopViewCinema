package com.lzb.www;

import com.lzb.www.pojo.po.User;
import com.lzb.www.service.UserService;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;

public class MockTest {
    @Test
    public void test01() {
        UserService userService = mock(UserService.class);
        when(userService.getUser(1)).thenReturn(new User(1, "1", "1", "1"));
        System.out.println(userService);
        User user = userService.getUser(1);
        System.out.println(user);
    }
}

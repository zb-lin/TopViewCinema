package com.lzb.www.controller.filter;

import com.google.protobuf.ProtocolStringList;
import com.lzb.www.api.RoleService;
import com.lzb.www.api.UserService;
import com.lzb.www.controller.BaseServlet;
import com.lzb.www.exception.WebException;
import com.lzb.www.pojo.po.User;
import com.lzb.www.proto.RPCProto;
import com.lzb.www.util.JWTUtils;
import com.lzb.www.util.ProtoBufUtil;
import com.lzb.www.util.StringUtils;
import redis.clients.jedis.Jedis;
import rpc.core.consumer.ProxyFactory;
import rpc.util.JedisUtil;
import sspring.bean.annotation.Hosted;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.*;

import static com.lzb.www.constant.GlobalConstant.ONE_DAY;
import static com.lzb.www.constant.WebConstant.*;
import static rpc.constants.Constants.REDIS_DEFAULT_VALUE;
import static rpc.constants.Constants.REDIS_USER_KEY;

@Hosted
@WebFilter("/*")
public class AccessFilter extends BaseServlet implements Filter {
    private final UserService userService = ProxyFactory.getProxy(UserService.class);
    private final RoleService roleService = ProxyFactory.getProxy(RoleService.class);
    //    private final UserService userService = new MockUserServiceImpl();
    //    private final RoleService roleService = new MockRoleServiceImpl();
    private static String[] permissions = null;
    private static final int MAXIMUM_VISITS_PER_MINUTE = 500;
    private static final int EXIST_TIME_SECOND = 60;
    private static final int ORDINARY_USERS_ID = 2;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 判断访问资源路径是否和登录相关
        String[] urls = {LOGIN_HTML, LOGIN_INTERFACE, PHOTO_FOLDER};

        // 获取当前访问的资源路径
        String thisUrl = request.getRequestURL().toString();
        // 循环判断
        for (String url : urls) {
            if (thisUrl.contains(url)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        // 根据id 获得 user
        Integer id = getIdByToken(request);
        int id1 = 0;
        if (id == null) {
            // 从cookie获取id
            id1 = Integer.parseInt(getValueByCookie(request, ID));
            // 自动续签
            Jedis jedis = null;
            try {
                jedis = JedisUtil.getJedis();
                // redis中也不存在, 重新登录
                if (!jedis.exists("login:" + id1)) {
                    throw new WebException(NOT_LOGIN);
                }
                // 自动续签
                Map<String, Integer> map = new HashMap<>();
                map.put(ID, id1);
                String token = JWTUtils.getToken(map);
                Cookie cookie = new Cookie(TOKEN, URLEncoder.encode(token, UTF_8));
                cookie.setMaxAge(ONE_DAY);
                cookie.setPath("/");
                response.addCookie(cookie);
                cookie = new Cookie(ID, String.valueOf(id1));
                cookie.setMaxAge(ONE_DAY);
                cookie.setPath("/");
                response.addCookie(cookie);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                JedisUtil.closeJedis(jedis);
            }
        }

        RPCProto.UserRequest userRequest = ProtoBufUtil.getUserInfoRequest(id1);
        byte[] bytes = userService.getUser(userRequest.toByteArray());
        User user = ProtoBufUtil.getUser(bytes);

        if (!StringUtils.isNotEmpty(user.getPhone())) {
            // 没有用户信息, 存储提示信息, 跳转到登录界面
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.sendRedirect(LOGIN_HTML_PATH);
            return;
        }

        // 管理员直接放行
        if (ADMIN.equals(user.getPhone()) && ADMIN.equals(user.getPassword())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // 访问控制
        accessRestrictions(user);

        // 缓存无内容, 加载缓存  获得 权限数组
        if (permissions == null) {
            getPermissions();
        }

        // 判断是否有权限
        try {
            for (String url : permissions) {
                if (thisUrl.contains(url)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
            // 没有权限
            throw new WebException("访问无权限" + thisUrl);
        } catch (IOException | ServletException e) {
            throw new WebException("授权过程中出错", e);
        }
    }


    private void getPermissions() {
        RPCProto.RoleRequest roleRequest = ProtoBufUtil.listPermissionRequest(ORDINARY_USERS_ID);
        byte[] bytes1 = roleService.listPermission(roleRequest.toByteArray());
        RPCProto.RoleResponse.ListPermissionResult listPermissionResult = ProtoBufUtil.listPermissionResponse(bytes1);
        ProtocolStringList permissionList = listPermissionResult.getPermissionList();
        permissions = new String[permissionList.size()];
        for (int i = 0; i < permissionList.size(); i++) {
            permissions[i] = listPermissionResult.getPermission(i);
        }
    }

    private void accessRestrictions(User user) {
        Jedis jedis = null;
        try {
            // 记录用户访问频率
            jedis = JedisUtil.getJedis();
            String key = REDIS_USER_KEY + user.getPhone();
            if (!jedis.exists(key)) {
                jedis.setex(key, EXIST_TIME_SECOND, REDIS_DEFAULT_VALUE);
            }
            Long count = jedis.incr(key);
            // 一分钟内访问次数大于500, 拒绝访问
            if (count > MAXIMUM_VISITS_PER_MINUTE) {
                throw new WebException("访问太频繁");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisUtil.closeJedis(jedis);
        }
    }

    @Override
    public void destroy() {
    }
}

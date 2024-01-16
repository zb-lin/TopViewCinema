package com.lzb.www.controller;


import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lzb.www.exception.WebException;
import com.lzb.www.pojo.vo.Response;
import com.lzb.www.pojo.vo.Result;
import com.lzb.www.util.JWTUtils;
import com.lzb.www.util.type.*;
import rpc.core.register.Register;
import com.lzb.www.annotation.CookieParams;
import com.lzb.www.annotation.File;
import com.lzb.www.annotation.Param;
import sspring.bean.factory.BeanFactory;
import sspring.bean.factory.BeanRegister;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.*;

import static com.lzb.www.constant.Code.ERROR;
import static com.lzb.www.constant.Code.SUCCESS;
import static com.lzb.www.constant.GlobalConstant.*;
import static com.lzb.www.constant.WebConstant.*;


/**
 * 替换HttpServlet, 根据请求的最后一段路径来进行方法分发
 */
public class BaseServlet extends HttpServlet {
    private static final Map<Class<?>, TypeHandler<?>> typeHandlerMap = new HashMap<>();
    private static final Register register = Register.getInstance();
    private static final BeanRegister beanRegister = new BeanRegister(PATH, PROJECT_NAME_LENGTH);
    private static final BeanFactory beanFactory = beanRegister.getBeanFactory();
    /**
     * key: 类名   value: (key: 方法名  value: method)
     */
    private static final Map<String, Map<String, Method>> methodMap = new HashMap<>();
    private static final List<String> subClassNameList = new ArrayList<>();

    static {
        typeHandlerMap.put(Integer.class, new IntegerTypeHandler());
        typeHandlerMap.put(String.class, new StringTypeHandler());
        typeHandlerMap.put(Object.class, new ObjectTypeHandler());
        typeHandlerMap.put(Arrays.class, new ArrayTypeHandler());
        typeHandlerMap.put(Long.class, new LongTypeHandler());
        register.register();
        // 提前加载所有方法
        subClassNameList.add(UserServlet.class.getSimpleName());
        subClassNameList.add(CommentServlet.class.getSimpleName());
        subClassNameList.add(MovieServlet.class.getSimpleName());
        subClassNameList.add(OrderServlet.class.getSimpleName());
        subClassNameList.add(ServiceServlet.class.getSimpleName());
        subClassNameList.add(TicketServlet.class.getSimpleName());
        subClassNameList.forEach(subClassName -> {
            Object bean = beanFactory.getBean(subClassName);
            Map<String, Method> map = new HashMap<>();
            for (Method method : bean.getClass().getMethods()) {
                String name = method.getName();
                map.put(name, method);
            }
            methodMap.put(subClassName, map);
        });
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String uri = request.getRequestURI();
            String methodName = uri.substring(uri.lastIndexOf('/') + 1);
            Class<? extends BaseServlet> subclass = this.getClass();
            Object bean = beanFactory.getBean(subclass);
            Method method = methodMap.get(subclass.getSimpleName()).get(methodName);
            Parameter[] parameters = method.getParameters();
            Object[] params = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Param param = parameters[i].getAnnotation(Param.class);
                if (param != null) {
                    Type type = parameters[i].getParameterizedType();
                    String paramName = param.value();
                    Class<?> beanClass = Class.forName(type.getTypeName());
                    params[i] = getParam(request, paramName, beanClass);
                    continue;
                }
                CookieParams cookieParams = parameters[i].getAnnotation(CookieParams.class);
                if (cookieParams != null) {
                    params[i] = getIdByToken(request);
                    continue;
                }
                File file = parameters[i].getAnnotation(File.class);
                if (file != null) {
                    params[i] = request.getPart(PHOTO_FILE);
                    if (params[i] == null) {
                        return;
                    }
                }
            }
            Object result = method.invoke(bean, params);
            response(response, (Response<?>) result);
        } catch (InvocationTargetException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            Response<Object> result = Response.response(DEFAULT_ERROR_MESSAGE, false);
            response(response, result);
        }
    }

    protected void response(HttpServletResponse response, Response<?> result) throws IOException {
        String jsonString;
        if (result.isResult()) {
            if (result.getSupplier() != null) {
                Object object = result.getSupplier().get();
                if (object instanceof Cookie) {
                    Cookie cookie = (Cookie) object;
                    response.addCookie(cookie);
                    String token = cookie.getValue();
                    Integer id = getIdByToken(token);
                    cookie = new Cookie(ID, String.valueOf(id));
                    cookie.setMaxAge(ONE_DAY);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
            jsonString = JSON.toJSONString(new Result(SUCCESS, result.getSuccessMsg(), result.getData()));
        } else {
            jsonString = JSON.toJSONString(new Result(ERROR, result.getErrorMas(), null));
        }
        response.getWriter().write(jsonString);
    }


    protected String getValueByCookie(HttpServletRequest request, String key) throws UnsupportedEncodingException {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            if (key.equals(name)) {
                String value = cookie.getValue();
                return URLDecoder.decode(value, UTF_8);
            }
        }
        return null;
    }

    protected Integer getIdByToken(String token) throws UnsupportedEncodingException {
        if (!JWTUtils.verify(token)) {
            return null;
        }
        return JWTUtils.getTokenInfo(token).getClaim(ID).asInt();
    }

    protected Integer getIdByToken(HttpServletRequest request) throws UnsupportedEncodingException {
        String token = getValueByCookie(request, TOKEN);
        if (!JWTUtils.verify(token)) {
            return null;
        }
        return JWTUtils.getTokenInfo(token).getClaim(ID).asInt();
    }

    protected <T> T getParam(HttpServletRequest request, String paramName, Class<T> type) {
        String param;
        try {
            switch (type.getSimpleName()) {
                case STRING:
                case INTEGER:
                case LONG:
                    param = request.getParameter(paramName);
                    return (T) typeHandlerMap.get(type).getResult(param);
                case ARRAYS:
                    param = request.getReader().readLine();
                    return (T) typeHandlerMap.get(Arrays.class).getResult(param);
                default:
                    param = request.getReader().readLine();
                    return (T) typeHandlerMap.get(Object.class).getResult(param, type);
            }
        } catch (SQLException | IOException e) {
            throw new WebException("Parameter acquisition error", e);
        }
    }


}
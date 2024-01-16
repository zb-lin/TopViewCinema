package com.lzb.www.controller;

import com.lzb.www.pojo.vo.Response;
import rpc.core.consumer.Service;
import rpc.config.Host;
import rpc.config.ServiceCallDate;
import rpc.config.ServiceProvider;
import rpc.config.SystemCondition;
import sspring.bean.annotation.Autowired;
import sspring.bean.annotation.Hosted;
import sspring.bean.annotation.Import;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

import static com.lzb.www.constant.WebConstant.*;

@Hosted
@Import(packageName = "rpc.core.consumer.ServiceImpl")
@WebServlet("/service/*")
public class ServiceServlet extends BaseServlet {

    @Autowired
    private Service service;

    public Response<?> listService() throws IOException {
        List<ServiceProvider> serviceProviders = service.listServices();
        return Response.response(serviceProviders, LOGIN_SUCCESS, LOGIN_ERROR, serviceProviders != null);
    }

    public Response<?> listHosts() throws IOException {
        List<Host> hosts = service.listConsumerHost();
        return Response.response(hosts, LOGIN_SUCCESS, LOGIN_ERROR, hosts != null);
    }

    public Response<?> listServiceCallDates() throws IOException {
        List<ServiceCallDate> serviceCallDates = service.listServiceCallDates();
        return Response.response(serviceCallDates, LOGIN_SUCCESS, LOGIN_ERROR, serviceCallDates != null);
    }

    public Response<?> getSystemCondition() throws IOException {
        SystemCondition systemCondition = service.getSystemCondition();
        return Response.response(systemCondition, LOGIN_SUCCESS, LOGIN_ERROR, systemCondition != null);
    }

}

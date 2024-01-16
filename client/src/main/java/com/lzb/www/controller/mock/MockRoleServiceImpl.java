package com.lzb.www.controller.mock;

import com.lzb.www.api.RoleService;
import com.lzb.www.proto.RPCProto;
import sspring.bean.annotation.Hosted;

@Hosted
public class MockRoleServiceImpl implements RoleService {

    @Override
    public byte[] listPermission(byte[] bytes) {

        String[] strings = {"/user/login", "/user/getUserByToken", "/user/changePassword",
                "/user/uploadHead", "/ticket/insertTicket", "/ticket/listTicket",
                "/ticket/deleteTicket", "/ticket/updateTicket", "/ticket/updateSeat",
                "/movie/uploadMovie", "/movie/listMovies", "/movie/uploadPoster",
                "/movie/updateMovie", "/movie/deleteMovie",
                "/movie/listMoviesByParam", "/movie/updateEvaluate", "/order/insertOrder", "/order/listOrder", "/order/returnTicket", "/order/deleteOrder",
                "/service/listService", "/service/listHosts", "/service/listServiceCallDates",
                "/service/getSystemCondition", "/js/group.js",
                "/js/service.js", "/group.html", "/login.html", "/service.html", "/ticket.html", "/img",
                "/upload", "/comment/listComment",
        };


        RPCProto.RoleResponse.ListPermissionResult.Builder listPermissionResult = RPCProto.RoleResponse.ListPermissionResult.newBuilder();
        for (String s : strings) {
            listPermissionResult.addPermission(s);
        }
        return RPCProto.RoleResponse.newBuilder().setListPermissionResult(listPermissionResult).build().toByteArray();
    }

}

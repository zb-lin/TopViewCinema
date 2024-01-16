package rpc.core.consumer;


import rpc.config.Host;
import rpc.config.ServiceCallDate;
import rpc.config.ServiceProvider;
import rpc.config.SystemCondition;

import java.util.List;

/**
 * 服务治理接口
 */
public interface Service {
    /**
     * 获得所有服务器信息(服务接口名  ip 端口号  该服务过期时间)
     */
    List<ServiceProvider> listServices();

    /**
     * 获得所有客户端信息(消费者 ip  端口号)
     */
    List<Host> listConsumerHost();

    /**
     * 获得所有服务响应信息(服务名 服务调用的次数 成功率  平均响应时间)
     */
    List<ServiceCallDate> listServiceCallDates();

    /**
     * 获得系统状态(CPU 使用率  内存使用率  系统总内存  系统总内存 )
     */
    SystemCondition getSystemCondition();
}

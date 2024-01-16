package rpc.core.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import rpc.config.*;
import rpc.util.JedisUtil;
import sspring.bean.annotation.Hosted;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static rpc.constants.Constants.*;

@Hosted
public class ServiceImpl implements Service {
    private static final List<String> interfaceList = new ArrayList<>();

    // 提前加载服务名
    static {
        interfaceList.add("UserService");
        interfaceList.add("MovieService");
        interfaceList.add("OrderService");
        interfaceList.add("TicketService");
        interfaceList.add("CommentService");
    }

    @Override
    public List<ServiceProvider> listServices() {
        Jedis jedis = null;
        List<ServiceProvider> serviceProviderList = new ArrayList<>();
        try {
            jedis = JedisUtil.getJedis();
            for (String interfaceName : interfaceList) {
                Map<String, String> map = jedis.hgetAll(REDIS_API_KEY + interfaceName);
                for (String ip : map.keySet()) {
                    ServiceProvider serviceProvider = new ServiceProvider();
                    serviceProvider.setServiceInterface(interfaceName);
                    serviceProvider.setHostname(ip);
                    String jsonString = map.get(ip);
                    RedisData redisData = JSON.parseObject(jsonString, RedisData.class);
                    serviceProvider.setPort((Integer) redisData.getData());
                    serviceProvider.setExpireTime(redisData.getExpireTime());
                    serviceProviderList.add(serviceProvider);
                }
            }
            return serviceProviderList;
        } finally {
            JedisUtil.closeJedis(jedis);
        }
    }

    @Override
    public List<Host> listConsumerHost() {
        Jedis jedis = null;
        List<Host> hosts = new ArrayList<>();
        try {
            jedis = JedisUtil.getJedis();
            Map<String, String> hostMap = jedis.hgetAll(CONSUMER_KEY);
            for (String hostname : hostMap.keySet()) {
                String port = hostMap.get(hostname);
                hosts.add(new Host(hostname, Integer.parseInt(port)));
            }
            return hosts;
        } finally {
            JedisUtil.closeJedis(jedis);
        }
    }

    @Override
    public List<ServiceCallDate> listServiceCallDates() {
        Jedis jedis = null;
        List<ServiceCallDate> serviceCallDates = new ArrayList<>();
        try {
            jedis = JedisUtil.getJedis();
            Map<String, String> hostMap = jedis.hgetAll(SERVICE_CALL_DATE_KEY);
            for (String interfaceName : hostMap.keySet()) {
                String jsonString = hostMap.get(interfaceName);
                ServiceCallDate serviceCallDate = JSON.parseObject(jsonString, ServiceCallDate.class);
                serviceCallDates.add(serviceCallDate);
            }
            return serviceCallDates;
        } finally {
            JedisUtil.closeJedis(jedis);
        }
    }

    @Override
    public SystemCondition getSystemCondition() {
        final long GB = 1024 * 1024 * 1024;
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(operatingSystemMXBean));
        // 总物理内存大小
        Long totalPhysicalMemorySize = jsonObject.getLong("totalPhysicalMemorySize");
        // 可用物理内存大小
        Long freePhysicalMemorySize = jsonObject.getLong("freePhysicalMemorySize");
        // CPU 使用率  100%
        double systemCpuLoad = jsonObject.getDouble("systemCpuLoad") * INT_HUNDRED;
        // 内存使用率  100%
        double memoryUseRatio = (totalPhysicalMemorySize - freePhysicalMemorySize) * DOUBLE_ONE / totalPhysicalMemorySize * INT_HUNDRED;
        // 系统总内存  GB
        double totalMemory = totalPhysicalMemorySize * DOUBLE_ONE / GB;
        // 系统剩余内存  GB
        double freeMemory = freePhysicalMemorySize * DOUBLE_ONE / GB;
        return new SystemCondition(systemCpuLoad, memoryUseRatio, totalMemory, freeMemory);
    }


}
package rpc.core.consumer;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import rpc.config.RedisData;
import rpc.core.provider.CallBack;
import rpc.core.provider.DefaultCallBack;
import rpc.config.Host;
import rpc.config.InvokeData;
import rpc.exception.RPCException;
import rpc.util.JedisUtil;
import sspring.bean.factory.BeanFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static rpc.constants.Constants.*;
import static rpc.constants.Constants.REDIS_API_KEY;

public class RpcHandler implements InvocationHandler {
    private final Class<?> interfaceClass;
    private static final int MAX_RETRIES = 3;
    private final LoadBalance loadBalance;

    public RpcHandler(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
        loadBalance = new LoadBalance();
    }

    private final Client client = new Client();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.currentTimeMillis();
        List<Host> hosts = getHosts();
        // 幂等  每个请求生成唯一的 ID
        String randomUUID = UUID.randomUUID().toString();
        JedisUtil.saveID(randomUUID);
        // 同一请求不同服务器重试
        for (int i = 0; i < MAX_RETRIES; i++) {
            int index = 0;
            try {
                if (hosts.isEmpty()) {
                    break;
                }
                index = loadBalance.random(hosts);
                Host host = hosts.get(index);
                Object result = send(host, method, args, randomUUID);
                long end = System.currentTimeMillis();
                JedisUtil.insertServiceCallDate(interfaceClass.getSimpleName(), SUCCESS, (int) (end - start));
                return result;
            } catch (Exception e) {
                // 同一服务器重试均失败, 移除该服务器的host 和注册中心的注册信息
                Host host = hosts.remove(index);
                Jedis jedis = JedisUtil.getJedis();
                jedis.hdel(REDIS_API_KEY + interfaceClass.getSimpleName(), host.getHostname());
                JedisUtil.closeJedis(jedis);
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        JedisUtil.insertServiceCallDate(interfaceClass.getSimpleName(), ERROR, (int) (end - start));
        return null;
    }

    private Object send(Host host, Method method, Object[] args, String randomUUID) throws Exception {
        // 同一请求同一服务器重试
        for (int i = 0; i < MAX_RETRIES; i++) {
            try {
                InvokeData invokeData = new InvokeData(randomUUID, interfaceClass.getSimpleName(),
                        method.getName(), args[0]);
                CallBack callBack = new DefaultCallBack();
                client.asynchronousCall(host, invokeData, callBack);
                Object result = callBack.getResult();
                // id 请求唯一id  不能删除表示已被删除(请求已执行过)或redis异常
                if (!JedisUtil.deleteID(invokeData.getId())) {
                    throw new RPCException("delete id error");
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 根据接口名获得拥有该服务的服务器ip的端口号
     */
    public List<Host> getHosts() {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            Map<String, String> map = jedis.hgetAll(REDIS_API_KEY + interfaceClass.getSimpleName());
            List<Host> hosts = new ArrayList<>();
            for (String hostname : map.keySet()) {
                String redisDataJSON = map.get(hostname);
                RedisData redisData = JSON.parseObject(redisDataJSON, RedisData.class);
                // 逻辑过期  过期时间可用于展示  但会浪费空间
                LocalDateTime expireTime = redisData.getExpireTime();
                if (expireTime.isAfter(LocalDateTime.now())) {
                    // 未过期
                    String port = redisData.getData().toString();
                    hosts.add(new Host(hostname, Integer.parseInt(port)));
                    continue;
                }
                // 过期删除
                jedis.hdel(REDIS_API_KEY + interfaceClass.getSimpleName(), hostname);
            }
            return hosts;
        } catch (Exception e) {
            throw new RPCException(e);
        } finally {
            JedisUtil.closeJedis(jedis);
        }
    }
}
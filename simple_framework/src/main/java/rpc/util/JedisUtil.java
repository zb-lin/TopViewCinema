package rpc.util;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import rpc.config.ServiceCallDate;

import static rpc.constants.Constants.*;

public class JedisUtil {
    private static final JedisPool jedisPool;
    private static final int PORT = 6379;
    private static final int TIMEOUT = 1000;
    private static final String PASSWORD = "lzb240340";
    private static final int JEDIS_POOL_MAX_TOTAL = 50;
    private static final int JEDIS_POOL_MAX_IDLE = 10;
    private static final int JEDIS_POOL_MIN_IDLE = 5;
    private static final int JEDIS_POOL_MAX_WAIT_MILLIS = 200;
    private static final int REDIS_REQUEST_ID_TIMEOUT = 60;
    private static final String REDIS_REQUEST_ID_VALUE = "1";


    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(JEDIS_POOL_MAX_TOTAL);
        jedisPoolConfig.setMaxIdle(JEDIS_POOL_MAX_IDLE);
        jedisPoolConfig.setMinIdle(JEDIS_POOL_MIN_IDLE);
        jedisPoolConfig.setMaxWaitMillis(JEDIS_POOL_MAX_WAIT_MILLIS);
        jedisPool = new JedisPool(jedisPoolConfig, IP, PORT, TIMEOUT, PASSWORD);
    }

    public static Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();
        jedis.auth(PASSWORD);
        jedis.select(0);
        return jedis;
    }

    public static void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 幂等性校验id
     */
    public static void saveID(String id) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.setex(REDIS_REQUEST_ID_KEY + id, REDIS_REQUEST_ID_TIMEOUT, REDIS_REQUEST_ID_VALUE);
        } finally {
            JedisUtil.closeJedis(jedis);
        }
    }

    /**
     * 获取幂等性id
     */
    public static boolean getID(String id) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(REDIS_REQUEST_ID_KEY + id);
        } finally {
            JedisUtil.closeJedis(jedis);
        }
    }

    /**
     * 删除幂等性id
     */
    public static boolean deleteID(String id) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(REDIS_REQUEST_ID_KEY + id)) {
                jedis.del(REDIS_REQUEST_ID_KEY + id);
                return true;
            }
            return false;
        } finally {
            JedisUtil.closeJedis(jedis);
        }
    }

    /**
     * 生成服务调用情况
     */
    public static void insertServiceCallDate(String interfaceName, int status, int timeout) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String serviceCallDateString = jedis.hget(SERVICE_CALL_DATE_KEY, interfaceName);
            ServiceCallDate serviceCallDate1 = JSON.parseObject(serviceCallDateString, ServiceCallDate.class);
            int callCount = 1;
            double successRate = 0;
            if (serviceCallDateString != null) {
                callCount = serviceCallDate1.getCallCount() + INT_ONE;
                successRate = serviceCallDate1.getSuccessRate() * DOUBLE_ONE / INT_HUNDRED;
                if (status == SUCCESS) {
                    successRate = (successRate * (callCount - INT_ONE) + INT_ONE) / callCount;
                } else {
                    successRate = (successRate * (callCount - INT_ONE)) / callCount;
                }
                timeout = (serviceCallDate1.getTimeout() * (callCount - INT_ONE) + timeout) / callCount;
            } else {
                if (status == SUCCESS) {
                    successRate = 1;
                }
            }
            ServiceCallDate serviceCallDate = new ServiceCallDate(interfaceName, callCount, (int) (successRate * INT_HUNDRED), timeout);
            jedis.hset(SERVICE_CALL_DATE_KEY, serviceCallDate.getInterfaceName(), JSON.toJSONString(serviceCallDate));
        } finally {
            JedisUtil.closeJedis(jedis);
        }
    }

}

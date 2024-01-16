package rpc.core.register;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import rpc.config.RedisData;
import rpc.constants.Constants;
import rpc.util.JedisUtil;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static rpc.constants.Constants.*;


public class Register {
    private static final Map<String, Class<?>> map = new HashMap<>();
    private static final String PORT = "8080";

    private static class Instance {
        private static final Register INSTANCE = new Register();
    }

    private Register() {
    }

    public static Register getInstance() {
        return Instance.INSTANCE;
    }

    public void register(String interfaceName, Class<?> implClass, int port, String ip) {
        map.put(interfaceName, implClass);
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            RedisData redisData = new RedisData();
            redisData.setData(port);
            redisData.setExpireTime(LocalDateTime.now().plusSeconds(Constants.NOW_PLUS_SECONDS));
            jedis.hset(REDIS_API_KEY + interfaceName, ip, JSON.toJSONString(redisData));
        } finally {
            JedisUtil.closeJedis(jedis);
        }
    }


    public void register() {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            jedis.hset(CONSUMER_KEY, IP, PORT);
        } finally {
            JedisUtil.closeJedis(jedis);
        }
    }

    public static Class<?> get(String interfaceName) {
        return map.get(interfaceName);
    }


}

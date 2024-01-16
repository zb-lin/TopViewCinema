package rpc.util;

import redis.clients.jedis.Jedis;

import java.util.Collections;

public class RedisLock {
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final int EXPIRE_TIME = 100;
    private static final String LOCK_SUCCESS = "OK";
    private static final String LOCK_KEY = "lock:";
    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 阻塞式获得锁  超时返回
     */
    public static boolean getLock(Long ticketId, String value) {
        Jedis jedis = null;
        long start = System.currentTimeMillis();
        try {
            jedis = JedisUtil.getJedis();
            String result = jedis.set(LOCK_KEY + ticketId, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, EXPIRE_TIME);
            while (!LOCK_SUCCESS.equals(result)) {
                long end = System.currentTimeMillis();
                if (end - start > 100) {
                    return false;
                }
                result = jedis.set(LOCK_KEY + ticketId, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, EXPIRE_TIME);
            }
            return true;
        } finally {
            JedisUtil.closeJedis(jedis);
        }
    }

//    /**
//     * 释放锁   可以基于Lua脚本
//     */
//    public static void releaseLock(Long ticketId, String value) {
//        Jedis jedis = null;
//        String lockKey = LOCK_KEY + ticketId;
//        try {
//            jedis = JedisUtil.getJedis();
//            String result = jedis.get(lockKey);
//            if (value.equals(result)) {
//                jedis.del(lockKey);
//            }
//        } finally {
//            JedisUtil.closeJedis(jedis);
//        }
//    }

    /**
     * 释放锁   基于Lua脚本
     */
    public static boolean releaseLock(Long ticketId, String value) {
        Jedis jedis = null;
        String lockKey = LOCK_KEY + ticketId;
        try {
            jedis = JedisUtil.getJedis();
            // Lua脚本: 首先获取锁对应的value值, 检查是否与value相等, 如果相等则删除锁
            // 参数KEYS[1]赋值为lockKey, ARGV[1]赋值为value
            // 成功返回 1   不成功返回  0
            String script = "if (redis.call('GET', KEYS[1]) == ARGV[1]) then" +
                    " return redis.call('DEL', KEYS[1]) end return 0";
            Object result = jedis.eval(script, Collections.singletonList(lockKey),
                    Collections.singletonList(value));
            return RELEASE_SUCCESS == result;
        } finally {
            JedisUtil.closeJedis(jedis);
        }
    }
}

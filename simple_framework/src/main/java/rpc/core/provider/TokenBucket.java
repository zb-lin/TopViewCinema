package rpc.core.provider;

import rpc.exception.RPCException;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 令牌桶, 限流
 */
public class TokenBucket {

    private static class Instance {
        private static final TokenBucket INSTANCE = new TokenBucket();

    }

    private TokenBucket() {
    }

    public static TokenBucket getInstance() {
        return Instance.INSTANCE;
    }

    /**
     * 令牌桶容量
     */
    private static final int limit = 100;

    private static final int TOKEN_BUCKET_VALUE = 1;
    /**
     * 令牌生成间隔时间
     */
    private static final int timeout = 2;
    private final ArrayBlockingQueue<Integer> tokenBucketQueue = new ArrayBlockingQueue<>(limit);


    public void init() {
        for (int i = 0; i < limit; i++) {
            tokenBucketQueue.offer(TOKEN_BUCKET_VALUE);
        }
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(timeout);
                } catch (InterruptedException e) {
                    throw new RPCException(e);
                }
                // 在队尾添加, 成功返回true, 队列已满返回false
                tokenBucketQueue.offer(TOKEN_BUCKET_VALUE);
            }
        }).start();
    }

    /**
     * poll(), 获取并删除队列头元素, 队列没有数据就返回null
     */
    public boolean poll() {
        return tokenBucketQueue.poll() != null;
    }

    /**
     * take() 从队列头部删除, 队列没有元素就阻塞, 可中断
     */
    public void take() {
        try {
            tokenBucketQueue.take();
        } catch (InterruptedException e) {
            throw new RPCException(e);
        }
    }


    /**
     * 信号量通常用于限制线程数, 而不是访问某些资源
     * 多个共享资源互斥的使用 并发限流, 控制最大的线程数
     */
    private final Semaphore semaphore = new Semaphore(100);

    public void acquire() {
        try {
            // 阻塞获取
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RPCException(e);
        }
    }

    public void release() {
        semaphore.release();
    }


}

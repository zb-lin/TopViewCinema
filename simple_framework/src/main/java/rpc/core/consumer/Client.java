package rpc.core.consumer;


import com.google.protobuf.ByteString;
import com.lzb.www.proto.InvokeDataProto;
import rpc.core.provider.CallBack;
import rpc.config.Host;
import rpc.config.InvokeData;
import rpc.util.IOUtil;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.util.concurrent.*;

import static rpc.constants.Constants.error_msg;


public class Client {
    /**
     * 返回的是可用的计算资源
     */
    private static final int nThreads = Runtime.getRuntime().availableProcessors() * 2;
    public static final ExecutorService THREADS_POOL = new ThreadPoolExecutor(nThreads, nThreads,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    private final SocketPool socketPool = SocketPool.getInstance();
    private static final int TIMEOUT = 10;

    /**
     * 异步调用  线程池提交任务  Future获取结果
     */
    public <T extends CallBack> void asynchronousCall(Host url, InvokeData invokeData, T callback) throws Exception {
        Future<Object> future = THREADS_POOL.submit(() -> socketClient(invokeData, url));
        Object result = future.get(TIMEOUT, TimeUnit.SECONDS);
        callback.saveResult(result);
    }

    /**
     * 长连接  InvokeData: id: 请求id 幂等   RpcRequestByteArray: 方法参数经过protobuf处理后的byte[]
     */
    private Object socketClient(InvokeData invokeData, Host host) throws Exception {
        SSLSocket socket = socketPool.getSocket(host);
        if (socket == null) {
            return null;
        }
        OutputStream outputStream = socket.getOutputStream();
        InvokeDataProto.InvokeData builder = InvokeDataProto.InvokeData.newBuilder()
                .setId(invokeData.getId())
                .setInterfaceName(invokeData.getInterfaceName())
                .setMethodName(invokeData.getMethodName())
                .setRpcRequestByteArray(ByteString.copyFrom((byte[]) invokeData.getRpcRequestByteArray())).build();
        byte[] byteArray = builder.toByteArray();
        outputStream.write(byteArray);
        outputStream.flush();

        InputStream inputStream = socket.getInputStream();
        byte[] bytes = IOUtil.readStream(inputStream);
        String msg = new String(bytes);
        if (error_msg.equals(msg)) {
            return null;
        }
        return bytes;
    }

}

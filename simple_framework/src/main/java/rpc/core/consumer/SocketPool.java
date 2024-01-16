package rpc.core.consumer;

import com.lzb.www.proto.InvokeDataProto;
import rpc.config.Host;
import rpc.exception.RPCException;
import rpc.util.IOUtil;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class SocketPool {
    private static final String SSL_CONTEXT_INSTANCE_NAME = "TLSv1.2";
    private static final String TRUST_MANAGER_INSTANCE_NAME = "SunX509";
    private static final String CERTIFICATE_INSTANCE_TYPE = "X.509";
    private static final String SERVER_CER_PATH = "C:/Users/86177/Desktop/TopViewCinema/server.cer";

    private static final Map<String, SSLSocket> socketPool = new ConcurrentHashMap<>();
    private volatile boolean isStart = false;

    private static class Instance {
        private static final SocketPool INSTANCE = new SocketPool();
    }

    private SocketPool() {
    }

    public static SocketPool getInstance() {
        return Instance.INSTANCE;
    }

    /**
     * 重连接池中获得socket 没有则新建
     */
    public SSLSocket getSocket(Host host) {
        SSLSocket socket;
        try {
            String socketPoolKey = host.getHostname() + "-" + host.getPort();
            socket = socketPool.get(socketPoolKey);
            if (socket == null) {
                setSocket(host);
                socket = socketPool.get(socketPoolKey);
            }
            socket.setKeepAlive(true);
            return socket;
        } catch (SocketException e) {
            e.printStackTrace();
            return null;
        }
    }


    public SSLSocket getSocket(String hostname, Integer port) {
        return getSocket(new Host(hostname, port));
    }

    public synchronized void setSocket(Host host) {
        try {
            String socketPoolKey = host.getHostname() + "-" + host.getPort();
            if (socketPool.get(socketPoolKey) == null) {
                socketPool.put(socketPoolKey, (SSLSocket) getSSLContext()
                        .getSocketFactory().createSocket(host.getHostname(), host.getPort()));
                keepAlive();
            }
        } catch (IOException | NoSuchAlgorithmException | KeyStoreException | CertificateException |
                 KeyManagementException e) {
            e.printStackTrace();
        }
    }

    /**
     * 心跳检测
     */
    private void keepAlive() {
        // 判断是否已经开始 未开始则结束
        if (isStart) {
            return;
        }
        isStart = true;
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (String key : socketPool.keySet()) {
                    try {
                        System.out.println("发送心跳");
                        SSLSocket socket = socketPool.get(key);
                        socket.setSoTimeout(5000);
                        OutputStream outputStream = socket.getOutputStream();
                        InvokeDataProto.InvokeData builder = InvokeDataProto.InvokeData.newBuilder()
                                .setMethodName("1")
                                .build();
                        byte[] byteArray = builder.toByteArray();
                        outputStream.write(byteArray);
                        outputStream.flush();

                        InputStream inputStream = socket.getInputStream();
                        byte[] bytes = IOUtil.readStream(inputStream);

                        if (bytes.length != 1) {
                            System.out.println("连接断开");
                            SSLSocket sslSocket = socketPool.remove(key);
                            sslSocket.close();
                        }
                    } catch (IOException e) {
                        SSLSocket sslSocket = socketPool.remove(key);
                        if (socketPool.size() == 0) {
                            isStart = false;
                            break;
                        }
                        try {
                            sslSocket.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private SSLContext getSSLContext() throws
            NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance(SSL_CONTEXT_INSTANCE_NAME);
        // 生成信任证书Manager, 默认系统会信任CA机构颁发的证书, 自定的证书需要手动的加载
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TRUST_MANAGER_INSTANCE_NAME);
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);
        // 生成验证工厂
        CertificateFactory certificateFactory = CertificateFactory.getInstance(CERTIFICATE_INSTANCE_TYPE);
        // 生成别名
        String alias = Integer.toString(0);
        // 加载证书
        keyStore.setCertificateEntry(alias, certificateFactory.generateCertificate(
                Files.newInputStream(Paths.get(SERVER_CER_PATH))));
        trustManagerFactory.init(keyStore);
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        return sslContext;
    }

}

package rpc.core.provider;


import com.lzb.www.proto.InvokeDataProto;
import rpc.config.InvokeData;
import rpc.util.IOUtil;
import rpc.util.JedisUtil;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;

import static rpc.constants.Constants.ERROR_MSG;
import static rpc.constants.Constants.PORT;

public class Server {
    private static final Invoker invoker = Invoker.getInstance();
    private static final TokenBucket tokenBucket = TokenBucket.getInstance();
    private static final String SSL_CONTEXT_INSTANCE_NAME = "TLSv1.2";
    private static final String SERVER_KS_PATH = "C:/Users/86177/Desktop/TopViewCinema/server_ks.jks";
    private static final String SERVER_PASSWORD = "server_password";
    private static final String HEARTBEAT_DETECTION = "1";

    public void server() {
        socketServer();
    }


    private void socketServer() {
        // 获取SSLContext的SocketFactory
        try (SSLServerSocket serverSocket = getSSLServerSocket()) {
            // 是否开启双向验证
            serverSocket.setNeedClientAuth(false);
            Socket socket = serverSocket.accept();
            while (true) {
                tokenBucket.take();
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                byte[] bytes = IOUtil.readStream(inputStream);

                InvokeDataProto.InvokeData invokeData = InvokeDataProto.InvokeData.parseFrom(bytes);
                // 心跳检测
                if (HEARTBEAT_DETECTION.equals(invokeData.getMethodName())) {
                    outputStream.write(1);
                    outputStream.flush();
                    continue;
                }
                // 幂等性校验
                if (!JedisUtil.getID(invokeData.getId())) {
                    outputStream.write(ERROR_MSG.getBytes());
                    outputStream.flush();
                    continue;
                }
                InvokeData invokeData1 = new InvokeData(invokeData.getId(), invokeData.getInterfaceName(),
                        invokeData.getMethodName(), invokeData.getRpcRequestByteArray().toByteArray());
                Object result = invoker.invoker(invokeData1);
                if (result == null) {
                    outputStream.write(ERROR_MSG.getBytes());
                } else {
                    outputStream.write((byte[]) result);
                }
                outputStream.flush();
            }
        } catch (IOException | UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException |
                 CertificateException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private SSLServerSocket getSSLServerSocket() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, IOException, CertificateException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance(SSL_CONTEXT_INSTANCE_NAME);
        // 生成秘钥的manager
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        // 加载秘钥
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(Files.newInputStream(Paths.get(SERVER_KS_PATH)), SERVER_PASSWORD.toCharArray());
        // 秘钥初始化
        keyManagerFactory.init(keyStore, SERVER_PASSWORD.toCharArray());
        // 初始化SSLContext
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);
        return (SSLServerSocket) sslContext.getServerSocketFactory().createServerSocket(PORT);
    }
}



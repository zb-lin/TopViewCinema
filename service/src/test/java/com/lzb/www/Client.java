package com.lzb.www;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;


public class Client {

    public static void main(String[] args) throws Exception {

        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        //生成信任证书Manager,默认系统会信任CA机构颁发的证书,自定的证书需要手动的加载
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(null);
        //生成验证工厂
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        //生成别名(可以随便填写)
        String certificateAlias = Integer.toString(0);
        //加载证书
        keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(
                Files.newInputStream(Paths.get("C:/Users/86177/Desktop/TopViewCinema/server.cer"))));
        //初始化
        trustManagerFactory.init(keyStore);
        //初始化
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        SSLSocket socket = (SSLSocket) sslContext.getSocketFactory().createSocket("localhost", 8888);
        socket.setKeepAlive(true);


        //与服务端进行通信
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(1);
        outputStream.flush();
        outputStream.close();

    }
}

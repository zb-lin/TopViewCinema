package com.lzb.www;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import javax.net.ssl.TrustManagerFactory;


public class Server {


    public static void main(String[] args) throws Exception {

        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        //生成秘钥的manager
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        //加载秘钥
        KeyStore keyStoreOne = KeyStore.getInstance("JKS");
        keyStoreOne.load(Files.newInputStream(Paths.get("C:/Users/86177/Desktop/TopViewCinema/server_ks.jks")), "server_password".toCharArray());
        //秘钥初始化
        keyManagerFactory.init(keyStoreOne, "server_password".toCharArray());
        //初始化SSLContext
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);
        //获取SSLContext的SocketFactory
        SSLServerSocket serverSocket = (SSLServerSocket) sslContext.getServerSocketFactory().createServerSocket(8888);
        //是否开启双向验证
        serverSocket.setNeedClientAuth(false);


        while (true) {
            try {
                //等待客户端连接
                SSLSocket socket = (SSLSocket) serverSocket.accept();
                InputStream in = socket.getInputStream();
                int read = in.read();
                System.out.println(read);
                byte[] buf = new byte[1024];
                int len = in.read(buf);
                System.out.println(new String(buf, 0, len));
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

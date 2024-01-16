package com.lzb.www;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzb.www.service.UserService;
import com.lzb.www.util.StringUtils;
import lorm.factory.query.Query;
import lorm.factory.query.QueryFactory;
import org.junit.jupiter.api.Test;
import rpc.core.consumer.ProxyFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.io.InputStream;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import static com.lzb.www.constant.GlobalConstant.ADMIN;

public class UserTest {
    protected Query query = QueryFactory.createQuery();


    @Test
    public void test() {
//        String sql = "select * from tb_user where id = 1";
//        List<Object> user = query.queryRows(sql, User.class, null);
//        System.out.println(user.size());
//
//        String s = "1323";
//        String substring = s.substring(1, s.length() - 1);
//        System.out.println(substring);

        String newOptionalSeats = "[7,8,9,10,11,12,13,1,2]";
        String[] newOptionalSeatsArray = newOptionalSeats.substring(1, newOptionalSeats.length() - 1).split(",");
        int[] array = new int[newOptionalSeatsArray.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = Integer.parseInt(newOptionalSeatsArray[i]);
        }
        Arrays.sort(array);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < array.length; i++) {
            if (i == array.length - 1) {
                stringBuilder.append(array[i]).append("]");
            } else {
                stringBuilder.append(array[i]).append(",");
            }
        }
        System.out.println(stringBuilder);


    }

    @Test
    public void test01() {
        String phone = "177962148";
        String password = "admin";
        boolean legal = isLegal(phone, password);
        System.out.println(legal);

    }

    public boolean isLegal(String phone, String password) {
        if (!StringUtils.isPhone(phone)) {
            // 不是手机
            // 是admin
            System.out.println(1);
            return ADMIN.equals(phone) && ADMIN.equals(password);
        } else {
            System.out.println(2);
            return true;
        }
    }


    private final static Map<Integer, String> map = new ConcurrentHashMap<>();

    @Test
    public void test2() {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                map.put(finalI, String.valueOf(1));
                System.out.println(Thread.currentThread().getId() + "--" + map);
            }).start();
        }
    }

    @Test
    public void test3() {
        String oldOptionalSeats = "[1,2,3,4,5,6,7,8]";
        String seat = "1";
        String[] oldOptionalSeatsArr = oldOptionalSeats.substring(1, oldOptionalSeats.length() - 1).split(",");
        Stream<String> nowOptionalSeats = Arrays.stream(oldOptionalSeatsArr).filter(element -> !element.equals(seat));
        Object[] nowOptionalSeatsArray = nowOptionalSeats.toArray();
        if (oldOptionalSeatsArr.length == nowOptionalSeatsArray.length) {
            System.out.println(false);
        }
    }

    private final UserService userService = ProxyFactory.getProxy(UserService.class);

    private static int nThreads = Runtime.getRuntime().availableProcessors() * 2;

    @Test
    public void test4() throws ClassNotFoundException, NoSuchMethodException {

        System.out.println(nThreads);

    }

    @Test
    public void test5() throws ClassNotFoundException, NoSuchMethodException {
//        String str = "&";
//        System.out.println(Arrays.toString(str.getBytes()));
//        byte[] bytes = {10, -29, 1, 123, 34, 105, 110, 116, 101, 114, 102, 97, 99, 101, 78, 97,
//                109, 101, 34, 58, 34, 85, 115, 101, 114, 83, 101, 114, 118, 105, 99, 101, 34, 44,
//                34, 109, 101, 116, 104, 111, 100, 78, 97, 109, 101, 34, 58, 34, 108, 111, 103, 105,
//                110, 34, 44, 34, 112, 97, 114, 97, 109, 84, 121, 112, 101, 34, 58, 91, 34, 106, 97,
//                118, 97, 46, 108, 97, 110, 103, 46, 83, 116, 114, 105, 110, 103, 34, 44, 34, 106, 97,
//                118, 97, 46, 108, 97, 110, 103, 46, 83, 116, 114, 105, 110, 103, 34, 93, 44, 34, 112,
//                97, 114, 97, 109, 115, 34, 58, 91, 34, 49, 55, 55, 57, 54, 50, 49, 52, 56, 48, 53, 34,
//                44, 34, 49, 50, 51, 34, 93, 44, 34, 114, 101, 116, 117, 114, 110, 84, 121, 112, 101, 34,
//                58, 123, 34, 97, 110, 110, 111, 116, 97, 116, 105, 111, 110, 115, 34, 58, 91, 93, 44, 34,
//                100, 101, 99, 108, 97, 114, 101, 100, 65, 110, 110, 111, 116, 97, 116, 105, 111, 110, 115,
//                34, 58, 91, 93, 44, 34, 116, 121, 112, 101, 34, 58, 34, 106, 97, 118, 97, 120, 46, 115, 101,
//                114, 118, 108, 101, 116, 46, 104, 116, 116, 112, 46, 67, 111, 111, 107, 105, 101, 34, 125, 125};
//        int index = contains(bytes, (byte) 38);
//        System.out.println(index);
        String str = "hello";
        str = str.substring(0, str.length() - 1);
        System.out.println(str);


    }

    private static int contains(byte[] bytes, byte b) {
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == b) {
                return i;
            }
        }
        return -1;
    }

    @Test
    public void test06() throws Exception {
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, null, null);
        SSLSocketFactory factory = (SSLSocketFactory) context.getSocketFactory();
        SSLSocket socket = (SSLSocket) factory.createSocket();
        String[] protocols = socket.getSupportedProtocols();
        System.out.println("Supported Protocols: " + protocols.length);
        for (int i = 0; i < protocols.length; i++) {
            System.out.println(protocols[i]);
        }
        protocols = socket.getEnabledProtocols();
        System.out.println("Enabled Protocols: " + protocols.length);
        for (int i = 0; i < protocols.length; i++) {
            System.out.println(protocols[i]);
        }

    }

    @Test
    public void testRunServer() throws Exception {
//        //获取SSL上下文
//        SSLContext context = SSLContext.getInstance("SSL");
//        String keyStorePassword = "password";
//        //获取服务端KeyStore
//        KeyStore serverKeys = getKeyStore("serverKeys", "jks", keyStorePassword);
//        //获取KeyManagerFactory
//        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//        String privateKeyPassword = "password";
//        //初始化KeyManagerFactory
//        keyManagerFactory.init(serverKeys, privateKeyPassword.toCharArray());
//
//        //获取TrustManagerFactory
//        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//        String trustStorePassword = "password";
//        //获取服务端信任KeyStore
//        KeyStore serverTrustKeys = getKeyStore("serverTrust", "jks", trustStorePassword);
//        //初始化TrustManagerFactory
//        trustManagerFactory.init(serverTrustKeys);
//        //初始化SSL上下文
//        context.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
//
//        //使用SSL上下文获取SSLServerSocketFactory
//        SSLServerSocketFactory ssf = (SSLServerSocketFactory) context.getServerSocketFactory();


        SSLServerSocket serverSocket = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(9999);
        //使用SSLServerSocketFactory创建出SSLServerSocket，并监听指定端口
//        SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(9999);
        //设置需要对客户端进行认证
        serverSocket.setNeedClientAuth(true);

        while (true) {
            try {
                //等待客户端连接
                SSLSocket socket = (SSLSocket) serverSocket.accept();
                InputStream in = socket.getInputStream();

                byte[] buf = new byte[1024];
                int len = in.read(buf);
                System.out.println(new String(buf, 0, len));
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void testRunClient() throws Exception {
        String defaultAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
        System.out.println(defaultAlgorithm);
        String defaultAlgorithm1 = TrustManagerFactory.getDefaultAlgorithm();
        System.out.println(defaultAlgorithm1);
        String defaultType = KeyStore.getDefaultType();
        System.out.println(defaultType);

    }

    public static final ExecutorService THREADS_POOL = Executors.newFixedThreadPool(nThreads);


    private void keepAliveTest() {
        System.out.println(1);
        THREADS_POOL.submit(() -> {
            System.out.println(2);
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("hello");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void test6() {
        keepAliveTest();
    }

    @Test
    public void test7() {
        int evaluate1 = 47;
        int evaluateCount = 8;
        int evaluate = 4;
        double evaluate2 = (evaluate1 / 10.0 * evaluateCount + evaluate) / (evaluateCount + 1);
        String format = String.format("%.1f", evaluate2);
        System.out.println((int) (Double.parseDouble(format) * 10));
    }

    @Test
    public void test8() {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        String osJson = JSON.toJSONString(operatingSystemMXBean);
        JSONObject jsonObject = JSON.parseObject(osJson);
        System.out.println(jsonObject);
    }

    @Test
    public void test9() {
        final long GB = 1024 * 1024 * 1024;
        while (true) {
            OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
            String osJson = JSON.toJSONString(operatingSystemMXBean);
//            System.out.println("osJson is " + osJson);
            JSONObject jsonObject = JSON.parseObject(osJson);
            double processCpuLoad = jsonObject.getDouble("processCpuLoad") * 100;
            double systemCpuLoad = jsonObject.getDouble("systemCpuLoad") * 100;
            Long totalPhysicalMemorySize = jsonObject.getLong("totalPhysicalMemorySize");
            Long freePhysicalMemorySize = jsonObject.getLong("freePhysicalMemorySize");
            double totalMemory = 1.0 * totalPhysicalMemorySize / GB;
            double freeMemory = 1.0 * freePhysicalMemorySize / GB;
            double memoryUseRatio = 1.0 * (totalPhysicalMemorySize - freePhysicalMemorySize) / totalPhysicalMemorySize * 100;

            String result = "系统CPU占用率: " +
                    twoDecimal(systemCpuLoad) +
                    "%，内存占用率：" +
                    twoDecimal(memoryUseRatio) +
                    "%，系统总内存：" +
                    twoDecimal(totalMemory) +
                    "GB，系统剩余内存：" +
                    twoDecimal(freeMemory) +
                    "GB，该进程占用CPU：" +
                    twoDecimal(processCpuLoad) +
                    "%";
            System.out.println(result);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public double twoDecimal(double doubleValue) {
        // setScale(1,BigDecimal.ROUND_HALF_UP)四舍五入，2.35变成2.4
        BigDecimal bigDecimal = new BigDecimal(doubleValue).setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    @Test
    public void test10() throws NoSuchFieldException {
//        String fieldName = "orderService";
//        Field field = MiddleOrderServiceImpl.class.getDeclaredField(fieldName);
//        field.setAccessible(true);
//        System.out.println(field);

        System.out.println(com.lzb.www.api.UserService.class);
    }

}

import org.junit.jupiter.api.Test;
import com.lzb.www.annotation.Param;
import redis.clients.jedis.Jedis;
import rpc.util.JedisUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServletTest {
    @Test
    public void test01() throws Exception {


//        Class<?> valueClass = Class.forName("com.lzb.www.controller.UserServlet");
//        System.out.println(valueClass + "--1");
//
//        Field field = valueClass.getDeclaredField("userService");
//
//        System.out.println(field.getType() + "--2");
//        Class<?> fieldClass = Class.forName("com.lzb.www.api.UserService");
//        System.out.println(fieldClass + "--3");


        Jedis jedis = JedisUtil.getJedis();
        jedis.set("test", "0");
        String s = jedis.get("test");
        System.out.println(s);
        Long test = jedis.incr("test");
        System.out.println(test);

        Vector<Integer> vector = new Vector<>();
        vector.add(1);

        List<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
    }
}

package rpc.core.consumer;


import rpc.config.Host;

import java.util.List;
import java.util.Random;


public class LoadBalance {
    private static final Random rand = new Random();


    private static int POLLING_COUNT = 0;


    /**
     * 随机  如 list.size() == 0  random [0,1)
     */
    public int random(List<Host> list) {
        return rand.nextInt(list.size());
    }

    /**
     * 轮询  每次加一, 到底还原
     */
    public int polling(List<Host> list) {
        if (POLLING_COUNT == list.size()) {
            POLLING_COUNT = 0;
        }
        int temp = POLLING_COUNT;
        POLLING_COUNT++;
        return temp;
    }


}

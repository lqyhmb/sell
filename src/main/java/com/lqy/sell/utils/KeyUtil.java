package com.lqy.sell.utils;

import java.util.Random;

/**
 * Created by Rodriguez
 * 2018/7/13 9:19
 */
public class KeyUtil {

    /**
     * 生成唯一的主键
     *
     * @return
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }

}

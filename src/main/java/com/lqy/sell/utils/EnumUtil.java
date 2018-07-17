package com.lqy.sell.utils;

import com.lqy.sell.enums.CodeEnum;

/**
 * Created by Rodriguez
 * 2018/7/17 8:25
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }

}

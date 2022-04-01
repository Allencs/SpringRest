package com.allen.util;

import java.util.UUID;

public class UuidUtil {

    public static String getStrUuid() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}

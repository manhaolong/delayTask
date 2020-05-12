package com.ismarthealth.delaytask.server.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxk
 * @description 对应来源和spring中的bean class
 */
public class ChildClassConstants {
    public static Map<String, Class> childClassMap;
    static {
        childClassMap = new HashMap<>();
    }
}

package com.mca.client.utils;

import java.util.HashSet;
import java.util.Set;

public class ClassUtils {

    public static boolean isWrapperType(Class clazz) {
        return ClassUtils.getWrapperTypes().contains(clazz);
    }

    public static Set<Class<?>> getWrapperTypes()
    {
        Set<Class<?>> ret = new HashSet<>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        ret.add(String.class);
        return ret;
    }
}

package com.sbt.javaschool.lesson6;

import java.lang.reflect.*;
import java.util.*;

public class Reflection {
    private Class clazz;

    public Reflection(Object o) {
        clazz = o.getClass();
    }

    public Reflection(Class clazz) {
        this.clazz = clazz;
    }

    public String getAllMethods() {
        return getAllMethodsClass(clazz, null, "All methods of class: ").toString();
    }

    public String getAllGetters() {
        return getAllMethodsClass(clazz, Arrays.asList("is", "get"), "All getters of class: ").toString();
    }

    private boolean suitableMethod(List<String> suitablePrefix, String methodName) {
        for (String prefix : suitablePrefix) {
            if (methodName.startsWith(prefix)) return true;
        }
        return false;
    }

    private StringBuilder getAllMethodsClass(Class clazz, List<String> suitablePrefix, String prefixString) {
        StringBuilder sb = new StringBuilder();
        if (clazz == null) return sb;
        sb.append(prefixString);
        sb.append(clazz);
        sb.append('\n');

        for (Method oneMethod : clazz.getDeclaredMethods()) {
            if (suitablePrefix == null || suitableMethod(suitablePrefix, oneMethod.getName())) {
                sb.append("Method: ");
                sb.append(oneMethod.getName());
                sb.append("(");
                Class[] paramTypes = oneMethod.getParameterTypes();
                for (int i = 0; i < paramTypes.length; i++) {
                    sb.append(paramTypes[i].getName());
                    if (i != paramTypes.length - 1) sb.append(", ");
                }
                sb.append(")\n");
            }
        }
        sb.append(getAllMethodsClass(clazz.getSuperclass(), suitablePrefix, prefixString));
        return sb;
    }

    private boolean checkAllConstantsClass(Class clazz) {
        if (clazz == null) return true;

        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType() == String.class) {
                int modifier = field.getModifiers();
                if (Modifier.isFinal(modifier)) {
                    field.setAccessible(true);
                    try {
                        Object obj = clazz.newInstance();
                        if (!field.getName().equals(field.get(obj))) return false;
                    } catch (IllegalAccessException | InstantiationException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

        return checkAllConstantsClass(clazz.getSuperclass());

    }

    public boolean checkAllConstants() {
        return checkAllConstantsClass(this.clazz);
    }
}

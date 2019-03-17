package com.sbt.javaschool.lesson6;

import java.lang.reflect.*;
import java.util.*;

public class BeanUtils {
    /**
     *      * Scans object "from" for all getters. If object "to"
     *      * contains correspondent setter, it will invoke it
     *      * to set property value for "to" which equals to the property
     *      * of "from".
     *      * <p/>
     *      * The type in setter should be compatible to the value returned
     *      * by getter (if not, no invocation performed).
     *      * Compatible means that parameter type in setter should
     *      * be the same or be superclass of the return type of the getter.
     *      * <p/>
     *      * The method takes care only about public methods.
     *      *
     *      * @param to   Object which properties will be set.
     *      * @param from Object which properties will be used to get values.
     *      
     */
    public static void assign(Object to, Object from) {
        if (to == null || from == null) return;

        ArrayList<Method> gettersFrom = getAllMethodsClass(from.getClass(), Arrays.asList("get", "is"),
                Collections.singletonList("getClass"));
        ArrayList<Method> settersTo = getAllMethodsClass(to.getClass(), Collections.singletonList("set"), null);
        try {
            for (Method getFrom : gettersFrom) {
                suitableMethodToFrom(getFrom, settersTo, to, from);
            }
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }
    }

    private static void suitableMethodToFrom(Method getFrom, ArrayList<Method> settersTo, Object to, Object from)
            throws IllegalAccessException, InvocationTargetException {
        String method = getFrom.getName();
        method = method.startsWith("get") ? "set" + method.substring(3) : "set" + method.substring(2);
        Class classGetter = getFrom.getReturnType();

        for (Method setter : settersTo) {
            if (setter.getName().equals(method)) {
                Class[] setterClasses = setter.getParameterTypes();
                if (getFrom.getParameterTypes().length == 0 && setterClasses.length == 1) {
                    Object fromResult = getFrom.invoke(from);
                    Class classSetter = setterClasses[0];
                    if (classSetter.equals(classGetter) || classSetter.isInstance(fromResult)) {
                        setter.invoke(to, fromResult);
                    }
                }
            }
        }

    }

    private static boolean suitableMethod(List<String> suitablePrefix, String methodName, List<String> exceptMethods) {
        for (String prefix : suitablePrefix) {
            if (methodName.startsWith(prefix) && (exceptMethods == null || !exceptMethods.contains(methodName)))
                return true;
        }
        return false;
    }

    private static ArrayList<Method> getAllMethodsClass(Class clazz, List<String> suitablePrefix,
                                                        List<String> exceptMethods) {
        ArrayList<Method> methods = new ArrayList<>();

        for (Method oneMethod : clazz.getMethods()) {
            if (suitablePrefix == null || suitableMethod(suitablePrefix, oneMethod.getName(), exceptMethods)) {
                methods.add(oneMethod);
            }
        }

        return methods;
    }
}

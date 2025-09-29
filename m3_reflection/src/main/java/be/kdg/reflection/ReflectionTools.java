package be.kdg.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionTools {
    public static void classAnalysis(Class<?> aClass) {
        System.out.println("=".repeat(30));
        System.out.println("Class Analysis: Introspection");
        System.out.println("Class: " + aClass.getName());
        System.out.println("Super Class: " + aClass.getSuperclass().getName());
        System.out.println("Package: " + aClass.getPackageName());
        for (Class<?> interfaceClass : aClass.getInterfaces()) {
            System.out.println("Interface: " + interfaceClass.getName());
        }
        for (Constructor<?> constructor: aClass.getDeclaredConstructors())
    }
}

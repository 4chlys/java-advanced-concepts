package be.kdg.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionTools {
    public static void classAnalysis(Class<?> aClass) {
        System.out.println("=".repeat(30));
        System.out.println("Class Analysis: Introspection");
        //a. Volledige Klassenaam ("Fully Qualified Name")
        System.out.println("Class: " + aClass.getName());

        //b. Naam van de superklasse
        System.out.println("Super Class: " + (aClass.getSuperclass().getName().isEmpty()? "No Super Class" : aClass.getSuperclass().getName()));

        //c. Naam van de package
        System.out.println("Package: " + aClass.getPackageName());

        //d. Gebruikte interfaces
        if (aClass.getInterfaces().length > 0) {
            for (Class<?> interfaceClass : aClass.getInterfaces()) {
                System.out.println("Interface: " + interfaceClass.getName());
            }
        } else System.out.println("No Interfaces");

        //e. Aanwezige constructors
        for (Constructor<?> constructor: aClass.getDeclaredConstructors()){
            System.out.println("Constructor: " + constructor.toGenericString());
        }

        //f. Namen van de private attributen met tussen haakjes hun type.
        for (Field field: aClass.getDeclaredFields()) {
            System.out.printf("Field: %s (%s)", field.getName(), field.getType().getName());
        }

        //g. Getters, setters en andere methoden
        for (Method method: aClass.getDeclaredMethods()) {
            System.out.println("Method: " + method.getName());
        }
    }
}

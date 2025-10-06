package be.kdg.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReflectionTools {

    /**
     * Analyzes one or more classes using reflection and prints detailed information.
     * Uses varargs to accept multiple Class objects.
     *
     * @param classes One or more Class objects to analyze
     */
    public static void classAnalysis(Class<?>... classes) {
        for (Class<?> aClass : classes) {
            analyzeClass(aClass);
        }
    }

    /**
     * Creates an instance of the given class and runs all methods annotated with @CanRun
     * that have a single String parameter.
     *
     * @param aClass The class to instantiate and run annotated methods on
     * @return The created object with annotated methods executed
     * @throws Exception if instantiation or method invocation fails
     */
    public static Object runAnnotated(Class<?> aClass) throws Exception {
        // a. Maak een nieuwe instantie aan via de default constructor
        Constructor<?> defaultConstructor = aClass.getDeclaredConstructor();
        Object instance = defaultConstructor.newInstance();

        // b. Overloop alle methoden en selecteer die met @CanRun en één String parameter
        Method[] methods = aClass.getDeclaredMethods();

        for (Method method : methods) {
            // Check if method has @CanRun annotation
            if (method.isAnnotationPresent(CanRun.class)) {
                // Check if method has exactly one parameter and it's a String
                Type[] paramTypes = method.getGenericParameterTypes();
                if (paramTypes.length == 1 && paramTypes[0].equals(String.class)) {
                    // c. Voer de methode uit met de value van de annotation
                    CanRun annotation = method.getAnnotation(CanRun.class);
                    String value = annotation.value();
                    method.invoke(instance, value);
                }
            }
        }

        // d. Retourneer het gecreëerde object
        return instance;
    }

    /**
     * Analyzes a single class and prints its structure.
     *
     * @param aClass The class to analyze
     */
    private static void analyzeClass(Class<?> aClass) {
        System.out.println("\nAnalyse van de klasse: " + aClass.getSimpleName());
        System.out.println("=".repeat(47));

        // a. Volledige Klassenaam ("Fully Qualified Name")
        System.out.println("Fully qualified name : " + aClass.getName());

        // b. Naam van de superklasse
        String superClassName = aClass.getSuperclass() != null ?
                aClass.getSuperclass().getSimpleName() : "None";
        System.out.println("Naam van de superklasse: " + superClassName);

        // c. Naam van de package
        System.out.println("Naam van de package : " + aClass.getPackageName());

        // d. Gebruikte interfaces
        System.out.print("Interfaces: ");
        Class<?>[] interfaces = aClass.getInterfaces();
        if (interfaces.length > 0) {
            for (int i = 0; i < interfaces.length; i++) {
                System.out.print(interfaces[i].getSimpleName());
                if (i < interfaces.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        } else {
            System.out.println("(none)");
        }

        // e. Aanwezige constructors
        System.out.println("Constructors:");
        Constructor<?>[] constructors = aClass.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println("  " + constructor.toGenericString());
        }

        // f. Namen van de private attributen met tussen haakjes hun type
        System.out.print("attributen : ");
        Field[] fields = aClass.getDeclaredFields();
        List<String> fieldNames = new ArrayList<>();
        for (Field field : fields) {
            String fieldType = field.getType().getSimpleName();
            fieldNames.add(field.getName() + "(" + fieldType + ")");
        }
        if (fieldNames.isEmpty()) {
            System.out.println("(none)");
        } else {
            System.out.println(String.join(" ", fieldNames));
        }

        // g. Getters, setters en andere methoden
        Method[] methods = aClass.getDeclaredMethods();
        List<String> getters = new ArrayList<>();
        List<String> setters = new ArrayList<>();
        List<String> others = new ArrayList<>();

        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get") && method.getParameterCount() == 0) {
                getters.add(methodName);
            } else if (methodName.startsWith("set") && method.getParameterCount() == 1) {
                setters.add(methodName);
            } else if (methodName.startsWith("is") && method.getParameterCount() == 0) {
                getters.add(methodName);
            } else {
                others.add(methodName);
            }
        }

        System.out.print("getters : ");
        System.out.println(getters.isEmpty() ? "(none)" : String.join(" ", getters));

        System.out.print("setters : ");
        System.out.println(setters.isEmpty() ? "(none)" : String.join(" ", setters));

        System.out.print("andere methoden: ");
        System.out.println(others.isEmpty() ? "(none)" : String.join(" ", others));
    }
}
package be.kdg.reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods that can be run automatically with a default value.
 * This is a single-value annotation with a String value parameter.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CanRun {
    /**
     * The value to pass as parameter when running the annotated method.
     * Default value is "dummy".
     */
    String value() default "dummy";
}
package cz.cuni.mff.java.semestr4.hnetCviceni.c10.formAnnotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FormField {
    String name();
    FieldKind kind() default FieldKind.TEXT;
}

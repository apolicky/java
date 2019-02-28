package cz.cuni.mff.java.semestr4.cv02;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TesterInfo {
    public enum Priority{
        LOW, MEDIUM, HIGH;
    }

    String createdBy() default "adam";
    String lastModified() default "01/01/2019";
    Priority priority() default Priority.MEDIUM;

}

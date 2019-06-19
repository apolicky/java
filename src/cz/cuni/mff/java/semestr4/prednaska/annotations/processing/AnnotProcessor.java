package cz.cuni.mff.java.semestr4.prednaska.annotations.processing;

import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;

@SupportedAnnotationTypes(value = {"*"})
@SupportedSourceVersion(SourceVersion.RELEASE_10)
public class AnnotProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement element : annotations) {
            System.out.println(element.getQualifiedName());
            System.out.println("  " + roundEnv.getElementsAnnotatedWith(element));
        }
        return true;
    }
}

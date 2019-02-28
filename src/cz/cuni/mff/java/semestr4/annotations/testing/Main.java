package cz.cuni.mff.java.semestr4.annotations.testing;

import java.lang.reflect.*;

public class Main {

    public static void main(String[] args) throws Exception {

        int passed = 0;
        int failed = 0;

        for (Method m : Class.forName("cz.cuni.mff.java.semestr4.annotations.testing.Foo").getMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                try {
                    m.invoke(null);
                    passed++;
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    System.out.printf("Test %s failed: %s %n", m, ex.getCause());
                    failed++;
                }
            }
        }
        System.out.printf("Passed: %d, Failed %d%n", passed, failed);
    }
}

package cz.cuni.mff.java.semestr4.hnetCviceni.c02;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Testing {

    private static class ClassInfo {
        Priority priority;
        Class<?> clazz;
        TesterInfo ti;
        Method[] before;
        Method[] after;
        Method[] tests;

        public ClassInfo(Priority priority, Class<?> clazz, TesterInfo ti, List<Method> before, List<Method> after, List<Method> tests) {
            this.priority = priority;
            this.clazz = clazz;
            this.ti = ti;
            this.before = before.toArray(new Method[before.size()]);
            this.after = after.toArray(new Method[after.size()]);
            this.tests = tests.toArray(new Method[tests.size()]);
        }

        public Priority getPriority() {
            return priority;
        }

        public Class<?> getClazz() {
            return clazz;
        }

        public String createdBy() {
            return ti != null ? ti.createdBy() : "N/A";
        }

        public String lastModified() {
            return ti != null ? ti.lastModified() : "N/A";
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Nothing to test. Exiting.");
            System.exit(0);
        }

        int passed = 0, failed = 0, skipped = 0;
        List<ClassInfo> classes = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Path.of(args[0]));
            for (String className : lines) {
                Class<?> clazz = Class.forName(className.trim());

                List<Method> beforeMethods = new ArrayList<>();
                List<Method> afterMethods = new ArrayList<>();
                List<Method> testMethods = new ArrayList<>();
                for (Method m : clazz.getMethods()) {
                    if (m.isAnnotationPresent(Before.class)) {
                        beforeMethods.add(m);
                    } else if (m.isAnnotationPresent(After.class)) {
                        afterMethods.add(m);
                    } else if (m.isAnnotationPresent(Test.class)) {
                        testMethods.add(m);
                    }
                }

                if (clazz.isAnnotationPresent(TesterInfo.class)) {
                    TesterInfo ti = clazz.getAnnotation(TesterInfo.class);
                    classes.add(new ClassInfo(ti.priority(), clazz, ti, beforeMethods, afterMethods, testMethods));
                } else {
                    classes.add(new ClassInfo(Priority.LOW, clazz, null, beforeMethods, afterMethods, testMethods));
                }
            }

            classes.sort(Comparator.comparingInt(a -> -a.getPriority().getVal()));

            for (ClassInfo classInfo : classes) {
                Class<?> clazz = classInfo.getClazz();

                System.out.println("Processing class: " + clazz.getName());
                System.out.println("Tester: " + classInfo.createdBy());
                System.out.println("Last modified: " + classInfo.lastModified());

                Object obj = clazz.getConstructor().newInstance();
                for (Method m : classInfo.tests) {

                    Test annot = m.getAnnotation(Test.class);
                    if (annot.enabled()) {
                        try {
                            for (Method mBefore: classInfo.before) {
                                mBefore.invoke(obj);
                            }
                            m.invoke(obj);
                            for (Method mAfter: classInfo.after) {
                                mAfter.invoke(obj);
                            }
                            if (annot.expectedExceptions().length != 0) {
                                failed++;
                                System.out.printf("Test %s failed, exception expected\n", m.getName());
                            } else {
                                passed++;
                                System.out.printf("Test %s passed\n", m.getName());
                            }
                        } catch (Throwable ex) {
                            if (annot.expectedExceptions().length != 0) {
                                if (ex instanceof InvocationTargetException) {
                                    boolean ok = false;
                                    for (Class<? extends Throwable> exClass : annot.expectedExceptions()) {
                                        if (exClass.isAssignableFrom(ex.getCause().getClass())) {
                                            passed++;
                                            System.out.printf("Test %s passed with expected exception %s\n", m.getName(), exClass);
                                            ok = true;
                                            break;
                                        }
                                    }
                                    if (!ok) {
                                        failed++;
                                        System.out.printf("Test %s failed with unexpected exception %s\n", m.getName(), ex.getCause().getClass());
                                    }
                                }
                            } else {
                                System.out.printf("Test %s failed: %s %n", m.getName(), ex.getCause());
                                failed++;
                            }
                        }
                    } else {
                        skipped++;
                        System.out.printf("Test %s skipped\n", m.getName());
                    }

                }
                System.out.println("=========================================================");
            }
            System.out.printf("Passed: %d, Failed %d, Skipped %d\n", passed, failed, skipped);
        } catch (IOException ex) {
            System.out.println(ex);
            System.out.println("Cannot read input file");
            System.exit(1);
        }
    }
}

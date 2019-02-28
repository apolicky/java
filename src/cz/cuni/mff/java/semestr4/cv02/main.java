package cz.cuni.mff.java.semestr4.cv02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) throws ClassNotFoundException{

        int passed = 0;
        int failed = 0;

        List<TestIinf> tests=new ArrayList<>();

        List<String> namesOfTests = new ArrayList<>();
        namesOfTests.add("cz.cuni.mff.java.semestr4.cv02.SimpleTest");

        tests=loadTests(TestIinf.class,namesOfTests);

        /*
        try(BufferedReader FR = new BufferedReader(new FileReader(args[0]))){

            String nameOfProcessor="";
            while((nameOfProcessor=FR.readLine())!=null){
                namesOfTests.add(nameOfProcessor);
            }
            tests=loadTests(Test.class,namesOfTests);
        }
        catch (IOException e){
            System.out.println("Error during file reading: " + e);
        }*/


        for(int i=0;i<tests.size();i++) {
            TestIinf t = tests.get(i);
            for (Method m : t.getClass().getDeclaredMethods()) {
                /*if(
                        try {
                    m.invoke(null);
                    passed++;
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    System.out.printf("Test %s failed: %s %n", m, ex.getCause());
                    failed++;
                }
                */


                System.out.println();
                //System.out.println("sthing");
                System.out.println(m.getName());
            }
        }

        //System.out.println(tests.size());
        System.out.printf("Passed: %d, Failed %d%n", passed, failed);

    }


    static <T> List<T> loadTests(Class<T> IfaceName, List<String> testNames){
        List<T> tests = new ArrayList<>();

        for(String name : testNames){
            try {
                Class<?> cls = Class.forName(name);
                if (cls.isArray() || cls.isInterface() || cls.isPrimitive() || cls.isEnum()) {
                    System.out.println(cls.getName() + " is not a class.");
                    continue;
                }
                if (!IfaceName.isAssignableFrom(cls)) {
                    System.out.println("The class " + cls.getName() + " does not implement the interface Test.");
                    continue;
                }
                tests.add((T) cls.getDeclaredConstructor().newInstance());
                //System.out.println("load," + tests.size());
            } catch (ClassNotFoundException e) {
                System.out.println("The class " + name + " does not exists.");
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.out.println("It is not possible to instantiate " + name);
            }
        }
        return tests;
    }
}
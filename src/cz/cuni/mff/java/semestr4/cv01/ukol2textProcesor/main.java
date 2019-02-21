package cz.cuni.mff.java.semestr4.cv01.ukol2textProcesor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args){
        String fileName="";
        if(args.length==1){
            fileName=args[0];
        }
        else{
            System.out.println("Wrong number of parameters. Wanted just one name of file to read.");
        }

        List<String> namesOfProcessors = new ArrayList<>();
        List<TextProcessor> textProcessors = new ArrayList<>();

        try(BufferedReader FR = new BufferedReader(new FileReader(fileName))){

            String nameOfProcessor="";
            while((nameOfProcessor=FR.readLine())!=null){
                namesOfProcessors.add(nameOfProcessor);
            }
            textProcessors=loadProcessors(TextProcessor.class,namesOfProcessors);
        }
        catch (IOException e){
            System.out.println("Error during file reading: " + e);
        }

        textProcessors.forEach(tP -> {tP.process("processing this");});

    }

    static <T> List<T> loadProcessors(Class<T> IfaceName, List<String> processorsNames){
        List<T> processors = new ArrayList<>();

        for(String name : processorsNames){
            try {
                Class<?> cls = Class.forName(name);
                if (cls.isArray() || cls.isInterface() || cls.isPrimitive() || cls.isEnum() || cls.isAnnotation()) {
                    System.out.println(cls.getName() + " is not a class.");
                    continue;
                }
                if (!IfaceName.isAssignableFrom(cls)) {
                    System.out.println("The class " + cls.getName() + " does not implement the interface TextProcessor.");
                    continue;
                }
                processors.add((T) cls.getDeclaredConstructor().newInstance());
            } catch (ClassNotFoundException e) {
                System.out.println("The class " + name + " does not exists.");
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.out.println("It is not possible to instantiate " + name);
            }
        }
        return processors;
    }

}

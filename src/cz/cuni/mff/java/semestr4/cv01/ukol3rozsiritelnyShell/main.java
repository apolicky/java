package cz.cuni.mff.java.semestr4.cv01.ukol3rozsiritelnyShell;

import cz.cuni.mff.java.semestr4.cv01.ukol2textProcesor.TextProcessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {
    public static void main(String[] args) {

        //reading command names from a file.
        String fileName="";
        if(args.length==1){
            fileName=args[0];

            List<String> namesOfCommands = new ArrayList<>();
            List<Command> commands = new ArrayList<>();

            try(BufferedReader FR = new BufferedReader(new FileReader(fileName))){

                String nameOfCommand="";
                while((nameOfCommand=FR.readLine())!=null){
                    namesOfCommands.add(nameOfCommand);
                }
                commands=loadCommands(Command.class,namesOfCommands);
            }
            catch (IOException e){
                System.out.println("Error during file reading: " + e);
            }

            //i've got all the possible commands loaded
            process(commands);
        }
        else{
            System.out.println("Wrong number of parameters. Wanted just one name of file to read.");
        }

    }

    static <T> List<T> loadCommands(Class<T> tClass, List<String> namesOfCommands){
        List<T> commands = new ArrayList<>();

        for(String name : namesOfCommands){
            try {
                Class<?> cls = Class.forName(name);
                if (cls.isArray() || cls.isInterface() || cls.isPrimitive() || cls.isEnum() || cls.isAnnotation()) {
                    System.out.println(cls.getName() + " is not a class.");
                    continue;
                }
                if (!tClass.isAssignableFrom(cls)) {
                    System.out.println("The class " + cls.getName() + " does not implement the interface Command.");
                    continue;
                }
                commands.add((T) cls.getDeclaredConstructor().newInstance());
            } catch (ClassNotFoundException e) {
                System.out.println("The class " + name + " does not exists.");
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.out.println("It is not possible to instantiate " + name);
            }
        }
        return commands;
    }

    static void process(List<Command> commands){
        try(BufferedReader BR = new BufferedReader(new InputStreamReader(System.in))) {
            String line = "";

            while ((line = BR.readLine()) != null) {
                if(line.equals("^D")){
                    break;
                }
                else{
                    if(line.equals("help")){
                        help(commands);
                    }
                    else if(line.split(" ")[0].equals("help")){
                        for(Command c : commands){
                            if(c.getCommand().equals(line.split(" ")[1])){
                                c.getCommand();
                            }
                        }
                    }
                    else{
                        String[] namePlusAgrs=line.split(" ");
                        for(Command c : commands){
                            if(c.getCommand().equals(namePlusAgrs[0])){
                                c.execute(Arrays.copyOfRange(namePlusAgrs,1,namePlusAgrs.length-1));
                            }
                        }
                    }
                }

            }
        }
        catch (IOException e){
            System.out.println("Error while executing shell.");

        }
    }

    static void help(List<Command> commands){
        commands.forEach(command -> {command.getCommand();});
    }
}

package cz.cuni.mff.java.semestr3.cv09;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class TodoSeznam {
    public static void main(String[] args){
        if(args.length==0){
            System.out.println("Zadny argument!");
            System.exit(2);
        }
        Path fN = Paths.get(System.getProperty("user.home"),".todo");
        readTodos(fN);
        switch (args[0]){
            case "-l":
                listThem();
                //listTodos(false);
                break;
            case "-r":
                reverseListThem();
                //listTodos(true);
                break;
            case "-a":
                addTodo(args);
                writeTodos(fN);
                break;
            case "-d":
                break;
                default:
                    System.out.println("Spatne zadana akce!!!");
        }
    }

        private static void listThem(){
            for(var i : todos){
                System.out.println(i.toString());
            }
        }

        private static void reverseListThem(){
            for(int i = todos.size()-1;i>=0;i--){
                System.out.println(todos.get(i).toString());
            }
        }

        private static void listTodos(boolean reverse){
            if(!reverse){
                todos.forEach(System.out::println);
            }
            else{
                ListIterator<Item> it = todos.listIterator(todos.size());
                while(it.hasPrevious()){
                    System.out.println(it.previous());
                }
            }
        }

        private static void addTodo(String[] args){
            if(args.length <3){
                System.out.println("malo parametru");
                System.exit(3);
            }
            int pri;
            try{
                pri = Integer.parseInt(args[1]);

            }
            catch (NumberFormatException e){
                pri = 0;
            }

            String text = Arrays.stream(args,2,args.length).collect(Collectors.joining(" "));
            Item i = new Item(pri,text);
            boolean added = false;
            for (int j = 0;j<todos.size();j++){
                if(todos.get(j).priority < pri){
                    todos.add(i);
                    added=true;
                    break;
                }
                if(!added){
                    todos.add(i);
                }
            }
        }

        private static class Item{
            int priority;
            String text;

            public Item(int pri, String t){
                this.priority = pri;
                this.text = t;
            }

            public Item(String line){
                String[] parts = line.split(":");
                try{
                    priority=Integer.parseInt(parts[0]);
                }
                catch(NumberFormatException e){
                   throw new IllegalArgumentException("bad line",e);
                }
                text= Arrays.stream(parts,1,parts.length).collect(Collectors.joining(":"));
            }

            @Override
            public String toString(){
                return priority + ":" +text;
            }
        }

        private static List<Item> todos;

        private static void readTodos(Path fileName){
            if(!Files.exists(fileName)){
                System.out.println("Seznam neni :-/");
                todos=new ArrayList<>();
            }
            else{
                try{
                    todos = Files.lines(fileName).map(Item::new).collect(Collectors.toList());
                }
                catch(IOException e){
                    System.out.println("nelze precist");
                    System.exit(1);
                }
            }
        }

        private static void writeTodos(Path fileName){
            List<String> lines = todos.stream().map(Item::toString).collect(Collectors.toList());
            try{
                Files.write(fileName,lines);
            }
            catch (IOException e){
                System.out.println("nelze zapsat");
                System.exit(1);
            }
        }
}

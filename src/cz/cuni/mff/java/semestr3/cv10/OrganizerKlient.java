package cz.cuni.mff.java.semestr3.cv10;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class OrganizerKlient {
    public static void main(String[] args) {
        if(args.length==0){
            System.out.println("Zadny argument!");
            System.exit(2);
        }
        switch (args[0]) {
            case "-l":
                listTodos(false);
                break;
            case "-r":
                listTodos(true);
                break;
            case "-a":
                //writeTodos(fN);
                break;
            case "-d":
                break;
            default:
                System.out.println("Spatne zadana akce!!!");
        }
    }

    private static void listTodos(boolean reverse){
        try(Socket s = new Socket(InetAddress.getByName(null),6666)){
            ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(s.getInputStream());

            os.writeObject(Command.LIST);
            List<Item> list = (List<Item>) is.readObject();

            if (!reverse) {
                list.forEach(System.out::println);
            } else {
                ListIterator<Item> it = list.listIterator(list.size());
                while (it.hasPrevious()) {
                    System.out.println(it.previous());
                }
            }
        }
        catch(IOException e){
            Logger.getLogger(OrganizerServer.class.getName()).log(Level.SEVERE,"chybka se stala", e);
            System.exit(1);
        }
        catch(ClassNotFoundException ecnf){
            Logger.getLogger(OrganizerServer.class.getName()).log(Level.SEVERE,"chybka se stala", ecnf);
            System.exit(1);
        }

    }

    private static void addTodo(String[] args){
        if(args.length < 3){
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

        try(Socket s = new Socket(InetAddress.getByName(null),6666)){
            ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(s.getInputStream());

            os.writeObject(Command.ADD);
            os.writeObject(i);

            Result res = (Result) is.readObject();


            if(res == Result.OK){
                System.out.println("ok");
            }


        }
        catch(IOException e){
            Logger.getLogger(OrganizerServer.class.getName()).log(Level.SEVERE,"chybka se stala", e);
            System.exit(1);
        }
        catch(ClassNotFoundException ecnf){
            Logger.getLogger(OrganizerServer.class.getName()).log(Level.SEVERE,"chybka se stala", ecnf);
            System.exit(1);
        }
        catch(ClassCastException ecc){

        }
    }

}

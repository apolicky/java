package cz.cuni.mff.java.semestr3.cv10;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class OrganizerServer {

    private static List<Item> todos;

    public static void main(String[] Args){
        Path p = Paths.get(System.getProperty("user.name",".todo"));
        readTodos(p);

        try(ServerSocket SS= new ServerSocket(6666)){

            while(true){
                Socket S = SS.accept();
                Thread t = new ServeConnection(S);
                t.start();
            }

        }
        catch(IOException ex){
            Logger.getLogger(OrganizerServer.class.getName()).log(Level.SEVERE,"chybka se stala", ex);
            System.exit(1);
        }
    }

    private static class ServeConnection extends Thread{
        private Socket s;

        public ServeConnection(Socket s){
            this.s = s;
        }

        @Override
        public void run(){
            try(ObjectInputStream IS = new ObjectInputStream(s.getInputStream())){
                ObjectOutputStream OS = new ObjectOutputStream(s.getOutputStream());

                Object o = IS.readObject();
                if(o instanceof Command){
                    switch((Command) o){
                        case LIST:
                            OS.writeObject(todos);
                            break;
                        case ADD:
                            addTodo(IS,OS);
                            break;
                    }
                }
                else{
                    Logger.getLogger(OrganizerServer.class.getName()).log(Level.SEVERE,"chyba, spatny prikaz");
                    System.exit(3);
                }
            }
            catch(IOException e){
                Logger.getLogger(OrganizerServer.class.getName()).log(Level.SEVERE,"chyba, neotevrel se stream",e);
                return;
            }
            catch(ClassNotFoundException ecnf){
                Logger.getLogger(OrganizerServer.class.getName()).log(Level.SEVERE,"chyba, neprecteno",ecnf);
                return;
            }
        }
    }

    private static synchronized void addTodo(ObjectInputStream is, ObjectOutputStream os) throws IOException{
        try{
            Item i = (Item) is.readObject();
            boolean added = false;
            for (int j = 0;j<todos.size();j++){
                if(todos.get(j).priority < i.priority){
                    todos.add(i);
                    added=true;
                    break;
                }
                if(!added){
                    todos.add(i);
                }
            }
            os.writeObject(Result.OK);
        }
        catch(ClassNotFoundException ecnf){
            os.writeObject(Result.FAIL);
            System.exit(2);
        }


    }

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
                System.out.println("nelze precist prikazy do /todos/");
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

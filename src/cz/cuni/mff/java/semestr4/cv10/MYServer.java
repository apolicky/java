package cz.cuni.mff.java.semestr4.cv10;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MYServer {
    MYServer(int port){
        //tridy = new ArrayList<>();
        try {
            socket = new ServerSocket(port);
        }
        catch (IOException e){
            System.out.println("error");
        }
    }

    public void run(){
        while(true){
            try(Socket s = socket.accept()){
                System.out.println("conn acc.");


            }
            catch (IOException e){
                System.out.println(e);
            }
        }
    }

    public void register( String jmeno){
        //tridy.add(nova);
        System.out.println("registered " + jmeno);
    }

    private static ServerSocket socket;
    //private ArrayList<MYBeanImpl> tridy;
}

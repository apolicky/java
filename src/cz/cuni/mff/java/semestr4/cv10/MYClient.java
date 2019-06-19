package cz.cuni.mff.java.semestr4.cv10;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class MYClient {
    public static void main(String[] args) {

        //MYServer server = new MYServer(8080);
        //server.register("MyBean");

        try(Socket s = new Socket("localhost",8080)){
            new PrintWriter(s.getOutputStream()).println("hey");


        }
        catch(IOException e){

        }


    }
}

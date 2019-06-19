package cz.cuni.mff.java.semestr4.cv10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MYServer2 {
    public static void main(String[] args){
        try(ServerSocket serverSocket = new ServerSocket(8080)){
            while(true){
                try(Socket rem = serverSocket.accept()){
                    System.out.println("conn accepted.");
                    BufferedReader BR = new BufferedReader(new InputStreamReader(rem.getInputStream()));

                    //BR.readLine());

                    System.out.println(BR.readLine());



                }
                catch (IOException e){
                    System.out.println("exc" + e);
                }
            }
        }
        catch (IOException e){
            System.out.println("Exception "+ e);
        }
    }
}

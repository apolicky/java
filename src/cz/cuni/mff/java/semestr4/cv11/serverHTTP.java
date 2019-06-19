package cz.cuni.mff.java.semestr4.cv11;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class serverHTTP extends Thread {

    private static Socket sok;

    serverHTTP(Socket s){
        sok = s;
    }

    public static void main(String[] args){

 /*       try(ServerSocket S = new ServerSocket(8080)){

            while(true){
                Socket socket = S.accept();
                Thread t = new serverHTTP(socket);
                t.start();
            }

        }
        catch(IOException e){
            System.out.println("error "+ e);

         }*/

        try{
            Class c = Class.forName("cz.cuni.mff.java.semestr4.cv11.serverHTTP");
            Method[] methods = c.getDeclaredMethods();

            for(Method m : methods){
                System.out.println(m.getName());

                if(m.getName() == "hello"){
                    String answer = m.invoke(c.newInstance(),null).toString();
                    System.out.println("asnwer: " + answer);
                }
            }

        }
        catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException e){
            System.out.println("error " + e);
        }

    }

    public void run(){
        try(BufferedReader BR = new BufferedReader(new InputStreamReader(sok.getInputStream()));
            OutputStream os = sok.getOutputStream()){

            String line =  BR.readLine();
            System.out.println(line);

            Method_To_Call m = parseLine(line);

            String answ2 = invokeMethod(m);

            System.out.println(answ2);

            String answer = "HTTP/1.0 200 Ok\nContent-Type: text/html\n\n<html><head><title>Answer</title></head><body>Hello my friend</body></html>";

            os.write(answer.getBytes());

        }
        catch (IOException ex){}
    }

    public String invokeMethod(Method_To_Call m){

        System.out.println("invoking "+ m.getMethodName());

        try{
            Object answ;

            Class c = Class.forName(m.getClassName());

            // contains method
            Method[] methods = c.getDeclaredMethods();
            //Method wanted_method;

            for ( Method met : methods){
                if (met.getName() == m.getMethodName()){
                    answ = met.invoke(m.getParameters());
                    return answ.toString();
                }
            }


        }
        catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException e){

        }


        return "Fail :-(";
    }

    public Method_To_Call parseLine(String line) {

        System.out.println("parsing: " + line);

        Pattern starting_of_line = Pattern.compile("^GET");
        Matcher matcher = starting_of_line.matcher(line);



        if (matcher.find()) {
            System.out.println("matches");

            String new_line = line.replaceFirst("GET /", "").replaceAll(" HTTP/.*", "");
            System.out.println(new_line);

            String class_name = new_line.split("/")[0];
            String method_name = new_line.split("/")[1].split("\\?")[0];

            System.out.println(class_name + " " + method_name);

            String[] params_and_values = new_line.split("/")[1].split("\\?")[1].split("&");
            ArrayList<String> values_of_params = new ArrayList<>();

            for (String p_a_v : params_and_values) {
                values_of_params.add(p_a_v.split("=")[1]);
            }

            Method_To_Call m = new Method_To_Call(class_name, method_name);

            for (String param : values_of_params) {
                m.addParam(param);
            }

            return m;
        }
        else {
            return null;
        }

    }

    public String hello(){
        return "hello";
    }
}


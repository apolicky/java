package cz.cuni.mff.java.semestr4.cv03.shellAndServiceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ServiceLoader;

public class main {
    public static void main(String[] args) {

        //reading command names from a file.
        String filename="";
        if(args.length==1){
            filename=args[0];
        }
        else{
            System.out.println("Wrong number of parameters.");
            System.exit(1);
        }

        ServiceLoader<Command> sl = ServiceLoader.load(Command.class);

        process(sl);



    }

    static void process(ServiceLoader<Command> serviceLoader){
        try(BufferedReader BR = new BufferedReader(new InputStreamReader(System.in))) {
            String line = "";

            while ((line = BR.readLine()) != null) {
                if(line.equals("^D")){
                    break;
                }
                else{
                    if(line.equals("help")){
                        help(serviceLoader);
                    }
                    else if(line.split(" ")[0].equals("help")){
                        for(cz.cuni.mff.java.semestr4.cv03.shellAndServiceLoader.Command c : serviceLoader){
                            if(c.getCommand().equals(line.split(" ")[1])){
                                c.getHelp();
                            }
                        }
                    }
                    else{
                        String[] namePlusAgrs=line.split(" ");
                        for(Command c : serviceLoader){
                            if(c.getCommand().equals(namePlusAgrs[0])){
                                System.out.println(c.execute(Arrays.copyOfRange(namePlusAgrs,1,namePlusAgrs.length-1)));
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

    static void help(ServiceLoader<cz.cuni.mff.java.semestr4.cv03.shellAndServiceLoader.Command> sl){
        for(Command c : sl){
            System.out.println(c.getCommand());
        }
        sl.forEach(c->System.out.println(c.getCommand()));
    }
}

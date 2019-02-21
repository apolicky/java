package cz.cuni.mff.java.semestr3.cv07;

import java.io.*;
import java.util.Scanner;

public class addUser {
    public static void main(String[] args) throws  IOException{
        try(FileInputStream IF = new FileInputStream("C:\\Users\\adamp\\IdeaProjects\\Java\\src\\cz\\cuni\\mff\\java\\cv07\\FakeEtcPasswd.txt")){

            try (FileOutputStream OF = new FileOutputStream("C:\\Users\\adamp\\IdeaProjects\\Java\\src\\cz\\cuni\\mff\\java\\cv07\\tmpEtcPasswd.txt",false)){
                byte[] b = new byte[1024];
                while(IF.read(b,0,1024)>0){
                    OF.write(b);
                }
            }
            catch (IOException e){

            }
        }
        catch (IOException ex){
            System.out.println("chyba!");
        }
        //^zkopiruj etc

        BufferedReader R = new BufferedReader(new InputStreamReader(System.in));
        String mozneJmeno;
        boolean databazeJmenoObsahuje;

        do {
            databazeJmenoObsahuje=false;
            System.out.println("Napis jmeno, ktere bys chtel mit:");
            mozneJmeno = R.readLine();

            Scanner etcPasswdScanner = new Scanner(new FileInputStream("C:\\\\Users\\\\adamp\\\\IdeaProjects\\\\Java\\\\src\\\\cz\\\\cuni\\\\mff\\\\java\\\\cv07\\\\tmpEtcPasswd.txt"));
            etcPasswdScanner.useDelimiter(":");

            while(etcPasswdScanner.hasNextLine()){
                if(mozneJmeno.equals(etcPasswdScanner.next())) databazeJmenoObsahuje=true;
                etcPasswdScanner.nextLine();
            }

        }while(databazeJmenoObsahuje);

        System.out.println("Ok, nastaveno.");

    }
}

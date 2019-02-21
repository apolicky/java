package cz.cuni.mff.java.semestr3.cv07;

import java.io.*;

public class kopieSouboruFileStreamem {
    public static void main(String[] args){
        try(FileInputStream IF = new FileInputStream("C:\\Users\\adamp\\IdeaProjects\\Java\\src\\cz\\cuni\\mff\\java\\cv07\\vstup.txt")){
            try(FileOutputStream OF = new FileOutputStream(("C:\\Users\\adamp\\IdeaProjects\\Java\\src\\cz\\cuni\\mff\\java\\cv07\\vystup.txt"))){
                byte[] b = new byte[1024];
                while(IF.read(b,0,1024)>0){
                    OF.write(b);
                }
            }
            catch(IOException ex){
                System.out.println("chyba pri zapisu");
            }
        }
        catch (IOException ex){
            System.out.println("chyba pri cteni");
        }
    }

}

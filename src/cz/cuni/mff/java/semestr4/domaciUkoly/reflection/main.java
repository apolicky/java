package cz.cuni.mff.java.semestr4.domaciUkoly.reflection;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class main {

    public static void main(String[] args){
        String jmenoTypu;
        TypovyBonzak Bonzak = new TypovyBonzak();
        try(BufferedReader BR = new BufferedReader(new InputStreamReader(System.in))){
            while((jmenoTypu = BR.readLine()) != null ){
                Bonzak.bonzuj(jmenoTypu);
            }
        }
        catch(IOException e){
            System.out.println("Error during reading from console.");
        }
        //Bonzak.bonzuj("a");

    }

}

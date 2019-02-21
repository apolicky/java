package cz.cuni.mff.java.semestr3.domaciukoly.GameOfLife;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GoL {

    public static void main(String[] args){
        BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
        try{
            String radka = BR.readLine();
            String[] cisla = radka.split(" ");

            HerniPole H = new HerniPole(Integer.parseInt(cisla[0]),Integer.parseInt(cisla[1]),BR);

            H.prectiSiPocatecniRozlozeni();

            H.projdiPole();

            H.vypisSe();
        }
        catch(IOException e){

        }
    }
}



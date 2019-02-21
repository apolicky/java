package cz.cuni.mff.java.semestr3.domaciukoly.Justify;

import java.io.IOException;

public class Ctecka {

    public static void main(String[] args){
        //nejdriv precti, kolik znaku ma byt na jednom radku
        //kdyz nejde o cislo, vypis Error
        Radka R = new Radka();

        try{
            R.pocetZnakuNaRadce = Integer.parseInt(R.S.readLine());
            if(R.pocetZnakuNaRadce >0){
                String radka="";
                while((radka = R.S.readLine()) != null){
                    R.rozdelNaSlova(radka);
                }

                R.vypisZbytek();

            }
            else{
                System.exit(69);
            }

        }
        catch(NumberFormatException|IOException e ){
            System.out.println("Error");
        }

        return;

    }
}

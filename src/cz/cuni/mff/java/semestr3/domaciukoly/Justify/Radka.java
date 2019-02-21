package cz.cuni.mff.java.semestr3.domaciukoly.Justify;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Radka {
    public int pocetZnakuNaRadce;
    private int pocetZnakuVsechSlov;
    private int pocetSlov;
    public BufferedReader S;

    private List<String> slova;

    Radka(){
        pocetZnakuNaRadce=0;
        pocetZnakuVsechSlov = 0;
        pocetSlov = 0;
        slova = new ArrayList<String>();
        S = new BufferedReader(new InputStreamReader(System.in));
    }

    public void pridejSlovo(String sl){
        //kdyz plati nasledujici, muzu slovo pridat a vejdu se do limitu
        if(pocetZnakuVsechSlov + pocetSlov + sl.length()<= pocetZnakuNaRadce){     //pocetSlov=pocet mezer mezi slovy
            slova.add(sl);
            pocetSlov++;
            pocetZnakuVsechSlov +=sl.length();
        }
        //jinak uz s temi slovy, co mam, musim neco udelat, dostat je ven.
        else if(sl.length()<=pocetZnakuNaRadce){
            if(pocetSlov>1){
                vypisJeSMezerami();
            }
            else if(pocetSlov==1){
                vypisZbytek();
            }

            pridejSlovo(sl);
        }
        else{ //vypis formatovane, co mas v zasobniku, na novy radek dej to nejdelsi slovo
            if(pocetSlov>1){
                vypisJeSMezerami();
            }
            else if(pocetSlov==1){
                vypisZbytek();
            }

            System.out.println(sl);

        }

    }

    public void rozdelNaSlova(String radka){
        String slovo="";

        boolean neprazdnyRadek=false;
        boolean zacloSlovo=false;

        for (int i=0;i<radka.length();i++) {
            if((!Character.isWhitespace(radka.charAt(i))) || radka.charAt(i)=='.'){
                /*
                kdyz to neni bily znak, ulozim si ho.
                carky a tecky nechavam ulozene ve slovech, protoze mi nemuze radek zacit , nebo .
                 */

                slovo+=radka.charAt(i);
                zacloSlovo=true;
                neprazdnyRadek=true;
            }
            else{
                if(zacloSlovo){
                    pridejSlovo(slovo);
                    zacloSlovo=false;
                    slovo="";
                }
            }
        }
        pridejSlovo(slovo);  //.nextLine() nevraci posledni znak-konec radky nejspis

        if(!neprazdnyRadek){
            vypisZbytek();
            vypisNovejRadek();
        }

    }

    public void vypisZbytek(){
        if(pocetSlov>0) {
            for (int i = 0; i < pocetSlov -1; i++) { //vypis vsechna slova, u prvnich 'zbytek' mezer, dej jednu navic
                System.out.print(slova.get(i) + " "); //vypis slovicko
            }

            System.out.println(slova.get(pocetSlov-1));   //posledni slovo

            slova.clear();
            pocetSlov = 0;
            pocetZnakuVsechSlov = 0;

        }
    }

    public void vypisNovejRadek(){
        System.out.println();
    }

    public void vypisJeSMezerami(){
        int neobsazeneZnaky = pocetZnakuNaRadce-(pocetZnakuVsechSlov + pocetSlov - 1);  //pocetSlov - 1 = pocet mezer minimalne
        int oKolikZnakuNavicMaMitKazdaMezera = (neobsazeneZnaky / (pocetSlov - 1)); //melo by vzdy byt aspon 1, jinak by se nepridalo to minule slovo z predchozi podminky
        neobsazeneZnaky -= (pocetSlov-1)* oKolikZnakuNavicMaMitKazdaMezera;

        for(int i=0; i<pocetSlov-1;i++){ //vypis vsechna slova, u prvnich 'zbytek' mezer, dej jednu navic
            System.out.print(slova.get(i)+ " "); //vypis slovicko
            for(int j = 0; j< oKolikZnakuNavicMaMitKazdaMezera; j++){  //pak dostatecne mezer
                System.out.print(" ");
            }
            if(neobsazeneZnaky>0){
                System.out.print(" ");
                neobsazeneZnaky--;
            }
        }

        System.out.println(slova.get(pocetSlov-1)); //odsad radku

        slova.clear(); //muzu, protoze jsem vypsal vsechny.
        pocetSlov=0;
        pocetZnakuVsechSlov=0;

    }
}

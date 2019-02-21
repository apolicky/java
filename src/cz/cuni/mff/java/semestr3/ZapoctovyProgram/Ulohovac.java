package cz.cuni.mff.java.semestr3.ZapoctovyProgram;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Ulohovac {

    public static void main(String[] args){

        /*
        String zkouska = new String("hovno hovno hovinko [[v-0-30]] malinko");
        String[] zkouska1 = zkouska.split("\\[\\[");

        String[] zkouska2=zkouska1[1].split("\\]\\]");

        for(String s : zkouska1){
            //System.out.println(s);
        }
        Random r = new Random();
        r.nextDouble();
        for(String s : zkouska2){
            //System.out.println(r.nextInt(30));
        }


        String vnitrekZavorek[] = zkouska.split("\\[\\[*\\]\\]");

        for (String s: vnitrekZavorek
             ) {
            System.out.println(s);
        }*/

        List<Promenna> promenne = new ArrayList<>();
        HashSet<Character> pouzitePromenne = new HashSet<>();
        //postupne do nej budu sypat promenne. Musi byt poporade, abych je sypal spravne do zadani pro studenty
        //nazvy promennych sypu do hashsetu, abych v tom mel poradem

        RozsekavacTextu R = new RozsekavacTextu();
        //R.rozsekej(InputStream,promenne,String[] kusyTextuUlohy);

        //ParsovacVyrazu P = new ParsovacVyrazu();
        //P.parsuj(vzorecvInfixu,pouzitePromenne);

    }

}

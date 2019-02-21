package cz.cuni.mff.java.semestr3.domaciukoly.STEST;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Parsovac {
    private Scanner S;
    private enum typOtazky{ s, m};
    private int cisloRadky=0;


    Parsovac(InputStream is){
        S = new Scanner(is);
    };

    public void parsuj(ArrayList<Otazka> otazky){

        while(S.hasNextLine()){
            String[] slova = S.nextLine().split(" ");
            cisloRadky++;
            if(slova.length==1){
                if(slova[0].equals("singlechoice")){
                    Singlechoice singlechoice = new Singlechoice();
                    parsujOtazku(otazky,singlechoice);
                }
                else if(slova[0].equals("multichoice")){
                    Multichoice multichoice = new Multichoice();
                    parsujOtazku(otazky,multichoice);
                }
            }
            //zacly odpovedi od studentu
            else{
                parsujOdpoved(slova,otazky);
            }
        }
    }

    private void parsujOtazku(ArrayList<Otazka> otazky, Otazka O ) {
        //tady bych mel mit naparsovane otazky vlozene do seznamu otazek, ktery je serazeny podle cisel otazek

        if (S.hasNextLine()) {
            //cislo otazky + zneni
            String[] slova = S.nextLine().split(" ");
            cisloRadky++;

            if (slova[0].matches("[0-9]+\\.")) {
                int cisloOtazky = Integer.parseInt(slova[0].substring(0, slova[0].length() - 1));
                //mam cislo otazky

                O.nastavCislo(cisloOtazky);

                slova=S.nextLine().split(" ");
                cisloRadky++;

                while(!(slova.length==1 && slova[0].equals(""))){

                    if(slova[0].matches("Answer:")) {

                        try {
                            for(int i =1 ;i<slova.length;i++) {
                                if(!O.spravneOdpovedi().contains(slova[i].charAt(0))) {
                                    //abych tam nemel odpoved B treba dvakrat mezi spravnymi.
                                    O.nastavSpravneOdpovedi(slova[i].charAt(0));
                                }
                            }
                            //tohle jeste muze byt spravne, nastavim spravne odpovedi.

                            int indexKamOtazkuVlozim=otazky.size();
                            boolean prepisuOtazku=false;

                            for(int i=0;i<otazky.size();i++){
                                //projdu si otazky zapsane v seznamu
                                if(otazky.get(i).cisloOtazky()>O.cisloOtazky()){
                                    indexKamOtazkuVlozim=i;
                                    break;
                                }
                                else if(otazky.get(i).cisloOtazky()==O.cisloOtazky()){
                                    indexKamOtazkuVlozim=i;
                                    prepisuOtazku=true;
                                    break;
                                }
                            }
                            //mam index, kam otazku vlozim
                            //vim, jestli otazku prepisuji nebo ne.

                            if(prepisuOtazku){
                                otazky.remove(indexKamOtazkuVlozim);
                                otazky.add(indexKamOtazkuVlozim,O);
                            }
                            else{
                                otazky.add(indexKamOtazkuVlozim,O);
                            }


                        } catch (IOException e) {
                            System.out.println(e);
                            break;
                        }

                    }


                    if(S.hasNextLine()){
                        slova=S.nextLine().split(" ");
                        cisloRadky++;
                    }
                    else{break;}
                }
            }


        }


    }


    private void parsujOdpoved(String[] slova,ArrayList<Otazka> otazky){
        //ve slovech mam jmeno studenta;
        StringBuilder jmenoStudenta=new StringBuilder();
        for(String slovo : slova){
            jmenoStudenta.append(slovo+" ");
        }
        jmenoStudenta.deleteCharAt(jmenoStudenta.length()-1);
        //System.out.println(jmenoStudenta);


        if(S.hasNextLine()){
            slova = S.nextLine().split(" "); //prvni odpoved na otazku
            cisloRadky++;

            HashMap<Integer,HashSet<Character>> odpovediStudenta = new HashMap<>();


            while(!(slova.length==1 && slova[0].equals(""))){

                if(slova[0].matches("[0-9]+\\.")) {
                    int cisloOdpovedi = Integer.parseInt(slova[0].substring(0, slova[0].length() - 1));


                    //Odpoved o = new Odpoved();
                    HashSet<Character> odpovediKOtazce=new HashSet<>();

                    for (int i = 1; i < slova.length; i++) {
                        if(!odpovediKOtazce.contains(slova[i].charAt(0))){
                            odpovediKOtazce.add(slova[i].charAt(0));
                        }
                    }

                    odpovediStudenta.put(cisloOdpovedi,odpovediKOtazce);
                }

                if(S.hasNextLine()){
                    slova=S.nextLine().split(" ");
                    cisloRadky++;
                }
                else{break;}
            }
            //tady uz mam ulozene vsechny odpovedi od studenta.


            vyhodnotOtazkuStudenta(jmenoStudenta.toString(),odpovediStudenta,otazky);

            //System.out.println(jmenoStudenta);


        }

    }

    public void vyhodnotOtazkuStudenta(String jmenoStudenta,HashMap<Integer,HashSet<Character>> odpovediStudenta,ArrayList<Otazka> otazky){
        int pocetChybDohromady=0;

        System.out.println(jmenoStudenta);

        for(Otazka o : otazky){
            int pocetChyb=0;
            //projdu vsechny otazky
            if(odpovediStudenta.containsKey(o.cisloOtazky())){
                //kdyz ma na danou otazku nejakou odpoved
                for(char c : o.spravneOdpovedi()){
                    if(odpovediStudenta.get(o.cisloOtazky()).contains(c)){
                        //spravnou odpoved ze seznamu odstranim, zadne chyby za to nejsou.
                        //odpovediStudenta.remove(o.cisloOtazky(),c);
                        odpovediStudenta.get(o.cisloOtazky()).remove(c);
                    }
                    else{
                        pocetChyb++;
                    }
                }
                pocetChyb+=odpovediStudenta.get(o.cisloOtazky()).size();
            }
            else{
                //kdyz na otazku odpoved nema, ma za ni tolik chyb, kolik je spravnych odpovedi.
                pocetChyb+=o.spravneOdpovedi().size();
            }
            System.out.println(o.cisloOtazky()+". "+pocetChyb);
            pocetChybDohromady+=pocetChyb;
        }
        //tady mam prosle vsechny otazky.

        int znamka=0;
        if(0<=pocetChybDohromady && pocetChybDohromady<=2){
            znamka=1;
        }
        else if(3<=pocetChybDohromady && pocetChybDohromady<=5){
            znamka=2;
        }
        else if(6<=pocetChybDohromady && pocetChybDohromady<=8){
            znamka=3;
        }
        else{
            znamka=4;
        }

        System.out.println("Result: " + znamka + " (" + pocetChybDohromady + ")");

    }
}

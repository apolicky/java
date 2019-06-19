package cz.cuni.mff.java.semestr4.domaciUkoly.jdom;

import java.util.ArrayList;

public class Sekce {
    public ArrayList<Odkaz> odkazy;
    public String mojeJmeno;
    public int poradi;

    Sekce(String s, int por){
        mojeJmeno = s;
        poradi=por;
        odkazy = new ArrayList<>();
    }

    public void pridejOdkaz(Odkaz odkaz){
        boolean byl = false;
        for(Odkaz o : odkazy){
            if(o.text.equals(odkaz.text) && o.kamId.equals(odkaz.kamId)){
                byl = true;
            }
        }
        if (!byl) {
            odkazy.add(odkaz);
        }
    }

    public void vypisOdkazy(){
        System.out.println(mojeJmeno + ":");
        for ( Odkaz o : odkazy){
            System.out.println("    " + o.vypisObsah());
        }
    }

}

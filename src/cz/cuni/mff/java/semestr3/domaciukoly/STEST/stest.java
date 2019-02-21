package cz.cuni.mff.java.semestr3.domaciukoly.STEST;

import java.util.*;

public class stest {

    public static void main(String[] args){
        Parsovac P = new Parsovac(System.in);

        ArrayList<Otazka> otazky=new ArrayList<>();

        P.parsuj(otazky);

        System.out.println();

    }

}

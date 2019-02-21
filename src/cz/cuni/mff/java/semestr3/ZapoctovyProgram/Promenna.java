package cz.cuni.mff.java.semestr3.ZapoctovyProgram;

import java.util.Random;

public class Promenna {
    private char jmeno;
    private boolean celociselna;
    private double horMez=Double.NaN,dolMez=Double.NaN;
    private int horMezInt, dolMezInt;

    public Promenna(char nazev, double hM, double dM){
        jmeno=nazev;
        //hodnota_= dM+(hM-dM)*new Random().nextDouble(); //nova random double mezi hranicemi
        horMez=hM;
        dolMez=dM;
        celociselna=false;
    }

    public Promenna(char nazev, int hM, int dM){
        jmeno=nazev;
        //hodnota_=new Random().nextInt((hM-dM)+1)+dM; //novy random int v rozmezi
        horMezInt=hM;
        dolMezInt=dM;
        celociselna=true;
    }

    public double hodnota(){
        if(celociselna){
            return new Random().nextInt((horMezInt-dolMezInt)+1)+dolMezInt;
        }
        else{
            return dolMez+(horMez-dolMez)*new Random().nextDouble();
        }
    }

}

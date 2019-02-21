package cz.cuni.mff.java.semestr3.cv11;

import java.time.*;

public class Cal {
    public static void main(String[] args){
        if(args.length == 0){
            vypisAktualniMesic();
        }
        else if(args.length == 2){
            vypisMesicPodleZadaneho(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        }
        else{
            System.out.println("chybka v zadani parametru");
        }
    }

    private static void vypisAktualniMesic(){

    }

    private static void vypisMesicPodleZadaneho(int mesic, int rok){

    }
}


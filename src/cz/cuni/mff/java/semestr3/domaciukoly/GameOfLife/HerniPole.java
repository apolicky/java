package cz.cuni.mff.java.semestr3.domaciukoly.GameOfLife;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HerniPole {
    private char[][] hP;
    private int pocetKroku,delkaRadku;
    private BufferedReader BR;

    public HerniPole(int x, int y, BufferedReader br){
        pocetKroku=y;
        delkaRadku=x;
        hP = new char[delkaRadku][delkaRadku];
        BR = br;
    }

    public void prectiSiPocatecniRozlozeni() throws IOException {
        for(int i = 0;i<delkaRadku;i++){
            hP[i] = BR.readLine().substring(0,delkaRadku).toCharArray();
        }
        //vypisSe();
    }

    public void projdiPole(){
        for(int krok = 0; krok < pocetKroku;krok++){
            char[][] tmpHP = new char[delkaRadku][delkaRadku];
            for(int radek = 0; radek<delkaRadku;radek++){
                for(int sloupec = 0; sloupec<delkaRadku;sloupec++){
                    int pocetZivychVOkoli = zivychVOkoli(radek+delkaRadku,sloupec+delkaRadku);
                    if(hP[radek][sloupec]=='_'){
                        if(pocetZivychVOkoli == 3){
                            tmpHP[radek][sloupec]='X';
                        }
                        else{
                            tmpHP[radek][sloupec]='_';
                        }
                    }
                    else if(hP[radek][sloupec]=='X'){
                        if(pocetZivychVOkoli < 2){
                            tmpHP[radek][sloupec]='_';
                        }
                        else if(pocetZivychVOkoli == 2 || pocetZivychVOkoli == 3){
                            tmpHP[radek][sloupec]='X';
                        }
                        else if(pocetZivychVOkoli > 3){
                            tmpHP[radek][sloupec]='_';
                        }
                        else tmpHP[radek][sloupec]='_';
                    }
                    else{
                        System.out.print("Chyba, jinej znak, nez v zadani.");
                        System.exit(69);
                    }
                }
            }
            hP=tmpHP;

            //vypisSe();
        }
    }

    public void vypisSe(){
        for(int i =0; i<delkaRadku;i++){
            System.out.println(hP[i]);
        }
    }

    private int zivychVOkoli(int radek,int sloupec){
        int zivych = 0;
        //radek -1
        if(hP[(radek-1)%delkaRadku][(sloupec-1)%delkaRadku]=='X'){zivych++;}
        if(hP[(radek-1)%delkaRadku][(sloupec)%delkaRadku]=='X'){zivych++;}
        if(hP[(radek-1)%delkaRadku][(sloupec+1)%delkaRadku]=='X'){zivych++;}
        //radek
        if(hP[(radek)%delkaRadku][(sloupec-1)%delkaRadku]=='X'){zivych++;}
        if(hP[(radek)%delkaRadku][(sloupec+1)%delkaRadku]=='X'){zivych++;}
        //radek+1
        if(hP[(radek+1)%delkaRadku][(sloupec-1)%delkaRadku]=='X'){zivych++;}
        if(hP[(radek+1)%delkaRadku][(sloupec)%delkaRadku]=='X'){zivych++;}
        if(hP[(radek+1)%delkaRadku][(sloupec+1)%delkaRadku]=='X'){zivych++;}

        //System.out.println("pozice:["+radek%delkaRadku+"]["+sloupec%delkaRadku+"] pocet ZVO: "+ zivych);
        return zivych;
    }
}

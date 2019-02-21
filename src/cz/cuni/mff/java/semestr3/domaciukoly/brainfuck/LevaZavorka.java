package cz.cuni.mff.java.semestr3.domaciukoly.brainfuck;

import java.util.ArrayList;

public class LevaZavorka extends Prikaz {
    private int[] mojePoloha;

    LevaZavorka(int pR, int pZ){
        mojePoloha=new int[]{pR,pZ};
    }

    public int radka(){
        return mojePoloha[0];
    }

    public int znak(){
        return mojePoloha[1];
    }

    @Override
    public void provedSe(Pole pole) {

    }

    public void provedSe(Pole pole, Iterejtr i, ArrayList<Prikaz> prikazy){
        if(pole.p[pole.ukazatel]!=0){
            //pokracuj dal
            i.plus();
        }
        else{
            int zavorky=0;
            while(i.get()<prikazy.size()){
                i.plus(); //bez o jednu pozici v prikazech dal
                Prikaz p = prikazy.get(i.get());
                if(p.getClass().getName().equals(LevaZavorka.class.getName())){
                    zavorky++;
                }
                else if(p.getClass().getName().equals(PravaZavorka.class.getName())){

                    if(zavorky==0){
                        //ted mam spravnou pravou zavorku, za ni pokracuji
                        i.plus();
                        //iterator mam na te prave zavorce. Nic nedelam a vystupuji z procedury do brfu.
                        break;
                    }
                    else if(zavorky>0){
                        //preskoc dal
                        //System.out.println("hledam dalsi levou zavorku."+p.getClass().getName());
                    }
                    else{
                        System.out.println("chyba pri hledani zavorky more.");
                        System.exit(3);
                    }
                    zavorky--;
                }
            }
        }


    }
}

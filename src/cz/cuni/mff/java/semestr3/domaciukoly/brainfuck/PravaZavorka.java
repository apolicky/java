
package cz.cuni.mff.java.semestr3.domaciukoly.brainfuck;

import java.util.ArrayList;

public class PravaZavorka extends Prikaz {
    public void provedSe(Pole pole,Iterejtr i, ArrayList<Prikaz> prikazy) {
        if(pole.p[pole.ukazatel]!=0){
            int zavorky=0;  //kolik pravych cestou potkam, tolik musim prejit levych, abych pak byl na spravne.
            while(i.get()>=0){

                i.minus();
                Prikaz p = prikazy.get(i.get());

                if(p.getClass().getName().equals(PravaZavorka.class.getName())){
                    zavorky++;
                }
                else if(p.getClass().getName().equals(LevaZavorka.class.getName())){

                    if(zavorky==0){
                        //mam tu spravnou levou zavorku, koncim, pokracuji od dalsiho.
                        i.plus();
                        break;
                    }
                    else if(zavorky>0){
                        //mam porad vic pravych jak levych, pokracuji v hledani
                        //System.out.println("hledam dalsi pravou zavorku."+pole.ukazatel);
                    }
                    else{
                        System.out.println("chyba pri hledani leve zavorky more." + zavorky);
                        System.exit(3);
                    }
                    zavorky--;
                }

            }
        }
        else{
            i.plus();
        }

    }

    @Override
    public void provedSe(Pole p){

    }
}

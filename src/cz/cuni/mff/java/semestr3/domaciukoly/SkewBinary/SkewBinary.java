package cz.cuni.mff.java.semestr3.domaciukoly.SkewBinary;

import java.io.*;

public class SkewBinary {

    public static void main(String[] args) {
        BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
        String radka;
        boolean nebylaDvojka=true;
        int vysledek=0;

        try{
            radka = BR.readLine();
            if(radka.length()>32){
                vypisChybu();
            }
            else{
                int k = radka.length();
                for(int i = 0; i<radka.length();i++){
                    switch (radka.charAt(i)){
                        case '1':
                            if(nebylaDvojka) {
                                vysledek += (Integer.parseInt("1")) * ((1<<k) - 1);
                                k--;
                            }
                            else{
                                vypisChybu();
                            }
                            break;
                        case '0':
                            vysledek += (Integer.parseInt("0")) * ((1<<k) - 1);
                            k--;
                            break;
                        case '2':
                            if(nebylaDvojka){
                                vysledek+= (Integer.parseInt("2"))*((1<<k) -1);
                                nebylaDvojka = false;
                                k--;
                            }
                            else {
                                vypisChybu();
                            }
                            break;

                            default:
                                vypisChybu();
                    }

                }
                System.out.print(vysledek);
                System.out.flush();
            }
        }
        catch(IOException e){
            vypisChybu();
        }

    }

    public static void vypisChybu(){
        System.out.print("Error");
        System.out.flush();
        //System.out.println("k:" + k + " vysl:"+vysledek + " kde:" + kde);
        System.exit(0);
    }

}

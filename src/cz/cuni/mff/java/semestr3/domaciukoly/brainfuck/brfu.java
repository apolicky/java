package cz.cuni.mff.java.semestr3.domaciukoly.brainfuck;

//import Prikaz;
//import Prikaz;

import java.io.*;
import java.util.*;
//import Pole;

public class brfu {

    public static ArrayList<Prikaz> prikazy;

    public static void main(String[] args){

        Pole pole;
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));

        try {
            if (args.length == 2) {
                pole = new Pole(Integer.parseInt(args[1]));
                vytvorProgram(args[0], pole, System.in);
                provedProgram(pole);
            } else if (args.length == 1) {
                pole = new Pole();
                vytvorProgram(args[0], pole, System.in);
                provedProgram(pole);
            } else if (args.length == 0) {
                //vytvorProgram(new BufferedReader(new InputStreamReader(System.in)).readLine(),);
                pole=new Pole();
                vytvorProgram(bufferedReader.readLine(),pole,System.in);
                provedProgram(pole);
            } else {
                System.out.println("Spatny pocet argumentu" + args.length);
                System.exit(0);
            }

        }
        catch (IOException e){
            System.out.println("chyba pri cteni nazvu souboru na vstupu.");
        }
    }

    public static void vytvorProgram(String jmenoSouboru,Pole pole, InputStream IS){
        try(BufferedReader FS = new BufferedReader(new FileReader(jmenoSouboru))){
            //byte[] radka=new byte[1024];
            int[] zavorka = new int[2];

            int pR=1,pZ=1;

            Stack<LevaZavorka> leveZavorky = new Stack<>();

            int praveZavorkyNavic=0;

            prikazy = new ArrayList<>();

            String radka = FS.readLine();

            while(radka!=null) {
                for (char c : radka.toCharArray()) {
                    if (c != '0') {
                            switch (c) {
                                case '<':
                                    prikazy.add(new PosunVlevo());
                                    break;

                                case '>':
                                    prikazy.add(new PosunVpravo());
                                    break;

                                case '.':
                                    prikazy.add(new Vypis());
                                    break;

                                case ',':
                                    prikazy.add(new Zapis());
                                    break;

                                case '+':
                                    prikazy.add(new ZvysHodnotu());
                                    break;

                                case '-':
                                    prikazy.add(new SnizHodnotu());
                                    break;

                                case '[':
                                    //zavorky++;

                                    prikazy.add(new LevaZavorka(pR,pZ));

                                    leveZavorky.push(new LevaZavorka(pR,pZ));
                                    break;

                                case ']':
                                    //zavorky--;
                                    if(leveZavorky.size()<=0){
                                        System.out.println("Unopened cycle - line " + pR + " character " + pZ);
                                        praveZavorkyNavic++;
                                    }
                                    else{
                                        prikazy.add(new PravaZavorka());
                                        leveZavorky.pop();
                                    }

                                    break;

                                default:
                                    break;
                            }
                        pZ++;
                    }
                }
                radka=FS.readLine();
                pR++;
                pZ=1;
            }

            if(leveZavorky.size()>0) {
                while (!leveZavorky.empty()) {
                    LevaZavorka l = leveZavorky.pop();
                    System.out.println("Unclosed cycle - line " + l.radka() + " character " + l.znak());
                }
                System.exit(0);
            }

            if(praveZavorkyNavic>0){
                System.exit(0);
            }

        }
        catch(IOException e){
            System.out.println("Chyba pri otevreni souboru. " + e);
            System.exit(0);
        }
    }

    public static void provedProgram(Pole pole) {

        try {

            Iterejtr i = new Iterejtr();
            i.set(0);


            while (i.get()<prikazy.size()) {
                Prikaz p = prikazy.get(i.get());
                if (p.getClass().getName().equals(PravaZavorka.class.getName())) {
                    PravaZavorka PZ = (PravaZavorka) p;
                    PZ.provedSe(pole, i, prikazy);

                }else if(p.getClass().getName().equals(LevaZavorka.class.getName())){
                    LevaZavorka LZ = (LevaZavorka) p;
                    LZ.provedSe(pole,i,prikazy);
                }
                else {
                    p.provedSe(pole);
                    i.plus();
                }

            }

            System.out.flush();

        }
        catch (IOException e){
            System.out.println(e);
        }
    }

}

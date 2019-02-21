package cz.cuni.mff.java.semestr3.cv06;

import java.io.*;

public class PostfixCalcHnetynka {
    public static void main(String[] args){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            String line;
            while((line= br.readLine())!=null){
                int[] stack = new int[1024];
                int current=-1;
                String[] elements=line.split(" ");
                for(String el:elements){
                    switch (el){
                        case"+":
                            stack[current-1]=stack[current-1]+stack[current];
                            current--;
                            break;
                        case"-":
                            stack[current-1]=stack[current-1]-stack[current];
                            break;
                        case"*":
                            stack[current-1]=stack[current]*stack[current-1];
                            break;
                        case"/":
                            stack[current-1]=stack[current-1]/stack[current-1];
                            break;
                            default:
                                stack[++current]= Integer.parseInt(el);

                    }
                }
                System.out.println(stack[current]);
            }
        }
        catch (IOException ex){
            System.out.println("chybka :/");
            System.exit(1);
        }
    }
}

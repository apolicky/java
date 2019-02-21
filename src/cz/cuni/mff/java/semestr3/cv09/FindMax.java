package cz.cuni.mff.java.semestr3.cv09;

import java.util.Arrays;

public class FindMax {
    public static void main(String[] args){
        int[] a1 = {0,1,2,5,7,9,6,4,3,5,8,9,5,11};
        int[] a2 = {};

        Arrays.stream(a1).parallel().max().ifPresentOrElse(System.out::println,()->System.out.println("max not found"));

        Arrays.stream(a2).parallel().max().ifPresentOrElse(System.out::println,()->System.out.println("max not found"));

    }
}

package cz.cuni.mff.java.semestr3.domaciukoly.nasobilka;

public class nasobilka {
    public static void main(String[] args){
        int cislo = Integer.parseInt(args[0]);
        if(cislo !=10) {
            for (int i = 1; i <= 10; i++) {
                if (i < 10) {
                    System.out.print(" " + i + " * " + cislo + " = ");
                    if (i * cislo < 10) {
                        System.out.println(" " + i * cislo);
                    } else {
                        System.out.println(i * cislo);
                    }
                } else {
                    System.out.println(i + " * " + cislo + " = " + i * cislo);
                }
            }
        }else{
            for (int i = 1; i <= 10; i++) {
                if (i < 10) {
                    System.out.print(" " + i + " * " + cislo + " = ");
                    if (i * cislo < 100) {
                        System.out.println(" " + i * cislo);
                    } else {
                        System.out.println(i * cislo);
                    }
                } else {
                    System.out.println(i + " * " + cislo + " = " + i * cislo);
                }
            }
        }

    }
}

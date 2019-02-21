package cz.cuni.mff.java.semestr3.cv06;

import java.io.*;
import java.util.Stack;

public class PostfixKalkulacka {
    private Stack<Integer> zasobnik;
    private int dalsiCislo;
    private int vysledek;
    private BufferedReader R;

    public PostfixKalkulacka(){
        zasobnik=new Stack<Integer>();
        R = new BufferedReader(new InputStreamReader(System.in));
        dalsiCislo=0;
        vysledek=0;
    }

    public void secti(){
        int tmp1=zasobnik.pop();
        int tmp2=zasobnik.pop();
        zasobnik.push(tmp1+tmp2);
    }

    public void odecti(){
        int tmp1=zasobnik.pop();
        int tmp2=zasobnik.pop();
        zasobnik.push(tmp2-tmp1);
    }

    public void vynasob(){
        int tmp1=zasobnik.pop();
        int tmp2=zasobnik.pop();
        zasobnik.push(tmp2*tmp1);
    }

    public void vydel(){
        int tmp1=zasobnik.pop();
        int tmp2=zasobnik.pop();
        if(tmp1!=0) zasobnik.push(tmp2/tmp1);
    }

    public static void main(String[] args){
        PostfixKalkulacka pK=new PostfixKalkulacka();


         try{
                pK.R.readLine();

            }
            catch (IOException ex){

            }
    }

}

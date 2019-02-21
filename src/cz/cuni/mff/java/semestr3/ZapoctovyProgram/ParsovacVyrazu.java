package cz.cuni.mff.java.semestr3.ZapoctovyProgram;

import cz.cuni.mff.java.semestr3.ZapoctovyProgram.Parsovani.Deleno;
import cz.cuni.mff.java.semestr3.ZapoctovyProgram.Parsovani.Krat;
import cz.cuni.mff.java.semestr3.ZapoctovyProgram.Parsovani.Minus;
import cz.cuni.mff.java.semestr3.ZapoctovyProgram.Parsovani.Plus;

import java.util.HashSet;
import java.util.Stack;

public class ParsovacVyrazu {

    //private BufferedReader BR;
    //private String vyraz_;
    private Stack<Vyraz> zasobnikVyrazu;
    private Stack<Character> zasobnikOperatoru;

    public Vyraz Parsuj(String vyraz, HashSet<Character> pouzitePromenne){
        //meli byste pracovat pouze s promennymi, tak snad tu nebudou cisla
        //promenne neznacit cisly!!!

        //jako vystup teto funkce chci mit referenci na koren vyrazoveho stromu


        for(int i = 0; i<vyraz.length();i++){
            char c = vyraz.charAt(i);
            if(c=='('){
                zasobnikOperatoru.push(c);
            }
            else if(c==')'){
                //vysyp az do spravne zavorky
                //nahlas chybu, kdyz je moc zavorek
            }
            else if(c=='+'){
                //vezmi si dva operandy ze zasobniku vyrazu, secti je a pushni je na zasobnik
                if(zasobnikVyrazu.size()>=2){
                    Vyraz v1,v2;
                    v1=zasobnikVyrazu.pop();
                    v2=zasobnikVyrazu.pop();
                    zasobnikVyrazu.push(new Plus(v1,v2));
                }
                else {
                    return null;
                }
            }
            else if(c=='-'){
                //vezmi si dva operandy ze zasobniku vyrazu, secti je a pushni je na zasobnik
                if(zasobnikVyrazu.size()>=2){
                    Vyraz v1,v2;
                    v2=zasobnikVyrazu.pop();
                    v1=zasobnikVyrazu.pop();
                    zasobnikVyrazu.push(new Minus(v1,v2));
                }
                else {
                    return null;
                }
            }
            else if(c=='*'){
                //vezmi si dva operandy ze zasobniku vyrazu, secti je a pushni je na zasobnik
                if(zasobnikVyrazu.size()>=2){
                    Vyraz v1,v2;
                    v1=zasobnikVyrazu.pop();
                    v2=zasobnikVyrazu.pop();
                    zasobnikVyrazu.push(new Krat(v1,v2));
                }
                else {
                    return null;
                }
            }
            else if(c=='/'){
                //vezmi si dva operandy ze zasobniku vyrazu, secti je a pushni je na zasobnik
                if(zasobnikVyrazu.size()>=2){
                    Vyraz v1,v2;
                    v2=zasobnikVyrazu.pop();
                    v1=zasobnikVyrazu.pop();
                    zasobnikVyrazu.push(new Deleno(v1,v2));
                }
                else {
                    return null;
                }
            }
            else if(jeToCharPromenne(c,pouzitePromenne)){

            }
        }

        //nevim, jestli je spravny
        return zasobnikVyrazu.firstElement();
    }

    public boolean jeToCharPromenne(char c, HashSet<Character> pouzitePromenne){
        if(pouzitePromenne.contains(c)){
            return true;
        }
        else return false;
    }

}

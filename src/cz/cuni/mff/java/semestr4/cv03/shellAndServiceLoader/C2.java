package cz.cuni.mff.java.semestr4.cv03.shellAndServiceLoader;

public class C2 implements Command{

    public String getCommand(){
        return "C2";
    }


    public String getHelp(){
        return "C2 help";
    }


    public String execute(String... args){
        return "executing C2.";
    }
}

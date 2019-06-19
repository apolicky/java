package cz.cuni.mff.java.semestr4.cv03.shellAndServiceLoader;

public class C1 implements Command {

    public String getCommand(){
        return "C1";
    }


    public String getHelp(){
        return "C1 help";
    }


    public String execute(String... args){
        return "executing C1.";
    }
}

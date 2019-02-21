package cz.cuni.mff.java.semestr4.plugins;

public class P1 implements Plugin {

    @Override
    public void perform(String msg) {
        System.out.println("P1: " + msg);
    }
}

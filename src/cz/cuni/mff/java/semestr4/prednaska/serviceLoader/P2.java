package cz.cuni.mff.java.semestr4.prednaska.serviceLoader;

public class P2 implements Plugin {

    @Override
    public void perform(String msg) {
        System.out.println("P2: " + msg);
    }
}

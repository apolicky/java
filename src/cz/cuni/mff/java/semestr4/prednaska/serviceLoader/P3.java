package cz.cuni.mff.java.semestr4.prednaska.serviceLoader;

public class P3 implements Plugin {

    @Override
    public void perform(String msg) {
        System.out.println("P3: " + msg);
    }
}

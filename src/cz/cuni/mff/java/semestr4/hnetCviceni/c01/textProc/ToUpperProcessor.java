package cz.cuni.mff.java.semestr4.hnetCviceni.c01.textProc;

public class ToUpperProcessor implements TextProcessor {
    @Override
    public String process(String text) {
        return text.toUpperCase();
    }
}

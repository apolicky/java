package cz.cuni.mff.java.semestr4.hnetCviceni.c01.textProc;

public class DotToExclamationProcessor implements TextProcessor {
    @Override
    public String process(String text) {
        return text.replaceAll("\\.", "!");
    }
}

package cz.cuni.mff.java.semestr4.hnetCviceni.c03loaders.textproc.procs;

import cz.cuni.mff.java.loaders.textprocessor.TextProcessor;

public class ToUpperProcessor implements TextProcessor {
    @Override
    public String process(String text) {
        return text.toUpperCase();
    }
}

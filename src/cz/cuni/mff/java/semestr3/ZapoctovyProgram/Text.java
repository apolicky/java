package cz.cuni.mff.java.semestr3.ZapoctovyProgram;

public class Text extends TextPart{

    String text;

    Text(String t){
        text = t;
    }

    @Override
    public String getTextValue() {
        return text;
    }
}

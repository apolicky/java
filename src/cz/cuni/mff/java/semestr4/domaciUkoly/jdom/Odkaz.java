package cz.cuni.mff.java.semestr4.domaciUkoly.jdom;

public class Odkaz {
    public String text,kamId;

    Odkaz(String t, String k){
        text=t;
        kamId=k;
    }

    public String vypisObsah(){
        return text + " (" + kamId + ")";
    }

    public void nahradIDzaJmenoSekce(String nove_jmeno){
        kamId = nove_jmeno;
    }
}

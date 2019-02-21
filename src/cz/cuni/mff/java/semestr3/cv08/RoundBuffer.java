package cz.cuni.mff.java.semestr3.cv08;

public class RoundBuffer {
    private String[] arr;

    /*
    fronta pevne velikosti, prechod od konce na zacatek
    dva ukazatele na zacatek a konec
    put pridava na posledni mod velikost
    get odebira prvky
    je naplneno?
    !konec nizsi cislo nez zacatek
    bezpecne pro vic vlaken - put, get synchronized
        zacit si hrat s wait pri vkladani - put
         notify - pri odebrani, abych dal vedet kamosum, ze se muzou pridat

     */

}

package cz.cuni.mff.java.semestr4.cv10;

public class main {
    public static void main(String[] args){
        MYServer myServer = new MYServer(8080);
        myServer.run();
    }
}

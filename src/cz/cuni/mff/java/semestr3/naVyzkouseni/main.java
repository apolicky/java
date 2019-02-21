package cz.cuni.mff.java.semestr3.naVyzkouseni;

import java.nio.ByteBuffer;

public class main {
    public static void main(String[] args){
        ByteBuffer bb = ByteBuffer.allocate(1024);
        bb.asIntBuffer().put(1234);
        bb.asIntBuffer().put(3,69);
        System.out.println(bb.getInt());
        System.out.println(bb.getInt());




    }
}

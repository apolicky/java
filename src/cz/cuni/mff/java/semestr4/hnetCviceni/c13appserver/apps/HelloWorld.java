package cz.cuni.mff.java.semestr4.hnetCviceni.c13appserver.apps;

import cz.cuni.mff.java.semestr4.hnetCviceni.c13appserver.Return;

public class HelloWorld {
    public static String sayHello() {
        return "Hello world";
    }

    @Return(mime="text/html")
    public static String sayHelloHtml() {
        return "<html><body><h1>Hello world</h1></body></html>";
    }

    public static String repeat(String s) {
        return s;
    }

}

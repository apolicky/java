package cz.cuni.mff.java.semestr4.prednaska.scripting.example;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Hello world example
 */
public class Hello {
    public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        //ScriptEngine engine = manager.getEngineByExtension("js");
        //ScriptEngine engine = manager.getEngineByMimeType("application/javascript");
        try {
            engine.eval("print( \"Hello World!\");");
            System.out.println(engine.eval(" 'Hello World again!' "));
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
    }
}
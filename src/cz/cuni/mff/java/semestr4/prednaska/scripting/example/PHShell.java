package cz.cuni.mff.java.semestr4.prednaska.scripting.example;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class PHShell {

    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager engManager = new ScriptEngineManager();
        ScriptEngine engine = engManager.getEngineByName("phscript");
        if (engine != null) {
            engine.eval("printHello");
            engine.eval("hello");
        } else {
            System.out.println("Engine not found");
        }
    }
}

package cz.cuni.mff.java.semestr4.prednaska.scripting.example;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;

public class Vars {

    public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByExtension("js");

        File f = new File("testfile");

        try {
            // put
            engine.put("file", f);
            engine.eval("print(file.getAbsolutePath())");
            // get
            engine.eval("var x = 10");
            Object o = engine.get("x");
            System.out.printf("type: %s; value: %s\n", o.getClass().getName(), o.toString());
        } catch (ScriptException e) {
            System.out.printf("exception: %s\n", e.getMessage());
        }
    }
}

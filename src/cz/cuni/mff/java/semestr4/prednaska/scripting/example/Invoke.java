package cz.cuni.mff.java.semestr4.prednaska.scripting.example;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Invoke {

    public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");

        try {
            if (engine instanceof Invocable) {
                Invocable inv = (Invocable) engine;

                engine.eval("function echo( x) { print( 'funkce echo: ' + x); };");
                inv.invokeFunction("echo", "text");

                engine.eval("var obj = new Object(); obj.sum = function( x, y) { return x + y;};");
                Object o = engine.get("obj");
                System.out.println(inv.invokeMethod(o, "sum", 5, 6));

                engine.eval("function run() { print( 'funkce run'); };");
                Runnable r = inv.getInterface(Runnable.class);
                (new Thread(r)).start();

                engine.eval("var runobj = { run: function() { print('metoda run'); } };");
                o = engine.get("runobj");
                r = inv.getInterface(o, Runnable.class);
                (new Thread(r)).start();
            }
        } catch (NoSuchMethodException | ScriptException e) {
            System.out.printf("exception: %s\n", e.getMessage());
        }
    }
}
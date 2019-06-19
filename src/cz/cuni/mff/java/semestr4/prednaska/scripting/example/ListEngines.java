package cz.cuni.mff.java.semestr4.prednaska.scripting.example;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

/**
 * Listing of all available scripting engines.
 */
public class ListEngines {

    public static void main(String[] args) {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        for (ScriptEngineFactory factory : engineManager.getEngineFactories()) {
            System.out.println("Engine name: " + factory.getEngineName());
            System.out.println("Engine version: " + factory.getEngineVersion());
            System.out.println("Language name: " + factory.getLanguageName());
            System.out.println("Language version: " + factory.getLanguageVersion());
            System.out.println("Engine names:");
            for (String name : factory.getNames()) {
                System.out.println("  " + name);
            }
            System.out.println("Engine MIME-types:");
            for (String mime : factory.getMimeTypes()) {
                System.out.println("  " + mime);
            }
            System.out.println("Engine extensions:");
            for (String ext : factory.getExtensions()) {
                System.out.println("  " + ext);
            }
        }
    }
}

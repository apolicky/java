package cz.cuni.mff.java.semestr4.prednaska.scripting.php;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import java.util.Arrays;
import java.util.List;

public class PHScriptEngineFactory implements ScriptEngineFactory {

    @Override
    public String getEngineName() {
        return "PHScript engine";
    }

    @Override
    public String getEngineVersion() {
        return "0.0.1";
    }

    @Override
    public List<String> getExtensions() {
        return Arrays.asList("phscript");
    }

    @Override
    public List<String> getMimeTypes() {
        return Arrays.asList("text/x-phscript");
    }

    @Override
    public List<String> getNames() {
        return Arrays.asList("PHScript", "phscript");
    }

    @Override
    public String getLanguageName() {
        return "PHScript";
    }

    @Override
    public String getLanguageVersion() {
        return "0.0.1";
    }

    @Override
    public Object getParameter(String key) {
        return null;
    }

    @Override
    public String getMethodCallSyntax(String obj, String m, String... args) {
        return "";
    }

    @Override
    public String getOutputStatement(String toDisplay) {
        return "";
    }

    @Override
    public String getProgram(String... statements) {
        return "";
    }

    @Override
    public ScriptEngine getScriptEngine() {
        return new PHScriptEngine(this);
    }

}

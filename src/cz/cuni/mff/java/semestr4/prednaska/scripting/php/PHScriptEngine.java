package cz.cuni.mff.java.semestr4.prednaska.scripting.php;

import javax.script.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

class PHScriptEngine extends AbstractScriptEngine {

    ScriptEngineFactory f;

    public PHScriptEngine(ScriptEngineFactory f) {
        this.f = f;
    }

    @Override
    public Object eval(String script, ScriptContext context) throws ScriptException {
        return eval(new StringReader(script), context);
    }

    @Override
    public Object eval(Reader reader, ScriptContext context) throws ScriptException {
        try {
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.equals("printHello")) {
                    System.out.println("Hello from PHScript ;-)");
                } else {
                    System.out.println("Unknown command");
                }
            }
            return "";
        } catch (IOException ex) {
            throw new ScriptException(ex);
        }
    }

    @Override
    public Bindings createBindings() {
        return new SimpleBindings();
    }

    @Override
    public ScriptEngineFactory getFactory() {
        return f;
    }


}

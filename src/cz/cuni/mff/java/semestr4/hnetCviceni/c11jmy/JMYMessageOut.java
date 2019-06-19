package cz.cuni.mff.java.semestr4.hnetCviceni.c11jmy;

import java.io.Serializable;

public class JMYMessageOut implements Serializable {
    private JMYMessageOutType type;
    private Object result;

    public JMYMessageOut(JMYMessageOutType type, Object result) {
        this.type = type;
        this.result = result;
    }

    public JMYMessageOut(JMYMessageOutType type) {
        this.type = type;
    }

    public JMYMessageOut(Throwable ex) {
        this.type = JMYMessageOutType.EXCEPTION;
        this.result = ex;
    }
    
    public JMYMessageOutType getType() {
        return type;
    }

    public Object getResult() {
        return result;
    }
    
    
}

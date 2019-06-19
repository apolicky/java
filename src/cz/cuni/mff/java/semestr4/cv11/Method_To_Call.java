package cz.cuni.mff.java.semestr4.cv11;

import java.lang.reflect.Parameter;
import java.util.ArrayList;

public class Method_To_Call {

    private static String class_name, method_name;
    private static ArrayList<String> params;

    Method_To_Call(String c_n, String m_n){
        class_name = c_n;
        method_name = m_n;
        params = new ArrayList<>();
    }

    public void addParam(String p){
        params.add(p);
    }

    public String getClassName(){
        return class_name;
    }

    public String getMethodName(){
        return method_name;
    }

    public ArrayList<String> getParameters(){
        return params;
    }

}

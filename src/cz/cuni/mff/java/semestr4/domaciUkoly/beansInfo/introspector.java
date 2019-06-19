package cz.cuni.mff.java.semestr4.domaciUkoly.beansInfo;

import java.beans.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class introspector {

    public static void main(String[] args){

        String radka;

        try(BufferedReader BR = new BufferedReader(new InputStreamReader(System.in))){
            while((radka = BR.readLine()) != null){
                prozkoumej_objekt(radka);
                System.out.println();
            }


        }
        catch (IOException e){

        }


    }

    public static void prozkoumej_objekt(String radka){
        try{
            BeanInfo BI = Introspector.getBeanInfo(Class.forName(radka));

            System.out.println("JavaBean Name: " + radka);

            for(PropertyDescriptor pd : BI.getPropertyDescriptors()){
                System.out.println(je_ro(pd) + je_bound(pd) + je_cstr(pd) + "property " + pd.getPropertyType().getCanonicalName() + " " + pd.getName());
            }

            ArrayList<EventSetDescriptor> ESDs = new ArrayList<>();
            for(EventSetDescriptor esd : BI.getEventSetDescriptors()){
                ESDs.add(esd);
            }

            ESDs.sort(new KomparatorESDu());

            for(EventSetDescriptor esd : ESDs){
                System.out.println("listener " + esd.getListenerType().getCanonicalName());

                ArrayList<String> jmena_metod = new ArrayList<>();

                for(Method m : esd.getListenerType().getDeclaredMethods()){
                    jmena_metod.add(m.getName());
                }

                Collections.sort(jmena_metod);

                for(String jmeno_metody : jmena_metod){
                    System.out.println("  " + jmeno_metody);
                }
            }

            ArrayList<String> jmena_metod_tridy = new ArrayList<>();
            for(MethodDescriptor m : BI.getMethodDescriptors()){
                jmena_metod_tridy.add(m.getName());
            }

            Collections.sort(jmena_metod_tridy);
            for(String jmeno_metody_tridy : jmena_metod_tridy){
                System.out.println("method " + jmeno_metody_tridy);
            }

        }
        catch (IntrospectionException | ClassNotFoundException e){
            System.out.println("\"" + radka + "\" does not exist" );
        }
    }

    public static String je_ro(PropertyDescriptor pd){
        if(pd.getWriteMethod() == null){
            return "readonly ";
        }
        else{
            return "";
        }
    }

    public static String je_bound(PropertyDescriptor pd){
        if (pd.isBound()){
            return "bound ";
        }
        else{
            return "";
        }
    }

    public static String je_cstr(PropertyDescriptor pd){
        if (pd.isConstrained()){
            return "constrained ";
        }
        else{
            return "";
        }
    }


    public static class KomparatorESDu implements Comparator<EventSetDescriptor> {
        @Override
        public int compare(EventSetDescriptor e1, EventSetDescriptor e2) {
            return e1.getName().compareTo(e2.getName());
        }
    }

}

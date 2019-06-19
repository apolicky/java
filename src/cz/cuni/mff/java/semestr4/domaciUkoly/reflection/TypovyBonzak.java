package cz.cuni.mff.java.semestr4.domaciUkoly.reflection;


import java.lang.reflect.*;

public class TypovyBonzak {

    public void bonzuj(String naKoho){
        try{
            Class<?> terc = Class.forName(naKoho);
            System.out.println(terc.getCanonicalName());
            System.out.println(ifaceXclass(terc));
            System.out.println("Generic: " + genericka(terc));
            System.out.println(predek(terc));
            impl_interfejsy(terc);
            System.out.println(pocet_Ver(terc));
            System.out.println(pocet_Ver_Static(terc));
            System.out.println(poc_vni_trid(terc));
            vypis_vni_tridy(terc);
            System.out.println();
        }
        catch(ClassNotFoundException e){
            System.out.println(naKoho + " does not exist");
        }
    }

    public String ifaceXclass(Class<?> terc){
        if(terc.isEnum()){
            return "enum";
        }
        else if (terc.isPrimitive()){
            return "primitive";
        }
        else if (terc.isInterface()){
            return "interface";
        }
        else if(terc.isArray()){
            return "array";
        }
        else if(terc.isAnnotation()){
            return "annotation";
        }
        else if(terc instanceof Class<?>){
            return "class";
        }
        else{
            return "sthing else";
        }
    }

    public String genericka(Class<?> terc){
        TypeVariable[] typeVariables;
        if ((typeVariables=terc.getTypeParameters()).length != 0){
            StringBuilder s = new StringBuilder();
            for ( TypeVariable t : typeVariables){
                s.append(t + " ");
            }
            return "yes, Variables: " + s;
        }

        return "no";
    }

    public String predek(Class<?> terc){
        if (terc.getSuperclass() != null){
            return terc.getSuperclass().getCanonicalName();
        }
        return "null";
    }

    public void impl_interfejsy(Class<?> terc){
        Class[] ifsy = terc.getInterfaces();
        System.out.println(ifsy.length);
        for ( Class i : ifsy){
            System.out.println(i.getCanonicalName());
        }
    }

    public int pocet_Ver(Class<?> terc){
        int count=0;
        Method[] allMethods = terc.getMethods();
        for (Method method : allMethods) {
            if (Modifier.isPublic(method.getModifiers())) {
                count++;
            }
        }
        return count;
    }

    public int pocet_Ver_Static(Class<?> terc){
        int count=0;
        Method[] allMethods = terc.getMethods();
        for (Method method : allMethods) {
            if (Modifier.isPublic(method.getModifiers())) {
                if(Modifier.isStatic(method.getModifiers())){
                    count++;
                }
            }
        }
        return count;
    }

    public int poc_vni_trid(Class<?> terc){
        return terc.getClasses().length;
    }

    public void vypis_vni_tridy(Class<?> terc){
        Class[] tridy = terc.getClasses();
        for (Class t : tridy){
            System.out.println(t.getName());
        }
    }

}

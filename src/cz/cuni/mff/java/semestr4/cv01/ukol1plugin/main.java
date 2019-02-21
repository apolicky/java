package cz.cuni.mff.java.semestr4.cv01.ukol1plugin;

import cz.cuni.mff.java.semestr4.plugins.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class main {
    public static void main(String[] args){
        List<Plugin> l = loadPlugins(Plugin.class, "cz.cuni.mff.java.semestr4.plugins.P1",
                "cz.cuni.mff.java.semestr4.plugins.P2");
        l.forEach(p1 -> {p1.perform("hi");});
    }

    static List loadPlugins(Class pluginInterface, String... pluginNames){
        List<cz.cuni.mff.java.semestr4.plugins.Plugin> plugins = new ArrayList<>();

        for(String name : pluginNames){
            try {
                Class<?> cls = Class.forName(name);
                if (cls.isArray() || cls.isInterface() || cls.isPrimitive() || cls.isEnum() || cls.isAnnotation()) {
                    System.out.println(cls.getName() + " is not a class.");
                    continue;
                }
                if (!pluginInterface.isAssignableFrom(cls)) {
                    System.out.println("The class " + cls.getName() + " does not implement the interface Plugin.");
                    continue;
                }
                plugins.add((Plugin) cls.getDeclaredConstructor().newInstance());
            } catch (ClassNotFoundException e) {
                System.out.println("The class " + name + " does not exists.");
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.out.println("It is not possible to instantiate " + name);
            }
        }
        return plugins;
    }
}

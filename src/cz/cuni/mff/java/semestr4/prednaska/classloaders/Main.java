package cz.cuni.mff.java.semestr4.prednaska.classloaders;

public class Main {

    public static void main(String[] argv) throws Exception {
        ClassLoader cl = new URLClassLoader(argv[0]);
        MyInterface obj = (MyInterface) Class.forName("RemoteClassLoaderTest.MyClass", true, cl).getConstructor().newInstance();

        obj.print();
    }

}

package cz.cuni.mff.java.semestr4.prednaska.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloClient {
    public static void main(String[] args) {
        try {
            Hello robj = (Hello) Naming.lookup("Hello");
            String mesg = robj.sayHello();
            System.out.println(mesg);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(HelloClient.class.getName()).log(Level.SEVERE, "Exception occured", ex);
        }
    }
}

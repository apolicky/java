package cz.cuni.mff.java.semestr4.prednaska.rmi;

import java.rmi.Remote;

public interface Hello extends Remote {
    public String sayHello() throws java.rmi.RemoteException;
}

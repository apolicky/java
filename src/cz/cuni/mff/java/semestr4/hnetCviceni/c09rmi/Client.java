package cz.cuni.mff.java.semestr4.hnetCviceni.c09rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    void notifyMessage() throws RemoteException;
}

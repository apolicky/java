package cz.cuni.mff.java.semestr4.hnetCviceni.c11jmy.example;

import cz.cuni.mff.java.jmy.Mgmt;

@Mgmt
public interface MBean {
    String getName();
    int getID();
    void setID(int value);
}

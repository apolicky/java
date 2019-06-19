package cz.cuni.mff.java.semestr4.cv10;


import java.io.Serializable;

public class MYBeanImpl implements MYBean {

    @Override
    public int getYear() {
        return 2019;
    }

    @Override
    public String getName() {
        return "MYBean class.";
    }
}

package cz.cuni.mff.java.semestr4.prednaska.mbeans.mx;

public class SimpleBeanImpl implements SimpleBean {
    private int state = 0;

    @Override
    public int getState() {
        return (state);
    }

    @Override
    public void setState(int s) {
        state = s;
    }

}

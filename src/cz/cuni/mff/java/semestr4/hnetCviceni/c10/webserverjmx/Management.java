package cz.cuni.mff.java.semestr4.hnetCviceni.c10.webserverjmx;

import javax.management.MXBean;

@MXBean
public interface Management {
    void shutdown();
    String getDirectory();
    void setDirectory(String dir);
    String getState();
    void pause();
    void resume();
}

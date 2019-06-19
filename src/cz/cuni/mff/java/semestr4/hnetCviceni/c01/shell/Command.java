package cz.cuni.mff.java.semestr4.hnetCviceni.c01.shell;

public interface Command {
    String getCommand();

    String getHelp();

    String execute(String... args);
}

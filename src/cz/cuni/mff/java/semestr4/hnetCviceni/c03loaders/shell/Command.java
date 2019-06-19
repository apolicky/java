package cz.cuni.mff.java.semestr4.hnetCviceni.c03loaders.shell;

public interface Command {
    String getCommand();

    String getHelp();

    String execute(String... args);
}

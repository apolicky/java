package cz.cuni.mff.java.semestr4.cv03.shellAndServiceLoader;

public interface Command {
    String getCommand();
    String getHelp();
    String execute(String... args);
}

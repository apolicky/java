package cz.cuni.mff.java.semestr4.cv01.ukol3rozsiritelnyShell;

public interface Command {
    String getCommand();
    String getHelp();
    String execute(String... args);
}

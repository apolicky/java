package cz.cuni.mff.java.semestr4.hnetCviceni.c03loaders.shell.commands;

import cz.cuni.mff.java.semestr4.hnetCviceni.c03loaders.shell.Command;

public class HelloCommand implements Command {
    @Override
    public String getCommand() {
        return "hello";
    }

    @Override
    public String getHelp() {
        return "prints out Hello World";
    }

    @Override
    public String execute(String... args) {
        return "Hello World\n";
    }
}

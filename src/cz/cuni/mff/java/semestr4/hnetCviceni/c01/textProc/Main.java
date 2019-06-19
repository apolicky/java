package cz.cuni.mff.java.semestr4.hnetCviceni.c01.textProc;

import cz.cuni.mff.java.plugins.Loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<TextProcessor> processors = null;
        if (args.length > 0) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(args[0]));
                processors = Loader.loadPlugins(TextProcessor.class, lines.toArray(new String[lines.size()]));
            } catch (IOException e) {
                System.out.println("Cannot read file with plugin names. Exiting.");
                System.exit(1);
            }
        } else {
            System.out.println("WARNING: no plugins");
            processors = new ArrayList<>();
        }

        try {
            int c;
            StringBuilder sb = new StringBuilder();
            Reader in = new BufferedReader(new InputStreamReader(System.in));
            while ((c = in.read()) != -1) {
                sb.append((char) c);
            }
            String input = sb.toString();
            for (TextProcessor tp : processors) {
                input = tp.process(input);
            }
            System.out.println(input);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}

package cz.cuni.mff.java.semestr3.cv10;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;


public class Item implements Serializable {
    int priority;
    String text;

    public Item(int pri, String t) {
        this.priority = pri;
        this.text = t;
    }

    public Item(String line) {
        String[] parts = line.split(":");
        try {
            priority = Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("bad line", e);
        }
        text = Arrays.stream(parts, 1, parts.length).collect(Collectors.joining(":"));
    }

    @Override
    public String toString() {
        return priority + ":" + text;
    }
}


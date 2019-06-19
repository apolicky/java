package cz.cuni.mff.java.semestr4.cv02;


/*
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
*/

import java.util.ArrayList;
import java.util.Collection;
import java.lang.annotation.*;

@TesterInfo(priority = TesterInfo.Priority.HIGH, createdBy = "AP",lastModified = "01/01/2018")
public class SimpleTest implements TestIinf{
    private Collection<String> collection;



    @TestIinf
    void setUp() {
        collection = new ArrayList<>();
    }

    @TestIinf
    void tearDown() {
        collection.clear();
    }

    @TestIinf
    void testEmptyCollection() {
        if (!collection.isEmpty())
            throw new AssertionError();
    }

    @TestIinf
    void testOneItemCollection() {
        collection.add("itemA");
        if (1 != collection.size())
            throw new AssertionError();
    }

    @TestIinf(enabled = false)
    void disabledTest(){
        System.out.println("Should never print this.");
    }

    @Override
    public boolean enabled() {
        return true;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
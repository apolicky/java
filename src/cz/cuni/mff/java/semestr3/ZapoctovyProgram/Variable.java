package cz.cuni.mff.java.semestr3.ZapoctovyProgram;

import java.util.Random;
import java.util.regex.Matcher;

public class Variable extends TextPart {

    private String name;
    private NumberType numberType;
    private float upperBound, lowerBound, value;
    private Random random;

    Variable(String n,float lB, float uB, NumberType nT){
        name = n;
        lowerBound = lB;
        upperBound = uB;
        numberType = nT;
        random = new Random();

        update();
    }

    public void update(){
        switch (numberType){
            case INTEGER:
                value = random.nextInt(Math.round(upperBound-lowerBound)) + Math.round(lowerBound);
                break;
            case FLOAT:
                value = random.nextFloat()*(upperBound-lowerBound) + lowerBound;
                break;

            default:
                throw new Error("wrong number type " + numberType);
        }
    }

    public String getName(){
        return name;
    }

    @Override
    public String getTextValue() {
        switch (numberType){
            case FLOAT:
                return Float.toString(value);
            case INTEGER:
                return Integer.toString(Math.round(value));

                default:
                    return null;
        }
    }
}

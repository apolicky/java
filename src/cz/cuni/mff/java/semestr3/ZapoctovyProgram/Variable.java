package cz.cuni.mff.java.semestr3.ZapoctovyProgram;

import java.util.Random;

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

        generateNextValue();
    }

    // generates new value of variable
    public void generateNextValue(){
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

    // returns name of variable
    public String getName(){
        return name;
    }

    // returns value as a string
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

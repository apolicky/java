package cz.cuni.mff.java.semestr3.ZapoctovyProgram;

import javax.management.ValueExp;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static String resultPattern;
    private static List<TextPart> parsedText;
    private static List<Variable> variables;

    public static void main(String[] args) {

        parsedText = new ArrayList<TextPart>();
        variables = new ArrayList<Variable>();
        resultPattern = null;

        readFile("testFile");

        for(int i = 0; i < 5; i++) {
            String resultForThisStudent = substituteValuesOfVariables(resultPattern);

            String taskForThisStudent = createTask();

            for (Variable v : variables) {
                v.update();
            }


            System.out.println(taskForThisStudent);
            System.out.println(resultForThisStudent);
            System.out.println(Float.toString(evaluate(resultForThisStudent)));

        }
        //printText(parsedText);

    }

    public static void readFile(String filename) {

        try (BufferedReader BR = new BufferedReader(new FileReader(filename))) {

            String line;
            while ((line = BR.readLine()) != null) {
                line += "\n";

                if (line.matches("\\[\\[[^\\]]*\\]\\]\\s*")) {
                    resultPattern = line;
                    break;
                }

                Pattern variablePattern = Pattern.compile("\\{\\{\\S+\\}\\}", 0);
                Matcher variableMatcher = variablePattern.matcher(line);


                String[] starts = line.split(variablePattern.pattern());

                int i = 0;
                while (variableMatcher.find()) {
                    // save text
                    parsedText.add(new Text(starts[i]));

                    // variable
                    String n;
                    float uB, lB;
                    NumberType nT;

                    // parse important information
                    String innerPart = variableMatcher.group(0).replaceAll("\\{\\{", "").replaceAll("\\}\\}", "");

                    n = innerPart.split(":")[0];
                    lB = Float.parseFloat(innerPart.split(":")[1]);
                    uB = Float.parseFloat(innerPart.split(":")[2]);

                    switch (innerPart.split(":")[3]) {
                        case "I":
                            nT = NumberType.INTEGER;
                            break;
                        case "F":
                            nT = NumberType.FLOAT;
                            break;

                        default:
                            throw (new Error("wrong number type " + innerPart.split(":")[2]));
                    }

                    // save variables and texts
                    Variable v = new Variable(n, lB, uB, nT);

                    parsedText.add(v);
                    variables.add(v);
                    i++;
                }
                if (i < starts.length) {
                    for (; i < starts.length; i++) {
                        parsedText.add(new Text(starts[i]));
                    }
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (resultPattern != null) {
            resultPattern = resultPattern.replace("[[","").replace("]]","");
            System.out.println("res pattern: " + resultPattern);
        }

    }

    public static float evaluate(String infixExpression) {
        char[] tokens = infixExpression.toCharArray();

        Stack<Float> values = new Stack<Float>();
        Stack<Operator> operators = new Stack<Operator>();

        for (int i = 0; i < tokens.length; i++) {
            // Current token is a whitespace, skip it
            if (tokens[i] == ' ')
                continue;

            if (Character.isAlphabetic(tokens[i])){
                StringBuilder SB = new StringBuilder();

                while (i < tokens.length && Character.isAlphabetic(tokens[i])) {
                    SB.append(tokens[i]);
                    i++;
                }

                switch (SB.toString()){
                    case "pi":
                        values.push((float) Math.PI);
                        break;
                    case "log":
                        operators.push(Operator.LOG);
                        break;
                    case "sin":
                        operators.push(Operator.SIN);
                        break;
                    case "cos":
                        operators.push(Operator.COS);
                        break;
                    case "tg":
                        operators.push(Operator.TG);
                        break;
                    case "arcsin":
                        operators.push(Operator.ARCSIN);
                        break;
                    case "arccos":
                        operators.push(Operator.ARCCOS);
                        break;
                    case "arctg":
                        operators.push(Operator.ARCTG);
                        break;

                        default:
                            throw new Error("Unsupported function " + SB.toString());
                }
            }
            else if (tokens[i] >= '0' && tokens[i] <= '9') {
                StringBuilder SB = new StringBuilder();

                int numberOfPoints = 0;
                while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.' ) && numberOfPoints <= 1) {
                    SB.append(tokens[i]);
                    if(tokens[i] == '.'){
                        numberOfPoints++;
                    }
                    i++;
                }
                values.push(Float.parseFloat(SB.toString()));
            }

            // Current token is an opening brace, push it to 'ops'
            else if (tokens[i] == '(')
                operators.push(Operator.LPARENTH);

                // Closing brace encountered, solve entire brace
            else if (tokens[i] == ')') {
                while (operators.peek() != Operator.LPARENTH)
                    values.push(applyBinaryOperator(operators.pop(), values.pop(), values.pop()));
                operators.pop();
            }

            // Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' ||
                    tokens[i] == '*' || tokens[i] == '/' || tokens[i] == '^') {

                Operator op;

                switch (tokens[i]){
                    case '+':
                        op = Operator.PLUS;
                        break;
                    case '-':
                        op = Operator.MINUS;
                        break;
                    case '*':
                        op = Operator.TIMES;
                        break;
                    case '/':
                        op = Operator.DIV;
                        break;
                    case '^':
                        op = Operator.POW;
                        break;

                        default:
                            throw new Error("Unsupported operator " + tokens[i]);

                }

                // While top of 'ops' has same or greater precedence to current
                // token, which is an operator, apply operator on top of 'ops'
                // to top two elements in values stack

                while (!operators.empty() && hasGreaterPriorityThanTopOfStack(op, operators.peek()))
                    values.push(applyBinaryOperator(operators.pop(), values.pop(), values.pop()));

                // Push current token to 'ops'.
                operators.push(op);
            }
        }

        // Entire expression has been parsed at this point, apply remaining
        // ops to remaining values
        while (!operators.empty())
            if (isBinary(operators.peek())){
                values.push(applyBinaryOperator(operators.pop(), values.pop(), values.pop()));
            }
            else {
                values.push(applyUnaryOperator(operators.pop(), values.pop()));
            }


        // Top of 'values' contains result, return it
        return values.pop();
    }

    // Returns true if 'topOfStack' has higher or same priority as 'newOp',
    // otherwise returns false.
    public static boolean hasGreaterPriorityThanTopOfStack(Operator newOp, Operator topOfStack) {
        if (topOfStack == Operator.LPARENTH || topOfStack == Operator.RPARENTH) {
            return false;
        }
        if(newOp == Operator.POW && topOfStack != Operator.POW){
            return false;
        }
        else if ((newOp == Operator.TIMES || newOp == Operator.DIV)
                && (topOfStack == Operator.PLUS || topOfStack == Operator.MINUS)) {
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean isBinary(Operator op){
        if (op == Operator.PLUS ||
            op == Operator.MINUS ||
            op == Operator.TIMES ||
            op == Operator.DIV ||
            op == Operator.POW ){
            return true;
        }
        else {
            return false;
        }
    }


    public static float applyUnaryOperator(Operator op, float a){
        switch (op){
            case SIN:
                return (float) Math.sin(a);
            case COS:
                return (float) Math.cos(a);
            case TG:
                return (float) Math.tan(a);
            case ARCSIN:
                return (float) Math.asin(a);
            case ARCCOS:
                return (float) Math.acos(a);
            case ARCTG:
                return (float) Math.atan(a);
            case LOG:
                return (float) Math.log(a);

                default:
                    throw new Error("Unsupported unary operator " + op);
        }
    }
    // A utility method to apply an operator 'op' on operands 'a'
    // and 'b'. Return the result.
    public static float applyBinaryOperator(Operator op, float b, float a) {
        switch (op) {
            case PLUS:
                return a + b;
            case MINUS:
                return a - b;
            case TIMES:
                return a * b;
            case DIV:
                if (b == 0)
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                return a / b;
            case POW:
                return (float) Math.pow(a,b);
        }
        return 0;
    }

    // substitute new values of variables to result pattern
    public static String substituteValuesOfVariables(String input){

        String output = new String(input);
        for(Variable v : variables){
            output = output.replaceAll("\\s*" + v.getName() + "\\W"," " + v.getTextValue() + " ");
        }

        return output;
    }

    // create task by substiting values to text
    public static String createTask(){

        StringBuilder SB = new StringBuilder();
        for(TextPart tP : parsedText){
            SB.append(tP.getTextValue());
        }
        return SB.toString();
    }
}



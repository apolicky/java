package cz.cuni.mff.java.semestr3.ZapoctovyProgram;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static String resultPattern;
    private static List<TextPart> parsedText;
    private static List<Variable> variables;

    private static String inputFilename, resultFilenamePrefix, resultFilename;
    private static int numberOfStudents;

    public static void main(String[] args) {

        initializeVariables();
        readArguments(args);

        readTask(inputFilename);

        try(PrintWriter PWresult = new PrintWriter(resultFilename)) {


            try(BufferedReader BR = new BufferedReader(new InputStreamReader(System.in))) {
                if (numberOfStudents == -1) {
                    System.out.println("Number of students not specified. Specify it, please:");
                    numberOfStudents = Integer.parseInt(BR.readLine());
                }

                if(resultFilenamePrefix == null){
                    System.out.println("Prefix of student tasks not specified. Specify it, please:");
                    resultFilenamePrefix = BR.readLine();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }


            for(int i = 0; i < numberOfStudents; i++) {
                String resultForThisStudent = substituteValuesOfVariables(resultPattern);

                String taskForThisStudent = createTask();

                for (Variable v : variables) {
                    v.update();
                }

                String evaluatedTask = Float.toString(evaluate(resultForThisStudent));

                try(PrintWriter PWstudent = new PrintWriter(resultFilenamePrefix + i)){
                    PWstudent.println(taskForThisStudent);
                }

                PWresult.println(i + ":");
                PWresult.println(resultForThisStudent + " - " + evaluatedTask);
                PWresult.flush();

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("end");
    }

    private static void readTask(String filename) {

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
                    String n; // name
                    float uB, lB;   //upper bound, lower bound
                    NumberType nT;  // number type

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

            if (resultPattern != null) {
                resultPattern = resultPattern.replace("[[", "").replace("]]", "");
                System.out.println("res pattern: " + resultPattern);
            }
            else {
                try(BufferedReader BR1 = new BufferedReader(new InputStreamReader(System.in))){
                    System.out.println("Result pattern not specified, please insert it. Use the same variable names.");
                    resultPattern = BR1.readLine();
                }
            }

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            System.out.println("Task filename was not specified, now you need to write the blocks of text in here.");
            System.out.println("(If you have finished, write \":end\".)");

            String line;

            boolean readingVariable = false;

            try(BufferedReader BR = new BufferedReader(new InputStreamReader(System.in))){
                while(true) {
                    if (readingVariable == true) {

                        // variable
                        String n; // name
                        float uB, lB;   //upper bound, lower bound
                        NumberType nT;  // number type

                        System.out.println("Write the name of variable:");
                        System.out.println("(If you have finished, write \":end\".)");
                        if((line = BR.readLine()).equals(":end")){
                            break;
                        }
                        else{
                            n = line;

                            System.out.println("Write the number type of variable: I (integer) / F (float)");
                            line = BR.readLine();
                            switch (line) {
                                case "I":
                                    nT = NumberType.INTEGER;
                                    break;
                                case "i":
                                    nT = NumberType.INTEGER;
                                    break;
                                case "F":
                                    nT = NumberType.FLOAT;
                                    break;
                                case "f":
                                    nT = NumberType.FLOAT;
                                    break;
                                default:
                                    throw new Error("Wrong number type selected");
                            }

                            System.out.println("Write the lower bound of variable:");
                            lB = Float.parseFloat(BR.readLine());

                            System.out.println("Write the upper bound of variable:");
                            uB = Float.parseFloat(BR.readLine());

                            // save variables and texts
                            Variable v = new Variable(n, lB, uB, nT);

                            parsedText.add(v);
                            variables.add(v);

                            readingVariable = false;
                        }
                    } else {
                        System.out.println("Write the part of text until next variable:");
                        System.out.println("(If you have finished, write \":end\".)");

                        if ((line = BR.readLine()).equals(":end")) {
                            break;
                        }
                        else {
                            parsedText.add(new Text(line));

                            readingVariable = true;
                        }
                    }
                }

                if(resultPattern == null){
                    System.out.println("Result pattern not specified, please insert it. Use the same variable names.");
                    resultPattern = BR.readLine();
                }

            }
            catch(IOException e1){
                e1.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static float evaluate(String infixExpression) {
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
            if (tokens[i] == '(')
                operators.push(Operator.LPARENTH);

                // Closing brace encountered, solve entire brace
            else if (tokens[i] == ')') {
                while (operators.peek() != Operator.LPARENTH)
                    values.push(applyBinaryOperator(operators.pop(), values.pop(), values.pop()));
                operators.pop(); // Pop left bracket
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


        if(values.size() == 1){
            return values.pop(); // navrchu zasobniku hodnot je vysledek.
        }
        else{
            throw new Error("values stack has more than one value, supposably missing an operator.");
        }

    }

    // je pravda, kdyz 'topOfStack' ma aspon takovou prioritu jako 'newOp',
    // jinak je nepravda.
    private static boolean hasGreaterPriorityThanTopOfStack(Operator newOp, Operator topOfStack) {
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

    // je operator 'op' binarni
    private static boolean isBinary(Operator op){
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

    // aplikuj unarni operator 'op' na hodnotu 'a'
    private static float applyUnaryOperator(Operator op, float a){
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

    // aplikuj binarni operator 'op' na hodnoty 'a' a 'b'
    private static float applyBinaryOperator(Operator op, float b, float a) {
        switch (op) {
            case PLUS:
                return a + b;
            case MINUS:
                return a - b;
            case TIMES:
                return a * b;
            case DIV:
                if (b == 0) {
                    throw new UnsupportedOperationException("Cannot divide by zero");
                }
                return a / b;
            case POW:
                return (float) Math.pow(a,b);
        }
        return 0;
    }

    // dosad hodnoty podle zadani do vzorce
    private static String substituteValuesOfVariables(String input){

        String output = new String(input);  // bal jsem se, aby to nebyla reference na puvodni
        for(Variable v : variables){
            output = output.replaceAll("\\s*" + v.getName() + "\\W"," " + v.getTextValue() + " ");
        }

        return output;
    }

    // vytvor zadani pro studenta
    private static String createTask(){

        StringBuilder SB = new StringBuilder();
        for(TextPart tP : parsedText){
            SB.append(tP.getTextValue());  // hodnoty ulozene v detech TextPartu
        }
        return SB.toString();
    }

    public static File createFile(String fileName){
        File file = new File(fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("File " + fileName + " could not be created.");
            }
        }
        return file;
    }

    private static void initializeVariables(){
        resultPattern = resultFilenamePrefix = inputFilename = null;
        numberOfStudents = -1;
        resultFilename = "./vysledky.txt";

        parsedText = new ArrayList<TextPart>();
        variables = new ArrayList<Variable>();

    }

    private static void readArguments(String[] args){

        for(int i = 0; i < args.length; i++){
            switch (args[i]){
                case "-f":
                    i++;
                    inputFilename = args[i];
                    break;
                case "-s":
                    i++;
                    numberOfStudents = Integer.parseInt(args[i]);
                    break;
                case "-o":
                    i++;
                    resultFilename = args[i];
                    break;
                case "-p":
                    i++;
                    resultFilenamePrefix = args[i];
                    break;
                case "-e":
                    i++;
                    resultPattern = args[i];
                    break;
                default:
                    throw new Error("Unsupported option :" + args[i]);
            }
        }
    }
}



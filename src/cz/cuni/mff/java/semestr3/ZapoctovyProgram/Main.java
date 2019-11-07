package cz.cuni.mff.java.semestr3.ZapoctovyProgram;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    private static String resultExpressionPattern;
    private static List<TextPart> parsedText;
    private static List<Variable> variables;

    private static String inputFilename, studentWordProblemFilenamePrefix, resultFilename;
    private static int numberOfStudents;

    public static void main(String[] args) {
        try(BufferedReader BR = new BufferedReader(new InputStreamReader(System.in))){
            initializeVariables(); // initializes static variables
            readCommandLineArguments(args); // reads command line arguments

            // read word problem either from file or from console
            if(inputFilename != null) {
                readWordProblemFromFile(inputFilename);
            }
            else{
                readWordProblemFromConsole(BR);
            }

            // check if number of students is specified
            if (numberOfStudents == -1) {
                System.out.println("Number of students not specified. Specify it, please:");
                numberOfStudents = Integer.parseInt(BR.readLine());
            }

            // check if prefix of files for students is specified
            if(studentWordProblemFilenamePrefix == null){
                System.out.println("Prefix of student tasks not specified. Specify it, please:");
                studentWordProblemFilenamePrefix = BR.readLine();
            }

            // check if result pattern is specified
            if (resultExpressionPattern != null) {
                // result pattern might come from file, remove guide brackets
                resultExpressionPattern = resultExpressionPattern.replace("[[", "").replace("]]", "");
            }
            else {
                // if not, read it from console
                readResultPatternFromConsole(BR);
            }

            if (resultFilename == null){
                System.out.println("Filename with results of word problems not specified. Specify it, please:");
                resultFilename = BR.readLine();
            }

            File resultsFile = createFile(resultFilename);
            try(PrintWriter PWresult = new PrintWriter(resultsFile)) {

                for(int i = 0; i < numberOfStudents; i++) {
                    String resultPatternForCurrentStudent = substituteValuesOfVariables(resultExpressionPattern);

                    String wordProblemForCurrentStudent = createWordProblem();

                    // generate new values of variables to use for current student
                    for (Variable v : variables) {
                        v.generateNextValue();
                    }

                    // evaluate current word problem
                    String evaluationOfCurrentWordProblem = Float.toString(evaluate(resultPatternForCurrentStudent));

                    File currentStudentWordProblemFile = createFile(studentWordProblemFilenamePrefix + i);
                    try(PrintWriter PWstudent = new PrintWriter(currentStudentWordProblemFile)){
                        PWstudent.println(wordProblemForCurrentStudent);
                    }

                    PWresult.println(i + ":");
                    PWresult.println(resultPatternForCurrentStudent + " ---> " + evaluationOfCurrentWordProblem);
                    PWresult.flush();

                }

                System.out.println("Generating finished successfully.");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }catch(IOException e3){
            e3.printStackTrace();
        }
    }

    // reads problem from file, creates represenation of word problem in memory
    private static void readWordProblemFromFile(String filename) throws IOException {

        try (BufferedReader BR = new BufferedReader(new FileReader(filename))) {

            String line;
            while ((line = BR.readLine()) != null) {
                line += "\n";

                // check if line matches result pattern
                if (line.matches("\\[\\[[^\\]]*\\]\\]\\s*")) {
                    resultExpressionPattern = line;
                    break;
                }

                Pattern variablePattern = Pattern.compile("\\{\\{\\S+\\}\\}", 0);
                Matcher variableMatcher = variablePattern.matcher(line);

                // line split by guide brackets of variables
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
                    String[] nameLBoundUBoundType = innerPart.split(":");

                    n = nameLBoundUBoundType[0];
                    lB = Float.parseFloat(nameLBoundUBoundType[1]);
                    uB = Float.parseFloat(nameLBoundUBoundType[2]);

                    switch (nameLBoundUBoundType[3]) {
                        case "I":
                            nT = NumberType.INTEGER;
                            break;
                        case "F":
                            nT = NumberType.FLOAT;
                            break;

                        default:
                            throw (new Error("wrong number type " + nameLBoundUBoundType[3]));
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
        }

    }

    // reads word problem from console, creates its representation in memory
    private static void readWordProblemFromConsole(BufferedReader IBR) throws IOException{
        System.out.println("Task filename was not specified, now you need to write the blocks of text in here.");
        System.out.println("(If you have finished, type in \":end\".)");

        String line;

        // indicator variable just to know what is being read
        boolean readingVariable = false;

        while(true) {
            if (readingVariable == true) {

                // variable
                String n; // name
                float uB, lB;   //upper bound, lower bound
                NumberType nT;  // number type

                System.out.println("Write the name of variable:");
                System.out.println("(If you have finished, type in \":end\".)");
                if((line = IBR.readLine()).equals(":end")){
                    break;
                }
                else{
                    n = line;

                    System.out.println("Write the number type of variable: I (integer) / F (float)");
                    line = IBR.readLine();
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
                    lB = Float.parseFloat(IBR.readLine());

                    System.out.println("Write the upper bound of variable:");
                    uB = Float.parseFloat(IBR.readLine());

                    // save variables and texts
                    Variable v = new Variable(n, lB, uB, nT);

                    parsedText.add(v);
                    variables.add(v);

                    readingVariable = false;
                }
            } else {
                System.out.println("Write the part of text until next variable:");
                System.out.println("(If you have finished, type in \":end\".)");

                if ((line = IBR.readLine()).equals(":end")) {
                    break;
                }
                else {
                    parsedText.add(new Text(line));

                    readingVariable = true;
                }
            }
        }
    }

    // reads result pattern from console
    private static void readResultPatternFromConsole(BufferedReader BR) throws IOException {
        System.out.println("Result pattern not specified, please insert it. Use the same variable names.");
        resultExpressionPattern = BR.readLine();
    }

    // evaluate the expression - result pattern with values substituted
    private static float evaluate(String infixExpression) {

        char[] tokens = infixExpression.toCharArray();

        Stack<Float> values = new Stack<Float>();
        Stack<Operator> operators = new Stack<Operator>();

        // parse tokens
        for (int i = 0; i < tokens.length; i++) {
            // Current token is a whitespace, skip it
            if (tokens[i] == ' ') {
                continue;
            }

            if (Character.isAlphabetic(tokens[i])) {
                StringBuilder SB = new StringBuilder();

                // read the whole word
                while (i < tokens.length && Character.isAlphabetic(tokens[i])) {
                    SB.append(tokens[i]);
                    i++;
                }

                // check if we know this word
                switch (SB.toString()) {
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
            // check if number is being read
            else if (tokens[i] >= '0' && tokens[i] <= '9') {
                StringBuilder SB = new StringBuilder();

                // read the whole number
                int numberOfPoints = 0;
                while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.') && numberOfPoints <= 1) {
                    SB.append(tokens[i]);
                    if (tokens[i] == '.') {
                        numberOfPoints++;
                    }
                    i++;
                }
                values.push(Float.parseFloat(SB.toString())); // save the number
            }

            // do this part only if the expression continues
            if (i < tokens.length) {

                // current token is an opening bracket, push it to operators
                if (tokens[i] == '(')
                    operators.push(Operator.LPARENTH);

                    // closing bracket, solve the inner part of brackets
                else if (tokens[i] == ')') {
                    while (operators.peek() != Operator.LPARENTH)
                        values.push(applyBinaryOperator(operators.pop(), values.pop(), values.pop()));
                    operators.pop(); // pop left bracket
                } else if (tokens[i] == '+' || tokens[i] == '-' ||
                        tokens[i] == '*' || tokens[i] == '/' || tokens[i] == '^') {

                    Operator op;

                    switch (tokens[i]) {
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

                    // evaluate part with higher priority
                    while (!operators.empty() && hasLowerPriorityThanTopOfStack(op, operators.peek())) {
                        values.push(applyBinaryOperator(operators.pop(), values.pop(), values.pop()));
                    }

                    // push current token to operators
                    operators.push(op);
                }
            }
        }
        // entire expression is parsed at this point, apply remaining
        // operators to values
        while (!operators.empty())
            if (isBinary(operators.peek())){
                values.push(applyBinaryOperator(operators.pop(), values.pop(), values.pop()));
            }
            else {
                values.push(applyUnaryOperator(operators.pop(), values.pop()));
            }


        if(values.size() == 1){
            return values.pop(); // there is the result on top of values stack
        }
        else{
            throw new Error("value stack has more than one value, supposably missing an operator.");
        }

    }

    // true if topOfStack lower priority as newOp, else false
    // priority descending: (,) ; ^ ; *,/ ; +,-
    private static boolean hasLowerPriorityThanTopOfStack(Operator newOp, Operator topOfStack) {
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

    // returns true if operator op is binary
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

    // applies unary operator to number a
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

    // applies binary operator op on numbers a and b -> a `op` b
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

    // substitutes values of variables in result expression
    private static String substituteValuesOfVariables(String input){

        String outputExpression = new String(input);  // bal jsem se, aby to nebyla reference na puvodni
        for(Variable v : variables){
            outputExpression = outputExpression.replaceAll("\\s*" + v.getName() + "\\W"," " + v.getTextValue() + " ");
        }

        return outputExpression;
    }

    // creates word problem for student
    private static String createWordProblem(){

        StringBuilder SB = new StringBuilder();
        for(TextPart tP : parsedText){
            SB.append(tP.getTextValue());  // hodnoty ulozene v detech TextPartu
        }
        return SB.toString();
    }

    // initializes static variables
    private static void initializeVariables(){
        resultExpressionPattern = studentWordProblemFilenamePrefix = inputFilename = resultFilename = null;
        numberOfStudents = -1; // number of students wasn't assigned yet

        parsedText = new ArrayList<TextPart>();
        variables = new ArrayList<Variable>();
    }

    // reads command-line arguments
    private static void readCommandLineArguments(String[] args){

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
                    studentWordProblemFilenamePrefix = args[i];
                    break;
                case "-e":
                    i++;
                    resultExpressionPattern = args[i];
                    break;
                case "-h":
                    printHelp();
                    System.exit(1);
                    break;
                case "--help":
                    printHelp();
                    System.exit(1);
                    break;
                default:
                    throw new Error("Unsupported option :" + args[i]);
            }
        }
    }

    // prints help
    private static void printHelp(){
        System.out.println("Usage: WPGenerator [options]\n" +
                "Options:\n" +
                "-f name_of_file_with_word_problem\n" +
                "       contains text of word problem. In places, where variables are,\n" +
                "       is written \n" +
                "       {{ variable_name : lower_bound : upper_bound : number_type [I/F] }}.\n" +
                "       Pattern of result expression can be specified on a single line like\n" +
                "       [[5*x^2-13*(7/(9*y))+17*2]] -- do not use numbers in variable names.\n\n" +
                "-s number_of_students\n\n" +
                "-o name_of_file_with_results\n\n" +
                "-p prefix_of_filenames_containing_word_problems\n\n" +
                "-e infix_expression_of_result\n" +
                "       use the same names of variables.\n\n" +
                "-h/--help\n" +
                "       prints this help.");
    }

    // creates a file for fileName
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

}



//import com.sun.org.apache.xpath.internal.operations.Bool;
package com.example.msharp;


import android.content.Context;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Parser {
    TextView console;
    Context context;

    public Parser(TextView console, Context context)
    {
        this.console = console;
        this.context = context;
    }

    /* Function to check if given input starts with '#', making it a string. */
    public static boolean isString(String input)
    {
        char[] allChars = input.toCharArray();
        if(allChars[0] == '#')
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /* Function to check if given input is an existing variable in any of the variable arrays. */
    public static boolean isExistingVariable(String input, Map<String,Integer> Numbers, Map<String, String> Strings, Map<String, String> Bools)
    {
        if(Numbers.containsKey(input))
        {
            return true;
        }
        else if(Strings.containsKey(input))
        {
            return true;
        }
        else if(Bools.containsKey(input))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isInteger(String input)
    {
        try
        {
            int temp = Integer.parseInt(input);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public static boolean isBool(String input)
    {
        if(input.equals("true") || input.equals("false") || input.equals("!false") || input.equals("!true"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isExpressionWithOperator (String input)
    {
        String[] splitInput = input.split(" ",3 );
        if(splitInput.length == 3)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    /* Function that takes a string and processes it into a condition. */
    public static Expression processCondition (String input, Map<String, Integer> Numbers, Map<String, String> Strings, Map<String,String> Bools)
    {

        String[] expression = input.split(" ",3 );
        Factor lhs;
        Factor rhs;

        /* Is the left hand side of the expression an int or an existing variable? */
        /*
        if(isExistingVariable(expression[2], Numbers, Strings, Bools))
        {
            rhs = new VariableFactor(expression[2]);
        }
        */
        if(isBool(expression[0]))
        {
            lhs = new BoolFactor(expression[0]);
        }
        else if (isInteger(expression[0]))
        {
            lhs = new IntegerFactor(Integer.parseInt(expression[0]));
        }
        else
        {
            lhs = new VariableFactor(expression[0],Numbers, Strings, Bools);
        }

        /* Is the right hand side of the expression an int or an existing variable? */
        /*
        if(isExistingVariable(expression[2], Numbers, Strings, Bools))
        {
            rhs = new VariableFactor(expression[2]);
        }
        */

        if(isBool(expression[2]))
        {
            rhs = new BoolFactor(expression[2]);
        }
        else if(isInteger(expression[2]))
        {
            rhs = new IntegerFactor(Integer.parseInt(expression[2]));
        }
        else
        {
            rhs = new VariableFactor(expression[2],Numbers, Strings, Bools);
        }

        switch(expression[1])
        {
            case "<":
                RelationExpression newLT = new RelationExpression(lhs, "<",rhs, Numbers);
                return  newLT;
            case ">":
                RelationExpression newGT = new RelationExpression(lhs, ">",rhs, Numbers);
                return newGT;
            case "<=":
                RelationExpression newLTE = new RelationExpression(lhs,"<=",rhs, Numbers);
                return newLTE;
            case ">=":
                RelationExpression newGTE = new RelationExpression(lhs,">=",rhs, Numbers);
                return  newGTE;
            case "==":
                EqualExpression newEq = new EqualExpression(lhs, "==", rhs, Numbers, Strings, Bools);
                return newEq;
            case "!=":
                EqualExpression newNe = new EqualExpression(lhs, "!=", rhs, Numbers, Strings, Bools);
                return  newNe;
            case "&&":
                AndExpression newAa = new AndExpression(lhs, "&&", rhs, Bools);
                return  newAa;
            case "||":
                AndExpression newOo = new AndExpression(lhs, "||", rhs, Bools);
                return  newOo;
        }
        return null;
    }

    /* Function that takes a string and processes it into an expression. Expects: (Factor; Operator; Factor) */
    public static Expression processExpression (String input,  Map<String, Integer> Numbers, Map<String, String> Strings, Map<String, String> Bools)
    {

        String[] expression = input.split(" ",3 );
        Factor lhs;
        Factor rhs;

        /* Is the left hand side of the expression an int or an existing variable? */
  /*      if(isExistingVariable(expression[0], Numbers, Strings, Bools))
        {
            lhs = new VariableFactor(expression[0]);
        }*/
        if(isBool(expression[0]))
        {
            lhs = new BoolFactor(expression[0]);
        }
        else if (isInteger(expression[0]))
        {
            lhs = new IntegerFactor(Integer.parseInt(expression[0]));
        }
        else
        {
            lhs = new VariableFactor(expression[0], Numbers, Strings, Bools);
        }

        /* Is the right hand side of the expression an int or an existing variable? */
/*        if(isExistingVariable(expression[2], Numbers, Strings, Bools))
        {
            rhs = new VariableFactor(expression[2]);
        }*/
        if(isBool(expression[2]))
        {
            rhs = new BoolFactor(expression[2]);
        }
        else if(isInteger(expression[2]))
        {
            rhs = new IntegerFactor(Integer.parseInt(expression[2]));
        }
        else
        {
            rhs = new VariableFactor(expression[2], Numbers, Strings, Bools);
        }

        switch(expression[1])
        {
            case "+":
                AddExpression newAddExp = new AddExpression(lhs, "+",rhs, Numbers);
                return newAddExp;

            case "-":
                AddExpression newMinusExp = new AddExpression(lhs, "-",rhs, Numbers);
                return  newMinusExp;

            case "*":
                MultExpression newTimesExp = new MultExpression(lhs, "*",rhs, Numbers, Strings, Bools);
                return  newTimesExp;

            case "/":
                MultExpression newDivideExp = new MultExpression(lhs, "/",rhs, Numbers, Strings, Bools);
                return newDivideExp;

            case "%":
                MultExpression newModExp = new MultExpression(lhs, "%", rhs, Numbers, Strings, Bools);
                return  newModExp;
            case "^":
                MultExpression newPowExp = new MultExpression(lhs, "^", rhs, Numbers, Strings, Bools);
                return  newPowExp;
            case "<":
                RelationExpression newLT = new RelationExpression(lhs, "<",rhs, Numbers);
                return  newLT;
            case ">":
                RelationExpression newGT = new RelationExpression(lhs, ">",rhs, Numbers);
                return newGT;
            case "<=":
                RelationExpression newLTE = new RelationExpression(lhs,"<=",rhs, Numbers);
                return newLTE;
            case ">=":
                RelationExpression newGTE = new RelationExpression(lhs,">=",rhs, Numbers);
                return  newGTE;
            case "==":
                EqualExpression newEq = new EqualExpression(lhs, "==", rhs, Numbers, Strings, Bools);
                return newEq;
            case "!=":
                EqualExpression newNe = new EqualExpression(lhs, "!=", rhs, Numbers, Strings, Bools);
                return  newNe;
            case "&&":
                AndExpression newAa = new AndExpression(lhs, "&&", rhs, Bools);
                return  newAa;
            case "||":
                AndExpression newOo = new AndExpression(lhs, "||", rhs, Bools);
                return  newOo;
        }
        return null;
    }

    /* The parser. Reads raw code and transforms it into an AST. */
    public void ParseTokens(String Tokens[], String line, Map<String, Integer> Numbers, Map<String, String> Strings, Map<String, String> Bools, Program program, int currentLine, ArrayList<String> rawCode)
    {
            /* Tokens[0] is the first token on a line of code.
            It tells the parser what type of statement to expect. */
            switch (Tokens[0])
            {
                /* Assignment Statement */
                case "let": /* Examples: let x = 1 + 1, let x = 17, let x = #hello, let x = y, let x = y + 1, let x = true, let x = y < 4*/

                    /* Separate the expression from the variable name and "=" sign. ie: {"x = ","y + 1"} */
                    String[] varAndExpression = line.split("= ",2 );

                    /* Our new variable will be read from the console. ie: let x = read*/
                    if(varAndExpression[1].equals("read"))
                    {
                        ReadInExpression newExpression = new ReadInExpression(context);
                        AssignmentStatement newAssignmentStatement = new AssignmentStatement(Tokens[1], newExpression,Numbers,Strings, Bools);
                        program.Add(newAssignmentStatement);
                    }

                    /* Our new variable is a string. ie: let x = #how now brown cow */
                    else if(isString(varAndExpression[1]))
                    {
                        StringFactor newFactor = new StringFactor(varAndExpression[1]);
                        AssignmentStatement newAssignmentStatement = new AssignmentStatement(Tokens[1], newFactor, Numbers, Strings, Bools);
                        program.Add(newAssignmentStatement);
                    }

                    /* Our new variable is an int. ie: let x = 77 */
                    else if(isInteger(varAndExpression[1]))
                    {
                        IntegerFactor newFactor = new IntegerFactor(Integer.parseInt(varAndExpression[1]));
                        AssignmentStatement newAssignmentStatement = new AssignmentStatement(Tokens[1], newFactor, Numbers, Strings, Bools);
                        program.Add(newAssignmentStatement);
                    }
                    /* Our new variable is a boolean. ie: let x = true */
                    else if (isBool(varAndExpression[1]))
                    {
                        BoolFactor newBoolFactor = new BoolFactor(varAndExpression[1]);
                        AssignmentStatement newAssignmentStatement = new AssignmentStatement(Tokens[1], newBoolFactor, Numbers, Strings, Bools);
                        program.Add(newAssignmentStatement);
                    }

                    /* Our new variable is copying an existing variable. ie: let x = y */


                    /* Our new variable has an expression that needs to be evaluated first. ie let x = y + 1,let x = y != 3 */
                    else if(isExpressionWithOperator(varAndExpression[1]))
                    {
                        Expression newExpression = processExpression(varAndExpression[1], Numbers, Strings, Bools);
                        AssignmentStatement newAssignmentStatement = new AssignmentStatement((Tokens[1]), newExpression, Numbers, Strings, Bools);
                        program.Add(newAssignmentStatement);
                    }
                    else /* if(isExistingVariable(varAndExpression[1], Numbers, Strings, Bools)) */
                    {
                        VariableFactor newVariableFactor = new VariableFactor(varAndExpression[1], Numbers, Strings, Bools);
                        AssignmentStatement newAssignmentStatement_5 = new AssignmentStatement(Tokens[1], newVariableFactor, Numbers, Strings, Bools);
                        program.Add(newAssignmentStatement_5);
                    }
                    break;

                /* Console Output Statement */
                case "print":

                    String[] printAndExpression = line.split(" ",2 );

                    /* Printing string */
                    if(isString(printAndExpression[1]))
                    {
                        StringFactor newStringFactor = new StringFactor(printAndExpression[1]);
                        ConsoleOutStatement newConsoleOutStatement_1 = new ConsoleOutStatement(newStringFactor, Numbers, Strings, Bools, console);
                        program.Add(newConsoleOutStatement_1);
                    }
                    /* Printing an int */
                    else if(isInteger(printAndExpression[1]))
                    {
                        IntegerFactor newIntegerFactor = new IntegerFactor(Integer.parseInt(printAndExpression[1]));
                        ConsoleOutStatement newConsoleOutStatement_2 = new ConsoleOutStatement(newIntegerFactor, Numbers, Strings, Bools, console);
                        program.Add(newConsoleOutStatement_2);
                    }
                    /* Printing a bool */
                    else if(isBool(printAndExpression[1]))
                    {
                        BoolFactor newBoolFactor = new BoolFactor(printAndExpression[1]);
                        ConsoleOutStatement newConsoleOutStatement_2_1 = new ConsoleOutStatement(newBoolFactor, Numbers, Strings, Bools, console);
                        program.Add(newConsoleOutStatement_2_1);
                    }
                    /* Printing the value of an existing variable. */

                    /* Printing the evaluation of an expression. */
                    else if(isExpressionWithOperator(printAndExpression[1]))
                    {
                        Expression newExpression = processExpression(printAndExpression[1], Numbers, Strings, Bools);
                        ConsoleOutStatement newConsoleOutStatement_4 = new ConsoleOutStatement(newExpression, Numbers, Strings, Bools, console);
                        program.Add(newConsoleOutStatement_4);
                    }
                    else /* if(isExistingVariable(printAndExpression[1], Numbers, Strings, Bools)) */
                    {
                        VariableFactor newVariableFactor = new VariableFactor(printAndExpression[1], Numbers, Strings, Bools);
                        ConsoleOutStatement newConsoleOutStatement_3 = new ConsoleOutStatement(newVariableFactor, Numbers, Strings, Bools, console);
                        program.Add(newConsoleOutStatement_3);
                    }
                    break;


                case "array":


                    break;

                /* If Statement */
                case "if":

                    String[] ifAndExpression = line.split(" ",2 );

                    int ifStart = currentLine + 1;

                    String[] expression = ifAndExpression[1].split(" ",3 );
                    Factor lhs;
                    Factor rhs;
                    String op = "";

                    /* Is the left hand side of the expression an int or an existing variable? */
/*                    if(isExistingVariable(expression[0], Numbers, Strings, Bools))
                    {
                        lhs = new VariableFactor(expression[0]);
                    }*/
                    if(isBool(expression[0]))
                    {
                        lhs = new BoolFactor(expression[0]);
                    }
                    else if(isInteger(expression[0]))
                    {
                        lhs = new IntegerFactor(Integer.parseInt(expression[0]));
                    }
                    else
                    {
                        lhs = new VariableFactor(expression[0], Numbers, Strings, Bools);
                    }

                    /* Is the right hand side of the expression an int or an existing variable? */
/*                    if(isExistingVariable(expression[2], Numbers, Strings, Bools))
                    {
                        rhs = new VariableFactor(expression[2]);
                    }*/
                    if(isBool(expression[2]))
                    {
                        rhs = new BoolFactor(expression[2]);
                    }
                    else if(isInteger(expression[2]))
                    {
                        rhs = new IntegerFactor(Integer.parseInt(expression[2]));
                    }
                    else
                    {
                        rhs = new VariableFactor(expression[2], Numbers, Strings, Bools);
                    }

                    op = expression[1];
                    //this gets the expression added
                    //now for the code that follows

                    String codeBodyLine = rawCode.get(currentLine + 1);
                    currentLine++;
                    String codeBodyLineTokens[] = codeBodyLine.split(" ");
                    List<String> codeBody = new ArrayList<String>();


                    /* Get the code to be executed under the condition. */
                    int ifCount = 0;
                    while(!codeBodyLineTokens[0].equals("ifEnd") || ifCount != 0)
                    {
                        if(codeBodyLineTokens[0].equals("if"))
                        {
                            ifCount++;
                        }
                        if(codeBodyLineTokens[0].equals("ifEnd"))
                        {
                            ifCount--;
                        }
                        codeBody.add(codeBodyLine);
                        codeBodyLine = rawCode.get(currentLine + 1);
                        currentLine++;
                        codeBodyLineTokens = codeBodyLine.split(" ");
                    }

                    Program tempProgram = new Program();
                    Boolean needToSkipIf_1 = false;
                    Boolean needToSkipWhile_1 = false;

                    for(int x = 0; x < codeBody.size(); x++)
                    {
                        String temp = codeBody.get(x);
                        String[] tempTokens = temp.split(" ");

                        if(!needToSkipIf_1 && !needToSkipWhile_1)
                        {
                            ParseTokens(tempTokens, temp, Numbers, Strings, Bools, tempProgram, ifStart, rawCode);
                        }
                        if(tempTokens[0].equals("ifEnd"))
                        {
                            needToSkipIf_1 = false;
                        }
                        if(tempTokens[0].equals("whileEnd"))
                        {
                            needToSkipWhile_1 = false;
                        }
                        if(tempTokens[0].equals("if"))
                        {
                            needToSkipIf_1 = true;
                        }
                        if(tempTokens[0].equals("while"))
                        {
                            needToSkipWhile_1 = true;
                        }

                    }

                    IfStatement newIfStatement = new IfStatement(lhs, op, rhs, tempProgram.statements , currentLine, Numbers, Strings, Bools, this);

                    program.Add(newIfStatement);

                    break;

                /* While statement */
                case "while":
                    String[] whileAndExpression = line.split(" ",2 );

                    int whileStart = currentLine + 1;

                    String[] expression_2 = whileAndExpression[1].split(" ",3 );
                    Factor lhs_2;
                    Factor rhs_2;
                    String op_2 = "";

                    /* Is the left hand side of the expression an int or an existing variable? */
/*                    if(isExistingVariable(expression_2[0], Numbers, Strings, Bools))
                    {
                        lhs_2 = new VariableFactor(expression_2[0]);
                    }*/
                    if(isBool(expression_2[0]))
                    {
                        lhs_2 = new BoolFactor(expression_2[0]);
                    }
                    else if(isInteger(expression_2[0]))
                    {
                        lhs_2 = new IntegerFactor(Integer.parseInt(expression_2[0]));
                    }
                    else
                    {
                        lhs_2 = new VariableFactor(expression_2[0], Numbers, Strings, Bools);
                    }

                    /* Is the right hand side of the expression an int or an existing variable? */
/*                    if(isExistingVariable(expression_2[2], Numbers, Strings, Bools))
                    {
                        rhs_2 = new VariableFactor(expression_2[2]);
                    }*/
                    if(isBool(expression_2[2]))
                    {
                        rhs_2 = new BoolFactor(expression_2[2]);
                    }
                    else if(isInteger(expression_2[2]))
                    {
                        rhs_2 = new IntegerFactor(Integer.parseInt(expression_2[2]));
                    }
                    else
                    {
                        rhs_2 = new VariableFactor(expression_2[2],Numbers, Strings, Bools);
                    }

                    op_2 = expression_2[1];

                    /* Initialize temp values for processing the code in the while statement. */
                    //String codeBodyLine_while = scan.nextLine();
                    String codeBodyLine_while = rawCode.get(currentLine + 1);
                    currentLine++;
                    String codeBodyLineTokens_while[] = codeBodyLine_while.split(" ");
                    List<String> codeBody_while = new ArrayList<String>();

                    /* Get the code to be executed under the condition. */

                    int whileCount = 0;
                    while(!codeBodyLineTokens_while[0].equals("whileEnd") || whileCount != 0)
                    {
                        if(codeBodyLineTokens_while[0].equals("while"))
                        {
                            whileCount++;
                        }
                        if(codeBodyLineTokens_while[0].equals("whileEnd"))
                        {
                            whileCount--;
                        }
                        codeBody_while.add(codeBodyLine_while);
                        codeBodyLine_while = rawCode.get(currentLine + 1);
                        currentLine++;
                        codeBodyLineTokens_while = codeBodyLine_while.split(" ");
                    }

                    Program tempProgram_while = new Program();
                    Boolean needToSkipIf = false;
                    Boolean needToSkipWhile = false;

                    for(int x = 0; x < codeBody_while.size(); x++)
                    {

                        String temp = codeBody_while.get(x);
                        String[] tempTokens = temp.split(" ");

                        if(!needToSkipIf && !needToSkipWhile)
                        {
                            ParseTokens(tempTokens, temp, Numbers, Strings, Bools, tempProgram_while, whileStart, rawCode);
                        }
                        if(tempTokens[0].equals("ifEnd"))
                        {
                            needToSkipIf = false;
                        }
                        if(tempTokens[0].equals("whileEnd"))
                        {

                                needToSkipWhile = false;

                        }
                        if(tempTokens[0].equals("if"))
                        {
                            needToSkipIf = true;
                        }
                        if(tempTokens[0].equals("while"))
                        {
                            needToSkipWhile = true;

                        }


                    }

                    WhileStatement newWhileStatement = new WhileStatement(lhs_2, op_2, rhs_2, tempProgram_while.statements, currentLine, Numbers, Strings, Bools, this);
                    program.Add(newWhileStatement);
                    break;

/*
                case "for":     //for: x 1 to 10
                    //print x
                    //loopEnd

                    if(!Numbers.containsKey(Tokens[1]) && !Strings.containsKey(Tokens[1]))
                    {
                        Numbers.put(Tokens[1], Integer.parseInt(Tokens[2]));

                        int loopCnt = Integer.parseInt(Tokens[4]);
                        int loopStart = Integer.parseInt(Tokens[2]);

                        String loopLine =  "";
                        String loopLineTonkens[] = loopLine.split(" ");
                        List<String> loopLines = new ArrayList<String>();
                        while(!loopLineTonkens[0].equals("loopEnd"))
                        {
                            loopLine = scan.nextLine();
                            loopLines.add(loopLine);
                            loopLineTonkens = loopLine.split(" ");
                        }
                        for (int x = loopStart; x < loopCnt + 1; x++)
                        {
                            for (int y = 0; y < loopLines.size(); y++)
                            {
                                Numbers.replace(Tokens[1], x);
                                String lineToProcess = loopLines.get(y);
                                String lineToProcessTokens[] = lineToProcess.split(" ");
                                ParseTokens(lineToProcessTokens, lineToProcess, scan, Numbers, Strings, program);
                            }
                        }
                        break;
                    }
                    else
                    {
                        System.out.println(Tokens[1] + " is already a declared identifier.");
                    }


                case "loop":        //outdated loop code: loop 10 -> repeats loop 10 times
                    int loopCnt;
                    try
                    {
                        loopCnt = Integer.parseInt(Tokens[1]);
                    }
                    catch (NumberFormatException e)
                    {
                        loopCnt = Numbers.get(Tokens[1]);
                    }

                    String loopLine =  "";
                    String loopLineTonkens[] = loopLine.split(" ");
                    List<String> loopLines = new ArrayList<String>();
                    while(!loopLineTonkens[0].equals("fin"))
                    {
                        loopLine = scan.nextLine();
                        loopLines.add(loopLine);
                        loopLineTonkens = loopLine.split(" ");
                    }
                    for (int x = 0; x < loopCnt; x++)
                    {
                        for (int y = 0; y < loopLines.size(); y++)
                        {
                            String lineToProcess = loopLines.get(y);
                            String lineToProcessTokens[] = lineToProcess.split(" ");
                            ParseTokens(lineToProcessTokens, lineToProcess, scan, Numbers, Strings, program);
                        }
                    }
                    break;
*/

                case "":
                    break;




                default:
                    //System.out.println("Ok, you got me, I have no idea what code you put here on line");

            }
        }


}

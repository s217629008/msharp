package com.example.msharp;

import java.util.ArrayList;
import java.util.Map;

public class Functions {

    /* Function to check if given input starts with '#', making it a string. */
    public boolean isString(String input)
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
    public boolean isExistingVariable(String input, Map<String,Integer> Numbers, Map<String, String> Strings, Map<String, String> Bools) throws Exception {
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
            Exception e = new Exception("Variable '" + input + "' does not exist");
            throw  e;

        }
    }


    public boolean checkValidExpression (String input) throws Exception {

        if(input.indexOf(' ') == -1) //its a variable
        {
            return true;
        }
        try{
            String[] expression = input.split(" ",3 ); //is an expression with an operator
        }
        catch (Exception e)
        {
            Exception o = new Exception("Expressions with multiple terms must be of the form 'Factor Operator Factor'");
            throw o;
        }
        String[] expression = input.split(" ",3 );

        /* Is the left hand side of the expression an int or an existing variable? */
  /*      if(isExistingVariable(expression[0], Numbers, Strings, Bools))
        {
            lhs = new VariableFactor(expression[0]);
        }*/


        if(expression[2].indexOf(' ') != -1)
        {
            Exception e = new Exception("Too many arguments");
            throw e;
        }

        if(isBool(expression[0]))
        {
            if(isInteger(expression[2]))
            {
                Exception e = new Exception("Factor types must match.");
                throw e;
            }
        }

        if(isInteger(expression[0]))
        {
            if(isBool(expression[2]))
            {
                Exception e = new Exception("Factor types must match.");
                throw e;
            }
        }

        switch(expression[1])
        {
            case "+":
            case "-":
            case "*":
            case "/":
            case "<":
            case ">":
            case "<=":
            case ">=":

                if(isBool(expression[0]))
                {
                    Exception e = new Exception("Invalid operator");
                    throw e;
                }
                break;

            case "==":

            case "!=":

                break;

            case "&&":

            case "||":

                if(isInteger(expression[0]))
                {
                    Exception e = new Exception("Invalid operator");
                    throw e;
                }
                break;

            default:
                Exception e = new Exception("Invalid operator");
                throw e;


        }
        return true;
    }

    public boolean checkIsValidCondition(String input) throws Exception {


        if(input.indexOf(' ') == -1) //its a variable
        {
            return true;
        }
        try{
            String[] expression = input.split(" ",3 ); //is an expression with an operator
        }
        catch (Exception e)
        {
            Exception o = new Exception("Expressions with multiple terms must be of the form 'Factor Operator Factor'");
            throw o;
        }
        String[] expression = input.split(" ",3 );

        /* Is the left hand side of the expression an int or an existing variable? */
  /*      if(isExistingVariable(expression[0], Numbers, Strings, Bools))
        {
            lhs = new VariableFactor(expression[0]);
        }*/


        if(expression[2].indexOf(' ') != -1)
        {
            Exception e = new Exception("Too many arguments");
            throw e;
        }

        if(isBool(expression[0]))
        {
            if(isInteger(expression[2]))
            {
                Exception e = new Exception("Factor types must match.");
                throw e;
            }
        }

        if(isInteger(expression[0]))
        {
            if(isBool(expression[2]))
            {
                Exception e = new Exception("Factor types must match.");
                throw e;
            }
        }

        switch(expression[1])
        {
            case "<":
            case ">":
            case "<=":
            case ">=":

                if(isBool(expression[0]))
                {
                    Exception e = new Exception("Invalid operator");
                    throw e;
                }
                break;

            case "==":

            case "!=":

                break;

            case "&&":

            case "||":

                if(isInteger(expression[0]))
                {
                    Exception e = new Exception("Invalid operator");
                    throw e;
                }
                break;

            default:
                Exception e = new Exception("Invalid operator");
                throw e;


        }
        return true;
    }

    public boolean isInteger(String input)
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

    public boolean isBool(String input)
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

    public boolean isExpressionWithOperator (String input)
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

    public static boolean isNotted(String input)
    {
        char[] allChars = input.toCharArray();
        if(allChars[0] == '!')
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isExistingBoolVariable(String input,  Map<String, String> Bools)
    {
        if(Bools.containsKey(input))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public static boolean isExistingIntVariable(String input,  Map<String, Integer> Numbers)
    {
        if(Numbers.containsKey(input))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}

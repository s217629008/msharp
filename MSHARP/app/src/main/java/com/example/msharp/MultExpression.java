package com.example.msharp;

import java.util.Map;

/* Multiplication expressions:
Covers multiplication ("*"), division ("/") and exponents ("^"). */
public class MultExpression extends Expression{
    public Factor leftHandSide;
    public String operator;
    public Factor rightHandSide;
    Map<String,Integer> Numbers;
    Map<String, String> Strings;
    Map<String, String> Bools;
    public int result;

    public  MultExpression(Factor leftHandSide, String operator, Factor rightHandSide, Map<String,Integer> Numbers, Map<String, String> Strings, Map<String, String> Bools)
    {
        this.leftHandSide = leftHandSide;
        this.operator = operator;
        this.rightHandSide = rightHandSide;
        this.Numbers = Numbers;
    }

    public static boolean isInteger(String input) {
        try {
            int temp = Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void execute() throws Exception {
        int rhs;
        int lhs;
        if(isInteger(this.leftHandSide.rawInput()))
        {
            lhs = Integer.parseInt(leftHandSide.rawInput());
        }
        else if(Numbers.containsKey(leftHandSide.rawInput()))
        {
            lhs = Numbers.get(leftHandSide.rawInput());
        }
        else //unknown variable
        {
            Exception e = new Exception("Variable does not exist ");
            throw e;
        }
        if(isInteger(this.rightHandSide.rawInput()))
        {
            rhs = Integer.parseInt(rightHandSide.rawInput());
        }
        else if(Numbers.containsKey(rightHandSide.rawInput()))
        {
            rhs = Numbers.get(rightHandSide.rawInput());
        }
        else //unknown variable
        {
            Exception e = new Exception("Variable does not exist ");
            throw e;
        }

        switch(operator)
        {
            case "*":

                this.result = rhs * lhs;
                break;

            case "/":

                this.result = lhs / rhs;
                break;
            case "%":

                this.result = lhs % rhs;
                break;
            case "^" :
                this.result = (int)Math.pow(lhs, rhs);
                break;
        }

    }

    @Override
    public int type() {
        return 3;
    }

    @Override
    public int resultInt() {
        return result;
    }

    @Override
    public String resultBool() {
        return null;
    }

    @Override
    public String resultString() {
        return null;
    }
}

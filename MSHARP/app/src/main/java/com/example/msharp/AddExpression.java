package com.example.msharp;

import com.google.android.material.navigation.NavigationView;

import java.util.Map;
//happy with this
public class AddExpression extends Expression{
    public Factor leftHandSide;
    public String operator;
    public Factor rightHandSide;
    public Map<String, Integer> Numbers;
    public int result;

    public  AddExpression(Factor leftHandSide, String operator, Factor rightHandSide, Map<String, Integer> Numbers)
    {
        this.leftHandSide = leftHandSide;
        this.operator = operator;
        this.rightHandSide = rightHandSide;
        this.Numbers = Numbers;

    }

    @Override
    public void execute() throws Exception {
        Functions fun = new Functions();

        int rhs;
        int lhs;
        if(fun.isInteger(this.leftHandSide.rawInput()))
        {
            lhs = Integer.parseInt(leftHandSide.rawInput());
        }
        else
        {
            if(Numbers.containsKey(leftHandSide.rawInput()))
            {
                lhs = Numbers.get(leftHandSide.rawInput());
            }
            else
            {
                Exception e = new Exception("Variable does not exist");
                throw e;
            }
        }
        if(fun.isInteger(this.rightHandSide.rawInput()))
        {
            rhs = Integer.parseInt(rightHandSide.rawInput());
        }
        else
        {
            if(Numbers.containsKey(rightHandSide.rawInput()))
            {
                rhs = Numbers.get(rightHandSide.rawInput());
            }
            else
            {
                Exception e = new Exception("Variable does not exist");
                throw e;
            }
        }

        switch(operator)
        {
            case "+":

                this.result = rhs + lhs;
                break;
            case "-":

                this.result = lhs - rhs;
                break;
        }

    }

    @Override
    public int type() {
        return 0;
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

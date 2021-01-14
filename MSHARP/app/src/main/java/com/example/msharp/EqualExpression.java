package com.example.msharp;

import java.util.Map;
//good
public class EqualExpression extends Expression{

    public Factor leftHandSide;
    public String operator;
    public Factor rightHandSide;
    Map<String,Integer> Numbers;
    Map<String, String> Strings;
    Map<String, String> Bools;
    public Boolean result;

    public  EqualExpression(Factor leftHandSide, String operator, Factor rightHandSide, Map<String,Integer> Numbers, Map<String, String> Strings, Map<String, String> Bools)
    {
        this.leftHandSide = leftHandSide;
        this.operator = operator;
        this.rightHandSide = rightHandSide;
        this.Numbers = Numbers;
        this.Strings = Strings;
        this.Bools = Bools;
    }


    @Override
    public void execute() throws Exception {
        Functions fun = new Functions();

        int lhs;
        int rhs;
        //LHS
        if(fun.isInteger(leftHandSide.rawInput())) //is it an int?
        {
            lhs = Integer.parseInt(leftHandSide.rawInput());
        }
        else if(Numbers.containsKey(leftHandSide.rawInput())) //int var?
        {
            lhs = Numbers.get(leftHandSide.rawInput());
        }
        else //its a bool?
        {

                String[] split = leftHandSide.rawInput().split("!",2);
                if(split[0].equals("")) // notted
                {
                    if(Bools.containsKey(split[1]))
                    {
                        if(Bools.get(split[1]).equals("true"))
                        {
                            lhs = 0;
                        }

                        else
                        {
                            lhs = 1;
                        }
                    }
                    else if(fun.isBool(split[1]))
                    {
                        if(split[1] == "true")
                        {
                            lhs = 0;
                        }

                        else
                        {
                            lhs = 1;
                        }
                    }
                    else //unknown variable
                    {
                        Exception e = new Exception("Variable does not exist ");
                        throw e;
                    }
                }
                else    //!notted
                {
                    if(Bools.containsKey(leftHandSide.rawInput()))
                    {
                        if(Bools.get(leftHandSide.rawInput()).equals("true"))
                        {
                            lhs = 1;
                        }

                        else
                        {
                            lhs = 0;
                        }
                    }
                    else if(fun.isBool(leftHandSide.rawInput()))
                    {
                        if(leftHandSide.rawInput().equals("true"))
                        {
                            lhs = 1;
                        }

                        else
                        {
                            lhs = 0;
                        }
                    }
                    else //unknown variable
                    {
                        Exception e = new Exception("Variable does not exist ");
                        throw e;
                    }
                }


        }
        //rhs
        if(fun.isInteger(rightHandSide.rawInput())) //is it an int?
        {
            rhs = Integer.parseInt(rightHandSide.rawInput());
        }
        else if(Numbers.containsKey(rightHandSide.rawInput())) //int var?
        {
            rhs = Numbers.get(rightHandSide.rawInput());
        }
        else //its a bool
        {

            String[] split = rightHandSide.rawInput().split("!",2);
            if(split[0].equals("")) // notted
            {
                if(Bools.containsKey(split[1]))
                {
                    if(Bools.get(split[1]).equals("true"))
                    {
                        rhs = 0;
                    }

                    else
                    {
                        rhs = 1;
                    }
                }
                else if(fun.isBool(split[1]))
                {
                    if(split[1].equals("true"))
                    {
                        rhs = 0;
                    }

                    else
                    {
                        rhs = 1;
                    }
                }
                else //unknown variable
                {
                    Exception e = new Exception("Variable does not exist ");
                    throw e;
                }
            }
            else    //!notted
            {
                if(Bools.containsKey(rightHandSide.rawInput()))
                {
                    if(Bools.get(rightHandSide.rawInput()).equals("true"))
                    {
                        rhs = 1;
                    }

                    else
                    {
                        rhs = 0;
                    }
                }
                else if(fun.isBool(rightHandSide.rawInput()))
                {
                    if(rightHandSide.rawInput().equals("true"))
                    {
                        rhs = 1;
                    }

                    else
                    {
                        rhs = 0;
                    }
                }
                else //unknown variable
                {
                    Exception e = new Exception("Variable does not exist ");
                    throw e;
                }
            }


        }



        switch (operator)
        {
            case "==":
                result = lhs == rhs;
                break;

            case "!=":
                result = lhs != rhs;
                break;

        }
    }

    @Override
    public int type() {
        return 2;
    }

    @Override
    public int resultInt() {
        return 0;
    }

    @Override
    public String resultBool() {
        if(result){
            return "true";
        }
        else
        {
            return "false";
        }
    }

    @Override
    public String resultString() {
        return null;
    }
}

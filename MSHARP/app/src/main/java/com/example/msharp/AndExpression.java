package com.example.msharp;

import java.util.Map;
// happy this works
/* And expressions:
(Confusingly) covers both AND ("&&") and OR ("||") operations between two elements. */
public class AndExpression extends  Expression
{
    public Factor leftHandSide;
    public String operator;
    public Factor rightHandSide;
    public Map<String,String> Bools;
    public boolean result;

    public  AndExpression(Factor leftHandSide, String operator, Factor rightHandSide, Map<String,String> Bools)
    {
        this.leftHandSide = leftHandSide;
        this.operator = operator;
        this.rightHandSide = rightHandSide;
        this.Bools = Bools;
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

    @Override
    public void execute() throws Exception {
        Functions fun = new Functions();

        int lhs;
        int rhs;
        //LHS

         //its a bool


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
                else if(isBool(split[1]))
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
                else if(isBool(leftHandSide.rawInput()))
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



        //rhs

         //its a bool


            String[] split2 = rightHandSide.rawInput().split("!",2);
            if(split2[0].equals("")) // notted
            {
                if(Bools.containsKey(split2[1]))
                {
                    if(Bools.get(split2[1]).equals("true"))
                    {
                        rhs = 0;
                    }

                    else
                    {
                        rhs = 1;
                    }
                }
                else if(isBool(split2[1]))
                {
                    if(split2[1].equals("true"))
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
                else if(isBool(rightHandSide.rawInput()))
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





        switch (operator)
        {
            case "&&":
                if(lhs == 1 && rhs == 1)
                {
                    result = true;
                }
                else
                {
                    result = false;
                }
                break;

            case "||":
                if(lhs == 1 || rhs == 1)
                {
                    result = true;
                }
                else
                {
                    result = false;
                }
                break;

        }
    }

    @Override
    public int type() {
        return 1;
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

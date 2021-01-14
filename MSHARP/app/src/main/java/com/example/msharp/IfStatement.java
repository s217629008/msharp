package com.example.msharp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* If statements */
public class IfStatement extends Statement
{
    public Factor leftHandSide;
    public String operator;
    public Factor rightHandSide;
    public ArrayList<Statement> codeBody;
    public Integer startIndex;
    public Map<String,Integer> Numbers;
    public Map<String, String> Strings;
    public Map<String, String> Bools;
    public Parser parser;
    //public ArrayList<Statement> elseCodeBody;


    public IfStatement(Factor leftHandSide, String operator, Factor rightHandSide, ArrayList<Statement> codeBody, Integer startIndex /*,ArrayList<Statement> elseCodeBody*/,Map<String,Integer> Numbers, Map<String, String> Strings, Map<String, String> Bools, Parser parser)
    {
        this.leftHandSide = leftHandSide;
        this.operator = operator;
        this.rightHandSide = rightHandSide;
        this.codeBody = codeBody;
        this.startIndex = startIndex;
        this.Strings = Strings;
        this.Bools = Bools;
        this.Numbers = Numbers;
        this.parser = parser;
        //this.elseCodeBody = elseCodeBody;
    }
    @Override
    public void execute() throws Exception {

        Functions fun = new Functions();


        boolean isNumber = false;
        boolean isExistingVar = false;
        boolean result;



        leftHandSide.execute();
        switch (leftHandSide.type())
        {
            //if x == 10, if x > 10, x == true,
            case 0:
            case 3:
            case 6:
                //number
                isNumber = true;
                break;
            case 1:
            case 2:
            case 4:
            case 5:
                //bool
                break;
            case 7:
                //strings, dont think we will let these be valid
                break;


        }

        if(isNumber)
        {
            int lhs = leftHandSide.resultInt();
            rightHandSide.execute();
            int rhs = rightHandSide.resultInt();

            switch (operator)
            {
                case "==":
                    if(lhs == rhs)
                    {
                        result = true;
                    }
                    else
                    {
                        result = false;
                    }
                    break;
                case "!=":
                    if(lhs != rhs)
                    {
                        result = true;
                    }
                    else
                    {
                        result = false;
                    }
                    break;
                case "<":
                    if(lhs < rhs)
                    {
                        result = true;
                    }
                    else
                    {
                        result = false;
                    }
                    break;
                case ">":
                    if(lhs > rhs)
                    {
                        result = true;
                    }
                    else
                    {
                        result = false;
                    }
                    break;
                case "<=":
                    if(lhs <= rhs)
                    {
                        result = true;
                    }
                    else
                    {
                        result = false;
                    }
                    break;
                case ">=":
                    if(lhs >= rhs)
                    {
                        result = true;
                    }
                    else
                    {
                        result = false;
                    }

                    break;
                default:
                    result = false;  //error
            }

            if(result)
            {
                //do the thing
                for(int n = 0; n < codeBody.size(); n++)
                {
                    Statement cur = codeBody.get(n);
                    cur.execute();
                }
            }
        }
        else //is bool
        {
            String lhs = leftHandSide.resultBool();
            rightHandSide.execute();
            String rhs = rightHandSide.resultBool();

            switch (operator)
            {
                case "==":
                    if(lhs.equals(rhs))
                    {
                        result = true;
                    }
                    else
                    {
                        result = false;
                    }
                    break;
                case "!=":
                    if(!lhs.equals(rhs))
                    {
                        result = true;
                    }
                    else
                    {
                        result = false;
                    }
                    break;
                default:
                    result = false; //error
                    break;

            }

            if(result)
            {
                //do if
                for(int n = 0; n < codeBody.size(); n++)
                {
                    Statement cur = codeBody.get(n);
                    cur.execute();
                }
            }
        }

    }

}

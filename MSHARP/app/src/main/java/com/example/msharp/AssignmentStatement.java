package com.example.msharp;

import java.io.File;
import java.util.Map;
// i think im happy, provided expression works as intended.
public class AssignmentStatement extends Statement
{
    public String variable;
    public Expression expression;
    public Map<String,Integer> Numbers;
    public Map<String, String> Strings;
    public Map<String, String> Bools;

    public AssignmentStatement(String variable, Expression expression, Map<String,Integer> Numbers, Map<String, String> Strings, Map<String, String> Bools)
    {
        this.variable = variable;
        this.expression = expression;
        this.Strings = Strings;
        this.Bools = Bools;
        this.Numbers = Numbers;
    }



    @Override
    public void execute() throws Exception {
        Functions fun = new Functions();
        //boolean replacingVar = fun.isExistingVariable(variable, Numbers, Strings, Bools);
        /*
        type 0 = addExp
        type 1 = andExp
        type 2 = eqlExp
        type 3 = multExp
        type 4 = relExp
        type 5 = boolFact
        type 6 = intFact
        type 7 = stringFact
        type 8 = varFact
        */
        expression.execute();

        switch (expression.type())
        {
            case 0:
            case 3:
            case 6:
                if(Numbers.containsKey(variable))
                {
                    Numbers.remove(variable);
                }
                if(Bools.containsKey(variable))
                {
                    Bools.remove(variable);
                }
                if(Strings.containsKey(variable))
                {
                    Strings.remove(variable);
                }

                Numbers.put(variable, expression.resultInt());
                break;
            case 1:
            case 2:
            case 4:
            case 5:
                if(Numbers.containsKey(variable))
                {
                    Numbers.remove(variable);
                }
                if(Bools.containsKey(variable))
                {
                    Bools.remove(variable);
                }
                if(Strings.containsKey(variable))
                {
                    Strings.remove(variable);
                }
                Bools.put(variable, expression.resultBool());
                break;
            case 7:
                if(Numbers.containsKey(variable))
                {
                    Numbers.remove(variable);
                }
                if(Bools.containsKey(variable))
                {
                    Bools.remove(variable);
                }
                if(Strings.containsKey(variable))
                {
                    Strings.remove(variable);
                }
                Strings.put(variable, expression.resultString());
                break;



        }

    }
}

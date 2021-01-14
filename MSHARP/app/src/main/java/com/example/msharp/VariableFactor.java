package com.example.msharp;
//import javax.xml.bind.annotation.XmlAttachmentRef;
import java.util.Map;

public class VariableFactor extends Factor {

    public String variableName;
    Map<String,Integer> Numbers;
    Map<String, String> Strings;
    Map<String, String> Bools;
    /* Matt: add the maps, then have each result retuen from its respective map */


    public VariableFactor(String variableName, Map<String,Integer> Numbers, Map<String, String> Strings, Map<String, String> Bools)
    {
        this.variableName = variableName;
        this.Bools = Bools;
        this.Numbers = Numbers;
        this.Strings = Strings;
    }

    @Override
    public String rawInput() {
        return variableName;
    }

    @Override
    public void execute()
    {

    }

    @Override
    public int type() {
        if(Numbers.containsKey(variableName))
        {
            return 3;
        }
        String[] split = variableName.split("!",2);
        if(split[0].equals("")) // notted
        {
            if(Bools.containsKey(split[1]))
            {
                return 4;
            }
        }
        if(Bools.containsKey(variableName))
        {
            return 4;
        }

        else
        {
            return 7;
        }

    }

    @Override
    public int resultInt() {

        return Numbers.get(variableName);
    }

    @Override
    public String resultBool() {
        String[] split = variableName.split("!",2);
        if(split[0].equals("")) // notted
        {
            String temp = Bools.get(split[1]);
            if (temp.equals("true"))
            {
                return "false";

            }
            else
            {
                return "true";
            }
        }
        else
        {
            return Bools.get(variableName);
        }

    }

    @Override
    public String resultString() {
        return Strings.get(variableName);
    }
}

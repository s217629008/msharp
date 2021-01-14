package com.example.msharp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Map;
//looks good
public class ConsoleInStatement extends Statement{
    public String newVar;
    Map<String,Integer> Numbers;
    Map<String, String> Strings;
    Map<String, String> Bools;
    public ConsoleInStatement(String newVar, Map<String,Integer> Numbers, Map<String, String> Strings, Map<String, String> Bools)
    {
        this.newVar = newVar;
        this.Bools = Bools;
        this.Numbers = Numbers;
        this.Strings = Strings;
    }


    @Override
    public void execute()
    {
        Functions fun = new Functions();
        String input = null;
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            input = reader.readLine();
        }
        catch(IOException e)
        {

        }
        if(fun.isInteger(input))
        {
            Numbers.put(newVar, Integer.parseInt(input));
        }
        if(fun.isBool(input))
        {
            Bools.put(newVar, input);
        }
        else
        {
            Strings.put(newVar, input);
        }


    }
}

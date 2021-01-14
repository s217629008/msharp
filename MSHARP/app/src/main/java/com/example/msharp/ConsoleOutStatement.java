package com.example.msharp;

import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Console;
import java.util.Map;
//good
public class ConsoleOutStatement extends Statement{

    public Expression expression;
    Map<String,Integer> Numbers;
    Map<String, String> Strings;
    Map<String, String> Bools;
    TextView console;

    public ConsoleOutStatement(Expression expression, Map<String,Integer> Numbers, Map<String, String> Strings, Map<String, String> Bools, TextView console)
    {
        this.expression = expression;
        this.Numbers = Numbers;
        this.Bools = Bools;
        this.Strings = Strings;
        this.console = console;
    }


    @Override
    public void execute() throws Exception {
        expression.execute();
        switch (expression.type())
        {
            case 0:
            case 3:
            case 6:
                //TextView textView = (TextView) findViewById(R.id.textView);
                //System.out.println( expression.resultInt());
                console.append(Integer.toString(expression.resultInt()) + "\r\n");
                break;
            case 1:
            case 2:
            case 4:
            case 5:
                //System.out.println(expression.resultBool());
                console.append(expression.resultBool()+ "\r\n");
                break;
            case 7:
                String[] stringOut = expression.resultString().split("#",1 );
                //System.out.println(stringOut[0]);
                console.append(stringOut[0]+ "\r\n");
                break;



        }

    }
}

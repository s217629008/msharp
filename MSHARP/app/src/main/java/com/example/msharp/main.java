/*
package com.example.msharp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;


public class main {
    /* Create arrays to act as memory for storing strings and ints */
/*
    public static Map<String, String> Strings = new Hashtable();
    public static Map<String, Integer> Numbers = new Hashtable();
    public static Map<String, String> Bools = new Hashtable();

    public int lineCount = 1;
    public static void main(String[] args) {

        /* Initialize scanner */
/*
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(new File("test if.txt"));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Couldn't find the file");
        }

        /*  Initialize parser */
/*
        Parser parser = new Parser();

        /* Initialize new AST for the program */
/*
        Program program = new Program();

        ArrayList<String> programLines = new ArrayList<>();

        while(scanner.hasNextLine())
        {
            programLines.add(scanner.nextLine());
        }

        Boolean needToSkipIf = false;
        Boolean needToSkipWhile = false;

        /* Scanner reads in a line, splits it into tokens. Tokens are then processed by parser. */
/*
        for(int x = 0; x < programLines.size(); x++)
        {

            String line = programLines.get(x);
            String Tokens[] = line.split(" ");

            if(!needToSkipIf && !needToSkipWhile)
            {
                parser.ParseTokens(Tokens, line, scanner, Numbers, Strings, Bools, program, x, programLines);
            }
            if(Tokens[0].equals("ifEnd"))
            {
                needToSkipIf = false;
            }
            if(Tokens[0].equals("whileEnd"))
            {
                needToSkipWhile = false;
            }
            if(Tokens[0].equals("if"))
            {
                needToSkipIf = true;
            }
            if(Tokens[0].equals("while"))
            {
                needToSkipWhile = true;
            }
        }
        program.Run();
        System.out.println("GAME OVER");
    }

}

*/

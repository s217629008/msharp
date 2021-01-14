package com.example.msharp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class activity_console extends AppCompatActivity {

    public static Map<String, String> Strings = new Hashtable();
    public static Map<String, Integer> Numbers = new Hashtable();
    public static Map<String, String> Bools = new Hashtable();
    public int lineCount = 1;
    Context concon = activity_console.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        BottomNavigationView botNav = findViewById(R.id.bottom_navigation);
        botNav.setOnNavigationItemSelectedListener(navListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch(item.getItemId())
                    {
                        case R.id.play:
                            ArrayList<String> loadedLines = getIntent().getStringArrayListExtra("program");

                            TextView textView = (TextView) findViewById(R.id.simpleTextView);
                            //        textView.setText(""); //set text for text view

                            /*  Initialize parser */
                            Parser parser = new Parser(textView, concon);

                            /* Initialize new AST for the program */
                            Program program = new Program();

                            Boolean needToSkipIf = false;
                            Boolean needToSkipWhile = false;

                            for(int x = 0; x < loadedLines.size(); x++)
                            {

                                String line = loadedLines.get(x);
                                String Tokens[] = line.split(" ");

                                if(!needToSkipIf && !needToSkipWhile)
                                {
                                    parser.ParseTokens(Tokens, line, Numbers, Strings, Bools, program, x, loadedLines);
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
                            program.Run(textView);
                            break;
                    }
                    return true;
                }
            };
}
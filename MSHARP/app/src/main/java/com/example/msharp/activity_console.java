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

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.play_code:

                ArrayList<String> loadedLines = getIntent().getStringArrayListExtra("program");

                Functions fun = new Functions();

                if(!fun.checkNoCodeUnchanged(loadedLines))
                {

                    TextView textView = (TextView) findViewById(R.id.simpleTextView);
                    textView.setText("Program still contains unchanged code blocks.");
                    break;
                }

                TextView textView = (TextView) findViewById(R.id.simpleTextView);
                textView.setText("");
                //        textView.setText(""); //set text for text view

                /*  Initialize parser */
                Parser parser = new Parser(textView, concon);

                /* Initialize new AST for the program */
                Program program = new Program();

                Boolean needToSkipIf = false;
                Boolean needToSkipWhile = false;

                int scopeWhile = 0;
                int scopeIf = 0;
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
                        if(scopeIf == 0)
                        {
                            needToSkipIf = false;
                        }
                        scopeIf--;
                    }
                    if(Tokens[0].equals("whileEnd"))
                    {
                        if(scopeWhile == 0)
                        {
                            needToSkipWhile = false;
                        }

                        scopeWhile--;

                    }
                    if(Tokens[0].equals("if"))
                    {
                        scopeIf++;
                        needToSkipIf = true;
                    }
                    if(Tokens[0].equals("while"))
                    {
                        scopeWhile++;
                        needToSkipWhile = true;
                    }
                }
                program.Run(textView);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_console, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Console");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_backarror);



        BottomNavigationView botNav = findViewById(R.id.bottom_navigation);
        botNav.setOnNavigationItemSelectedListener(navListener);
        botNav.getMenu().getItem(0).setChecked(false);
        botNav.getMenu().getItem(1).setChecked(true);

        ArrayList<String> FileNames = new ArrayList<>();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch(item.getItemId())
                    {

                        case R.id.code:

                            ArrayList<String> programName = getIntent().getStringArrayListExtra("programName");

                            Bundle b = new Bundle();
                            b.putStringArrayList("programName", programName);

                            Intent startIntent = new Intent(getApplicationContext(), activity_editor.class);
                            startIntent.putExtras(b);
                            startActivity(startIntent);
                            break;
                    }
                    return true;
                }
            };
}
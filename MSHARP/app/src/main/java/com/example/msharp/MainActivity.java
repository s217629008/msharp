package com.example.msharp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.FileInputStream;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView MainRecyclerView;
    private MyAdapter MainAdapter;
    private RecyclerView.LayoutManager MainLayoutManager;
    private static final String FILE_NAME = "test.txt";
    public ArrayList<SavedItem> File_names = new ArrayList<>();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        ArrayList<String> FileNames = new ArrayList<>();
        //load existing list of file names;
        FileInputStream fis = null;
        try {
            fis = openFileInput("program_names.ms");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                FileNames.add(text);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        for(int x = 0; x < FileNames.size(); x++)
        {
            SavedItem temp = new SavedItem(FileNames.get(x));

            File_names.add(temp);
        }

        //load(); //load saved files names

        buildRecyclerView();





        FloatingActionButton editorActivityBtn = (FloatingActionButton)findViewById(R.id.newProjectFAB);
        editorActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] programTitle = new String[1];
                FileInputStream fis = null;
                final boolean[] makeNewProgram = {true};
                try {
  //                  fis = openFileInput(FILE_NAME);
  //                  InputStreamReader isr = new InputStreamReader(fis);
  //                  BufferedReader br = new BufferedReader(isr);
  //                  StringBuilder sb = new StringBuilder();
  //                  String text;


                    final Handler[] handler = {new Handler() {
                        @Override
                        public void handleMessage(Message mesg) {
                            throw new RuntimeException();
                        }
                    }};

                    AlertDialog.Builder alert = new AlertDialog.Builder(context);

                    alert.setTitle("Please enter program title:");


                    // Set an EditText view to get user input
                    final EditText input = new EditText(context);
                    alert.setView(input);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String value = input.getText().toString();

                            programTitle[0] = value;

                            handler[0].sendMessage(handler[0].obtainMessage());

                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                            handler[0].sendMessage(handler[0].obtainMessage());
                            makeNewProgram[0] = false;
                        }
                    });

                    alert.show();

                    try{
                        Looper.loop();
                    }
                    catch (RuntimeException e) {}

                    if(makeNewProgram[0])
                    {
                        ArrayList<String> programName = new ArrayList<String>();

                        //while((text = br.readLine()) != null)
                        //{
                        //   loadedLines.add(text);
                        //}
                        programName.add(programTitle[0]);
                        Bundle b = new Bundle();
                        b.putStringArrayList("programName", programName);

                        Intent startIntent = new Intent(getApplicationContext(), activity_editor.class);
                        startIntent.putExtras(b);
                        startActivity(startIntent);
                    }


     //           } catch (FileNotFoundException e) {
     //               e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }




            }
        });
    }

    public void buildRecyclerView() {
        MainRecyclerView = findViewById(R.id.recyclerView);
        //MainRecyclerView.setHasFixedSize(true);
        MainLayoutManager = new LinearLayoutManager(this);
        MainAdapter = new MyAdapter(File_names);
        MainRecyclerView.setLayoutManager(MainLayoutManager);
        MainRecyclerView.setAdapter(MainAdapter);

        MainAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                ArrayList<String> programName = new ArrayList<String>();


                //while((text = br.readLine()) != null)
                //{
                //   loadedLines.add(text);
                //}
                programName.add(File_names.get(position).getText());
                Bundle b = new Bundle();
                b.putStringArrayList("programName", programName);

                Intent startIntent = new Intent(getApplicationContext(), activity_editor.class);
                startIntent.putExtras(b);
                startActivity(startIntent);
            }

            @Override
            public void onDeleteClicked(int position) {
                //String fileToDelete = File_names.get(position).getText();
                ArrayList<String> FileNames = new ArrayList<>();
                String lineSeparator = System.getProperty("line.separator");
                //load existing list of file names;
                FileOutputStream fos = null;
                FileInputStream fis = null;

                try {
                    fis = openFileInput("program_names.ms");
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    String text;
                    while ((text = br.readLine()) != null) {
                        FileNames.add(text);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


                FileNames.remove(position);

                //save new list, now with new file name, with it at top of the list
                try {
                    fos = openFileOutput("program_names.ms", MODE_PRIVATE);
                    for(int x = 0; x < FileNames.size(); x++)
                    {
                        String temp = FileNames.get(x);

                            fos.write(temp.getBytes());
                            fos.write(lineSeparator.getBytes());


                    }
                    //Toast.makeText(context, "Saved to " + getFilesDir() + "/" + myProgramName, Toast.LENGTH_LONG).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    if (fos != null)
                    {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                File_names.remove(position);
                MainAdapter.notifyItemRemoved(position);
            }
        });

    }



    public void load()
    {
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String text;
            while ((text = br.readLine()) != null) {
                SavedItem temp = new SavedItem(text);
                File_names.add(temp);
            }

        } catch (FileNotFoundException e) {

            //e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
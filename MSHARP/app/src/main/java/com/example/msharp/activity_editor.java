package com.example.msharp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;


public class activity_editor extends AppCompatActivity {
    private DrawerLayout drawerlayout;
    private NavigationView navigationview;
    private TreeNode root;
    private Context context = this;
    public int nodeCount = 1;
    private static final String FILE_NAME = "test.txt";
    private String myProgramName;

    public static Map<String, String> TempStrings = new Hashtable();
    public static Map<String, Integer> TempNumbers = new Hashtable();
    public static Map<String, String> TempBools = new Hashtable();









    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Save");
        alert.setMessage("Would you like to save your work?");


        // Set an EditText view to get user input


        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {





                String lineSeparator = System.getProperty("line.separator");
                //save the program
                ArrayList<String> saveLines = new ArrayList<String>();
                getPrintLines(saveLines, root);
                FileOutputStream fos = null;

                try {
                    fos = openFileOutput(myProgramName, MODE_PRIVATE);
                    for(int x = 0; x < saveLines.size(); x++)
                    {
                        fos.write(saveLines.get(x).getBytes());
                        fos.write(lineSeparator.getBytes());
                    }
                    Toast.makeText(context, "Saved to " + getFilesDir() + "/" + myProgramName, Toast.LENGTH_LONG).show();

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
                //save the file name

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

                //save new list, now with new file name, with it at top of the list
                try {
                    fos = openFileOutput("program_names.ms", MODE_PRIVATE);
                    fos.write(myProgramName.getBytes());
                    fos.write(lineSeparator.getBytes());
                    for(int x = 0; x < FileNames.size(); x++)
                    {
                        String temp = FileNames.get(x);
                        if(!temp.equals(myProgramName))
                        {
                            fos.write(temp.getBytes());
                            fos.write(lineSeparator.getBytes());
                        }

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


                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);

                // Do something with value!
            }
        });



        alert.show();


    }

    public void getPrintLines(ArrayList<String> output, TreeNode root)
    {
        ArrayList<TreeNode> childs = root.getChildren();

        if(childs.size() != 0)
        {
            if(root.ID != 0)
            {
                output.add(root.data + ";" + Integer.toString(root.type) + ";" + Integer.toString(root.ID) + ";" + Integer.toString(root.getParent().ID));
            }
            for(int x = 0; x < childs.size(); x++)
            {
                getPrintLines(output, childs.get(x));
            }
        }
        else
        {

                output.add(root.data + ";" + Integer.toString(root.type) + ";" + Integer.toString(root.ID) + ";" + Integer.toString(root.getParent().ID));

        }
    }


    




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ArrayList<String> load = getIntent().getStringArrayListExtra("loadedLines");
        setContentView(R.layout.activity_editor);



        drawerlayout = findViewById(R.id.drawer_layout);
        navigationview = findViewById(R.id.navigationView);


        DraggableTreeView draggableTreeView = (DraggableTreeView) findViewById(R.id.dtv);
        root = new TreeNode(this);
        Context context = this;
        //draggableTreeView.setKeyboard(keyboard);
        BottomNavigationView botNav = findViewById(R.id.bottom_navigation);
        botNav.setOnNavigationItemSelectedListener(navListener);

        try {
            ArrayList<String> programName = getIntent().getStringArrayListExtra("programName");
            //Toast.makeText(context, loadedLines.get(0), Toast.LENGTH_LONG).show();
            myProgramName = programName.get(0);

            ArrayList<String> loadedCode = new ArrayList<>();
            //load existing list of file names;
            FileInputStream fis = null;

            try {
                Functions fun = new Functions();
                fis = openFileInput(myProgramName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String text;
                while ((text = br.readLine()) != null) {
                    loadedCode.add(text);
                }
                for(int x = 0; x < loadedCode.size(); x++)
                {
                    //output.add(root.data + ";" + Integer.toString(root.type) + ";" + Integer.toString(root.ID) + ";" + Integer.toString(root.getParent().ID));

                    String[] temp = loadedCode.get(x).split(";");
                    if(Integer.parseInt(temp[3])==0) //root is parent
                    {
                        root.addChild(new TreeNode(temp[0], Integer.parseInt(temp[1]), Integer.parseInt(temp[2])));
                    }
                    else
                    {


                        root.addChildToID(Integer.parseInt(temp[3]),new TreeNode(temp[0], Integer.parseInt(temp[1]), Integer.parseInt(temp[2])));

                    }

                    nodeCount++;
                }

            } catch (FileNotFoundException e) {

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
        catch (Exception e)
        {

        }
        /*
        TreeNode item = new TreeNode("Item 1");
        TreeNode item2 = new TreeNode("Item 2");

        TreeNode subitem = new TreeNode("Sub Item 2");
        subitem.addChild(new TreeNode("Sub Sub Item 1"));
        item.addChild(subitem);
        item.addChild(new TreeNode("Sub Item 1"));
        root.addChild(new TreeNode("Item 3"));
        root.addChild(new TreeNode("Item 9"));
        root.addChild(new TreeNode("Item 10"));
        root.addChild(new TreeNode("Item 11"));
        root.addChild(new TreeNode("Item 12"));
        root.addChild(item2);
        root.addChild(item);
        */


        final SimpleTreeViewAdapter adapter = new SimpleTreeViewAdapter(this, root);
        draggableTreeView.setAdapter(adapter);



        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                DraggableTreeView draggableTreeView = (DraggableTreeView) findViewById(R.id.dtv);
                switch (item.getItemId()) {
                    case R.id.stmIf:
                        //item.setChecked(true);
                        root.addChild(new TreeNode("if COND", 1, nodeCount));
                        nodeCount++;
                        //root.addChild(new TreeNode("ifEnd", 1, nodeCount));
                        //nodeCount++;
                        draggableTreeView.setAdapter(adapter);
                        return true;

                    case R.id.stmAss:
                        //item.setChecked(true);
                        root.addChild(new TreeNode("let VAR = EXP", 2, nodeCount));
                        nodeCount++;
                        draggableTreeView.setAdapter(adapter);
                        return true;

                    case R.id.stmWhile:
                        item.setChecked(true);
                        root.addChild(new TreeNode("while COND",3,nodeCount));
                        nodeCount++;
                        //root.addChild(new TreeNode("whileEnd",3,nodeCount));
                        //nodeCount++;
                        draggableTreeView.setAdapter(adapter);
                        return true;


                    case R.id.stmWrite:
                        //item.setChecked(true);
                        root.addChild(new TreeNode("print EXP",5,nodeCount));
                        nodeCount++;
                        draggableTreeView.setAdapter(adapter);
                        return true;

                    case R.id.stmIfEnd:
                        //item.setChecked(true);
                        root.addChild(new TreeNode("ifEnd", 6, nodeCount));
                        nodeCount++;
                        draggableTreeView.setAdapter(adapter);
                        return true;

                    case R.id.stmWhileEnd:
                        //item.setChecked(true);
                        root.addChild(new TreeNode("whileEnd", 7, nodeCount));
                        nodeCount++;
                        draggableTreeView.setAdapter(adapter);
                        return true;

                        /*
                    case R.id.expAnd:
                        item.setChecked(true);
                        root.addChild(new TreeNode("FACTOR and FACTOR"));
                        draggableTreeView.setAdapter(adapter);
                        return true;

                    case R.id.expAdd1:
                        item.setChecked(true);
                        root.addChild(new TreeNode("FACTOR + FACTOR"));
                        draggableTreeView.setAdapter(adapter);
                        return true;

                    case R.id.expAdd2:
                        item.setChecked(true);
                        root.addChild(new TreeNode("FACTOR - FACTOR"));
                        draggableTreeView.setAdapter(adapter);
                        return true;

                    case R.id.expEql1:
                        item.setChecked(true);
                        root.addChild(new TreeNode("FACTOR == FACTOR"));
                        draggableTreeView.setAdapter(adapter);
                        return true;

                    case R.id.expEql2:
                        item.setChecked(true);
                        root.addChild(new TreeNode("FACTOR != FACTOR"));
                        draggableTreeView.setAdapter(adapter);
                        return true;

                    case R.id.expMult1:
                        item.setChecked(true);
                        root.addChild(new TreeNode("FACTOR * FACTOR"));
                        draggableTreeView.setAdapter(adapter);
                        return true;

                    case R.id.expMult2:
                        item.setChecked(true);
                        root.addChild(new TreeNode("FACTOR / FACTOR"));
                        draggableTreeView.setAdapter(adapter);
                        return true;

                    case R.id.expRel1:
                        item.setChecked(true);
                        root.addChild(new TreeNode("FACTOR < FACTOR"));
                        draggableTreeView.setAdapter(adapter);
                        return true;

                    case R.id.expRel2:
                        item.setChecked(true);
                        root.addChild(new TreeNode("FACTOR > FACTOR"));
                        draggableTreeView.setAdapter(adapter);
                        return true;

                    case R.id.expRel3:
                        item.setChecked(true);
                        root.addChild(new TreeNode("FACTOR <= FACTOR"));
                        draggableTreeView.setAdapter(adapter);
                        return true;

                    case R.id.expRel4:
                        item.setChecked(true);
                        root.addChild(new TreeNode("FACTOR >= FACTOR"));
                        draggableTreeView.setAdapter(adapter);
                        return true;

                         */
                }


                return false;
            }

        });


    }

    private void treeNodeData(TreeNode x, ArrayList<String> program)
    {
        if(x.getChildren().size() == 0)
        {
            program.add(x.data.toString());
        }
        else
        {
            if(x.ID != 0)
            {
                program.add(x.data.toString());
            }
            ArrayList<TreeNode> kids = x.getChildren();
            for(int y = 0; y < kids.size(); y++)
            {
                treeNodeData(kids.get(y),program);
            }
        }
    }

    private ArrayList<String> getProgram(TreeNode root)
    {
        ArrayList<String> program = new ArrayList<>();
        treeNodeData(root,program);
        return program;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch(item.getItemId())
                    {
                        case R.id.play:
                            ArrayList<String> program = getProgram(root);
                            Intent startIntent = new Intent(getApplicationContext(), activity_console.class);
                            Bundle b = new Bundle();
                            b.putStringArrayList("program", program);
                            startIntent.putExtras(b);
                            startActivity(startIntent);
                            break;
                    }
                    return true;
                }
            };

}
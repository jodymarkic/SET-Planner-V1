package com.example.jmarkic8674.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * Filename     : ProjectArrayAdapter.java
 * Project      : SETPlanner
 * Author       : Nathan Bray, Jody Markic, Gabriel Paquette
 * Date Created : 2017-02-03
 * Description  : this file hold the Main2Activity class, the second activity of
 *                the MADHatters project
 */

/*
* Class: Main2Activity
* Description: This Class holds the second Activity in the MADHatters project
*              it handles all user interation with the activity which includes
*              navigation to other activites along with creating and deleting
*              projects.
*
*/
public class Main2Activity extends Activity implements View.OnClickListener, DialogInterface.OnClickListener, AdapterView.OnItemLongClickListener{

    private AlertDialog alert;
    private EditText input;
    private DatePicker picker;
    private ListView listView;
    static Container container = new Container();
    private int cID;
    private TextView header;
    private ArrayAdapter<Project> projListAdapter;
    private long whichID;
    private Switch sw;

    private int AddOrDelete;


    /*
    * Method: onCreate()
    * Description: Creates the Main2Activity activity
    * Parameters: Bundle : savedInstanceState
    * Returns: N/A
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //set the View to this Activity
        setContentView(R.layout.activity_main2);
        try {
            //set the Action Bar title to "Projects"
            getActionBar().setTitle(R.string.activity2);
        }catch (NullPointerException e)
        {
            //log if unsuccessful
            Log.e("setTitle", e.getMessage());
        }

        //create a list view with R.id.list
        listView = (ListView) findViewById(R.id.list);
        //create a button with R.id.add_project_button
        Button btnAddProjects = (Button) findViewById(R.id.add_project_button);
        //set a listener on the local
        btnAddProjects.setOnClickListener(this);
        //setup our AlertDialog for this page.
        SetAlertDialog();

        //get container and course ID from main activity
        Intent intent = getIntent();
        cID = (int) intent.getExtras().getSerializable("cID");

        //set the title
        // Because indexes are not accurate after deletion, we loop through each course until
        // we find one with the proper course id we want, and set the title
        for (Course course:container.getCourses()) {
            if (cID == course.getCourseID())
            {
                getActionBar().setTitle(course.toString());
                break;
            }
        }
    }

    /*
    * Method: creatList()
    * Description: this method sets up our project listAdapter
    * Parameters: N/A
    * Returns: N/A
    */
    public void createList()
    {
        //create an ArrayList of Projects
        ArrayList<Project> thisProjectList = new ArrayList<>();
        //setup our adapter
        projListAdapter = new ProjectArrayAdapter(this, 0, thisProjectList, container, false);

        //check if any prof for the course selected
        if(container.getProj().size() != 0){
            // Else, create a temporary List of projects

            // We will check each projects' courseId to see if it matches the selected course
            for (Project proj: container.getProj())
            {
                // If it does add it to the temporary list
                if (proj.getCourseID() == cID)
                {
                    projListAdapter.add(proj);
                }
            }
        }

        //make header if no courses in proj list or no proj for the selected course
        if(projListAdapter.isEmpty()){
            header = new TextView(this);
            header.setText(R.string.ProjectPrompt);
            header.setTextSize(20);
            listView.addHeaderView(header, null, false);
        }

        //set our adapter to the listView
        listView.setAdapter( projListAdapter );
        //setup a long click lister to the listView
        listView.setOnItemLongClickListener(this);
    }




    /*
    * Method: SetDeleteDialog()
    * Description: Setup a Dialog when spawned on a longClick event
    * Parameters: N/A
    * Returns: N/A
    */
    public void SetDeleteDialog(){

        //flag set for OnClick event to determine which dialog to use
        AddOrDelete = 1;

        //create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //set the title of the Dialog
        builder.setTitle("Delete a Course");

        //set OK and Cancel Buttons of the Dialog
        builder.setPositiveButton("OK", this);
        builder.setNegativeButton("Cancel", this);

        // setup a LinearLayout to be used in the Dialog so we can have multiple widgets within
        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        parms.weight = 0.0f;

        // Set up Widgets for the Dialog
        TextView tv = new TextView(this);
        tv.setText(R.string.DelProject);

        sw = new Switch(this);
        sw.setTextOn("start");
        sw.setTextOff("close");
        sw.setWidth(200);

        //set our widgets to the layout
        layout.setLayoutParams(parms);
        layout.addView(tv);
        layout.addView(sw);

        //set our layout to the Dialog
        builder.setView(layout);

        //create the dialog
        alert = builder.create();
    }

    /*
    * Method: SetAlertDialog()
    * Description: Setup a Dialog when spawned on a button Click event
    * Parameters: N/A
    * Returns: N/A
    */
    public void SetAlertDialog(){

        //flag set for OnClick event to determine which dialog to use
        AddOrDelete = 0;

        //create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //set the title of the Dialog
        builder.setTitle("Project Setup");

        //set OK and Cancel Buttons of the Dialog
        builder.setPositiveButton("OK", this);
        builder.setNegativeButton("Cancel", this);

        // setup a LinearLayout to be used in the Dialog so we can have multiple widgets within
        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(parms);


        // Set up Widgets for the Dialog
        input = new EditText(this);
        input.setHint("Enter a Project Name");
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        TextView textViewLabel = new TextView(this);
        textViewLabel.setText("\n  Select a Due Date :");
        textViewLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        //set our DatePicker
        picker = new DatePicker(this);

        //set our widgets to the layout
        layout.addView(input);
        layout.addView(textViewLabel);
        layout.addView(picker);

        //set our layout to the Dialog
        builder.setView(layout);

        //create the Dialog
        alert = builder.create();
    }

    /*
    * Method: onClick()
    * Description: overloaded onClick event handle for a button click
    * Parameters: View v
    * Returns: N/a
    */
    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            //show the Add Dialog
            case R.id.add_project_button:
                alert.show();
                break;
        }
    }

    /*
    * Method: onClick()
    * Description: overloaded onClick event handle for a Dialog button click
    * Parameters: DialogInterface : dialog
    *             int             : which
    * Returns: N/A
    */
   @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which)
        {
            //if OK button in Dialog has been clicked
            case BUTTON_POSITIVE:
                //if this is the Add Dialog
                if(AddOrDelete == 0)
                {
                    //add the new project to the Adapter
                    Project newProj = new Project(input.getText().toString(), Project.getDueDateFromDatePicker(picker),cID, container.projLength());
                    container.addProj(newProj);
                    projListAdapter.add(newProj);
                    listView.removeHeaderView(header);
                    input.setText("");
                }
                //if this is the Delete Dialog
                else
                {
                    //check that user has confirmed they want to delete the project
                    if(sw.isChecked()) {
                        //find and delete the appropriate project
                        int pID = container.getProj().get((int)whichID).getProjectID();
                        int length = container.getProj().size();
                        if (length != 0) {
                            for (int i = 0; i < length; i++) {
                                if (pID == (container.getProj().get(i).getProjectID())) {
                                    container.getProj().remove(i);
                                    break;
                                }
                            }
                            //refresh the adapter to show the changes
                            createList();
                        }
                    }
                }
                break;
            //if Cancel button in Dialog has been clicked
            case BUTTON_NEGATIVE:
                //close the Dialog
                if(AddOrDelete == 0)
                {
                    input.setText("");
                }
                alert.cancel();
                break;
        }
    }

    /*
    * Method: onItemLongClick
    * Description: eventHandle for a longClick event on the ListView
    * Parameters: AdapterView<?> parent
    *             View view
    *             int position
    *             long id
    * Returns: true : boolean
    */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        //course ID
        whichID = id;
        //setup the delete dialog
        SetDeleteDialog();
        //show the delete dialog
        alert.show();

        //log the event
        Log.v("long clicked","pos: " + position);
        return true;
    }

    /*
    * Method: onResume()
    * Description: this method handles the onResume event
    *              and loads the container from file
    * Parameters: N/A
    * Returns: N/A
    */
    @Override
    public void onResume(){
        super.onResume();
        //load container contents from a file
        container = container.load(this);
        //reset adapters and lists
        createList();
        //log the event
        Log.d("FileIO", "onResume Activity2");
    }

    /*
    * Method: onPause()
    * Description: this method handles the onPause event
    *              and saves the container to a file
    * Parameters: N/A
    * Returns: N/A
    */
    @Override
    public void onPause(){
        super.onPause();
        //save container contents to a file
        container.save(this, container);
        //log the event
        Log.d("FileIO", "onPause Activity3");
    }

}


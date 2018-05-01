package com.example.jmarkic8674.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;
import static java.util.Collections.sort;

/**
 * Filename     : ProjectArrayAdapter.java
 * Project      : SETPlanner
 * Author       : Nathan Bray, Jody Markic, Gabriel Paquette
 * Date Created : 2017-02-03
 * Description  : this file hold the Main2Activity class, the second activity of
 *                the MADHatters project
 */

/*
* Class: Main3Activity
* Description: This Class holds the third Activity in the MADHatters project
*              it handles all user interation with the activity which includes
*              navigation to other activites along with showing all projects in
 *             descending order from dates along with updating a projects status.
*
*/
public class Main3Activity extends Activity implements DialogInterface.OnClickListener, AdapterView.OnItemLongClickListener {
    static Container container = new Container();

    private ListView listView;
    private long whichID;
    private CheckBox checkBox;
    private AlertDialog alert;

    /*
    * Method: onCreate()
    * Description: Creates the Main3Activity activity
    * Parameters: Bundle : savedInstanceState
    * Returns: N/A
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set View to activity_main3
        setContentView(R.layout.activity_main3);
        //set activity title
        getActionBar().setTitle(R.string.activity3);

        //set a listView to R.id.List
        listView = (ListView) findViewById(R.id.list);
        //set an long click event to the listView
        listView.setOnItemLongClickListener(this);

    }

    /*
    * Method: creatList()
    * Description: this method sets up our project listAdapter
    * Parameters: N/A
    * Returns: N/A
    */
    public void createList()
    {
        //sort the container by DueDate
        sort(container.getProj(), new Project());

        //create an ArrayList of Projects
        ArrayList<Project> thisProjectList = new ArrayList<>();
        //setup our adapter
        ArrayAdapter<Project> projListAdapter = new ProjectArrayAdapter(this, 0, thisProjectList, container, true);
        //check if any prof for the course selected
        if(container.getProj().size() != 0){
            // We will check each projects' courseId to see if it matches the selected course
            for (Project proj: container.getProj())
            {
                    projListAdapter.add(proj);
            }
        }
        else{ //if no projects exits
            TextView header = new TextView(this);
            header.setText(R.string.NoProjectsDue);
            header.setTextSize(20);
            listView.addHeaderView(header, null, false);
        }
        //set the project list Adapter to the List View
        listView.setAdapter(projListAdapter);
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
        createList();
        //log the event
        Log.d("FileIO", "onResume Activity3");
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
        //save the container contents to a file
        container.save(this, container);
        //log the event
        Log.d("FileIO", "onPause Activity3");
    }

    /*
    * Method: SetAlertDialog()
    * Description: Setup a Dialog when spawned on a button Click event
    * Parameters: N/A
    * Returns: N/A
    */
    public void SetAlertDialog(boolean state){

        //create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //set the title of the Dialog
        builder.setTitle("Project Status");

        //set buttons
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
        tv.setText("            Completed: ");

        checkBox = new CheckBox(this);
        checkBox.setWidth(200);

        checkBox.setChecked(state);

        //set our widgets to the layout
        layout.setLayoutParams(parms);
        layout.addView(tv);
        layout.addView(checkBox);

        //set our layout to the Dialog
        builder.setView(layout);
        //create the Dialog
        alert = builder.create();
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

        boolean state = container.getProj().get((int)id).getStatus();
        //setup the delete dialog
        SetAlertDialog(state);
        //show the delete dialog
        alert.show();

        //log the event
        Log.v("long clicked","pos: " + position);

        return true;
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
                //find the project that was selected
                int pID = container.getProj().get((int)whichID).getProjectID();
                Project finishedProj = new Project();
                for (Project tempProj:container.getProj()) {
                    if (pID == tempProj.getProjectID())
                    {
                        finishedProj = tempProj;
                        Log.i("Project-Change Status", "Found Project");
                        break;
                    }
                }
                //if user confirmed they finished the project
                if(checkBox.isChecked())
                {
                    //change the projects status
                    finishedProj.setFinished(true);
                }
                else //if user unchecks a finished project
                {
                    //change the projects status
                    finishedProj.setFinished(false);
                }
                createList();
                break;
            //if Cancel button is selected
            case BUTTON_NEGATIVE:
                //close Dialog
                alert.cancel();
                break;
        }
    }
}

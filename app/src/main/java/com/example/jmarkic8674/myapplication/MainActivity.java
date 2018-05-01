package com.example.jmarkic8674.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * Filename     : ProjectArrayAdapter.java
 * Project      : SETPlanner
 * Author       : Nathan Bray, Jody Markic, Gabriel Paquette
 * Date Created : 2017-02-03
 * Description  : this file hold the MainActivity class, the first activity of
 *                the MADHatters project
 */

/*
* Class: MainActivity
* Description: This Class holds the first Activity in the MADHatters project
*              it handles all user interation with the activity which includes
*              navigation to other activites along with adding and deleting courses.
*
*/
public class MainActivity extends Activity
        implements View.OnClickListener,
        View.OnKeyListener,
        DialogInterface.OnClickListener,
        AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener
{

    static Container container = new Container();

    private ArrayAdapter<Course> courseListAdapter ;
    private EditText mEditText;
    private ListView listView;

    private AlertDialog alert;
    private Switch sw;
    private long whichID;


    /*
    * Method: onCreate()
    * Description: Creates the MainActivity activity
    * Parameters: Bundle : savedInstanceState
    * Returns: N/A
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set our activity to display this
        setContentView(R.layout.activity_main);

        //change action bar to courses
        getActionBar().setTitle(R.string.activity1);

        //set btnAddCourses to the R.id.add_course_button
        Button btnAddCourses = (Button) findViewById(R.id.add_course_button);
        //set an on click listener to the button
        btnAddCourses.setOnClickListener(this);

        //set the listView to R.id.list
        listView = (ListView) findViewById(R.id.list);
        //set the edit text tot the R.id.editText
        mEditText = (EditText) findViewById(R.id.editText);

        //set an on key listern to the edit text
        mEditText.setOnKeyListener(this);
        //set a on item click listenrer to the listview
        listView.setOnItemClickListener(this);
        //set a long click listener to the listview
        listView.setOnItemLongClickListener(this);
    }

    /*
    * Method: createList()
    * Description: This method sets the addapter to the listView
    * Parameters: N/A
    * Returns: N/A
    */
    public void createList()
    {
        //create aa adapter for the courseList
        courseListAdapter = new ArrayAdapter<>(this, R.layout.simplerow, container.getCourses());
        //set the adapter to the listView
        listView.setAdapter( courseListAdapter );
    }

    /*
    * Method: onCreateOptionsMenu()
    * Description: Event handle that inflates the activities menu
    * Parameters: Menu menu
    * Returns: true : boolean
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //inflate out main activity menu
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    /*
    * Method: onOptionsItemSelected()
    * Description: Event handle for an onclick event for the Menu
    * Parameters: MenuItem item
    * Returns: true : boolean
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //our event handle for when we select a menu item
        switch(item.getItemId()){
            case R.id.menu_activity3:
                Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
                intent.putExtra("Container", container);
                startActivityForResult(intent, 1);
                return true;
            default:
                return super.onOptionsItemSelected((item));
        }
    }

    /*
    * Method: onClick
    * Description: Event handle for an onclick event for a button
    * Parameters: View v
    * Returns: N/A
    */
    @Override
    public void onClick(View v) {
        //our event handle when we click anything in our view
        //currently our button
        switch(v.getId())
        {
            case R.id.add_course_button:
                //add a course
                addCourse();
                break;
        }
    }

    /*
    * Method: onItemClick()
    * Description: Event handle for the onclick event for the ListView
    * Parameters: AdapterView<?> parent
    *             View View
    *             int position
    *             long id
    * Returns: N/A
    */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){

        int cID = container.getCourses().get((int)id).getCourseID();
        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
        intent.putExtra("cID", cID);
        startActivity(intent);
    }

    /*
    * Method: createNewCourse()
    * Description: Add a course to the Adapter
    * Parameters: String cName
    * Returns: N/A
    */
    private void createNewCourse(String cName) {
        //create a course object
        Course course = new Course(cName, container.getCourses().size());
        //add course to the adapter
        courseListAdapter.add(course);
    }

    /*
    * Method: onKey()
    * Description: Event handle for a keydown on the enter key
    * Parameters: View v
    *             int keyCode
    *             KeyEvent event
    * Returns: false : boolean
    */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
        {
            addCourse();
        }
        return false;
    }

    /*
    * Method: addCourse()
    * Description: Adds a course to the Adapter through calling
    *              create new course
    * Parameters: N/A
    * Returns: N/A
    */
    public void addCourse()
    {
        //check that there's something in the editText
        if(!mEditText.getText().toString().equals(""))
        {
            //add the course
            createNewCourse(mEditText.getText().toString());
            //reset the Edit text
            mEditText.setText("");
        }
    }

    /*
    * Method: onItemLongClick()
    * Description: Event handle for long clicks on the List view
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
        //set the Dialog
        SetAlertDialog();
        //show the Dialog
        alert.show();
        //log the event
        Log.v("long clicked","pos: " + position);
        return true;
    }

    /*
    * Method: SetAlertDialog()
    * Description: Setup a Dialog when spawned on a button Click event
    * Parameters: N/A
    * Returns: N/A
    */
    public void SetAlertDialog() {
        //create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //set the title of the Dialog
        builder.setTitle("Delete a Course");

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
        tv.setText(R.string.DelCourse);

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
        //create the Dialog
        alert = builder.create();
    }

    /*
    * Method: onResume()
    * Description: event handle for on resume of activity
    * Parameters: N/A
    * Returns: N/A
    */
    @Override
    public void onResume(){
        super.onResume();
        //load the container from file
        container = container.load(this);
        createList();
        //log the event
        Log.d("FileIO", "onResume Activity");
    }

    /*
    * Method: onPause()
    * Description: event handle for on pause of the Activity
    * Parameters: N/A
    * Returns: N/A
    */
    @Override
    public void onPause() {
        super.onPause();
        //save the container to a file
        container.save(this, container);
        //log the event
        Log.d("FileIO", "onPause Activity");
    }

    /*
    * Method: onClick()
    * Description: Event handle for on click events in the dialog
    * Parameters: DialogInterface dialog
    *             int which
    * Returns: N/A
    */
    @Override
    public void onClick(DialogInterface dialog, int which) {

        switch (which)
        {
            //if OK button in Dialog has been clicked
            case BUTTON_POSITIVE:
                //if user confirmed they want to delete the course
                if(sw.isChecked()) {
                    //find the project that was selected
                    int cID = container.getCourses().get((int)whichID).getCourseID();
                    int length = container.getProj().size();
                    //check if any project exist
                    if(length != 0)
                    {
                        //find the project and delete it
                        for(int i = (length-1); i >= 0; i--)
                        {
                            if(cID == (container.getProj().get(i).getCourseID()))
                            {
                                container.getProj().remove(i);
                            }
                        }
                    }
                    //check if any courses exist
                    length = container.getCourses().size();
                    if(length != 0)
                        {//find the course and delete it
                        for(int i = 0; i < length; i++)
                        {
                            if(cID == (container.getCourses().get(i)).getCourseID())
                            {
                                container.getCourses().remove(i);
                                break;
                            }
                        }
                    }
                    //update Adapter
                    createList();
                }
                break;
            //if Cancel button is selected
            case BUTTON_NEGATIVE:
                //close dialog
                break;
        }
    }
}

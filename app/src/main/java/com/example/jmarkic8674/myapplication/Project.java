package com.example.jmarkic8674.myapplication;

import android.widget.DatePicker;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * Filename     : ProjectArrayAdapter.java
 * Project      : SETPlanner
 * Author       : Nathan Bray, Jody Markic, Gabriel Paquette
 * Date Created : 2017-02-03
 * Description  :This class is to provide the basic accessors, mutators, and  creation of a project object
 */

/*
* Class: Project
* Description: This class holds all significant data for a project object
*
*/
public class Project implements Serializable, Comparator<Project>
{
    private int projectID;
    private int courseID;
    private String projectName;
    private Date dueDate;
    private Boolean status;

    public Project(){
        super();
    }

    /*
    * Method: Project()
    * Description: overloaded construct for the Project Class
    * Parameters: String pName
    *             Date dDate
    *             int cID
    *             int projectSize
    * Returns: N/A
    */
    public Project(String pName, Date dDate, int cID, int projectSize)
    {
        super();
        this.projectID = projectSize+1;
        this.courseID = cID;
        this.projectName = pName;
        this.dueDate = dDate;
        this.status = false;
    }

    /*
    * Method: toString()
    * Description: Overloaded toString Method
    * Parameters: N/A
    * Returns: projectInfo : string
    */
    @Override
    public String toString()
    {
        //format date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
        //concatinate the formated date to a string
        String projectInfo = projectName  + "\n" + sdf.format(dueDate);
        //output whether a project is completed or not
        if(status){
            projectInfo += "                                      Complete";
        }
        else{
            projectInfo += "                                    Incomplete";
        }
        return projectInfo;
    }

    /*
    * Method: getCourseID()
    * Description: Accessor for courseID
    * Parameters: N/A
    * Returns: courseID : int
    */
    int getCourseID()
    {
        return courseID;
    }

    /*
    * Method: getStatus()
    * Description: Accessor for status
    * Parameters: N/A
    * Returns: status : boolean
    */
    Boolean getStatus()
    {
        return status;
    }

    /*
    * Method: getProjectName()
    * Description: Accessor for projectName
    * Parameters: N/A
    * Returns: projectName : string
    */
    String getProjectName(){
        return projectName;
    }

    /*
    * Method: setFinished()
    * Description: Mutator for status
    * Parameters: Boolean finished
    * Returns: N/A
    */
    void setFinished(Boolean finished)
    {
        this.status = finished;
    }

    /*
    * Method: getDueDate()
    * Description: Accessor for dueDate
    * Parameters: N/A
    * Returns: dueDate : Date
    */
    Date getDueDate()
    {
        return dueDate;
    }

    /*
    * Method: getProjectID()
    * Description: Accessor for projectID
    * Parameters: N/A
    * Returns: projectID : int
    */
    int getProjectID() {
        return projectID;
    }

    /*
    * Method: getDueDateFromDatePicker()
    * Description: this method retrieves the dueDate the user has selected
    *              from a datePicker object
    * Parameters: DatePicker datePicker
    * Returns: calender.getTime() : Date
    */
    static Date getDueDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    /*
    * Method: compare()
    * Description: Overload of the the compare() method
    *              used for sorting the array list by Date.
    * Parameters: Project o1
    *             Project o2
    * Returns: o1.getDueDate().compareTo(o2.getDueDate()) : int
    */
    @Override
    public int compare(Project o1, Project o2) {

        return o1.getDueDate().compareTo(o2.getDueDate());
    }
}

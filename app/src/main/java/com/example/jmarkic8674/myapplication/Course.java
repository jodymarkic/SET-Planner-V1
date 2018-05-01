package com.example.jmarkic8674.myapplication;

import java.io.Serializable;

/**
 * Filename     : ProjectArrayAdapter.java
 * Project      : SETPlanner
 * Author       : Nathan Bray, Jody Markic, Gabriel Paquette
 * Date Created : 2017-02-03
 * Description  : This class is to provide the basic accessors and creation of a Course object
 */

/*
* Class: Course
* Description: This class hold all significant data associated with a
*              course object, it has method that allow you to access and
*              mutate that data
*
*/
class Course implements Serializable {

    private String courseName;
    private int courseID;


    /*
    * Method: Course()
    * Description:A Constructor for the Course Class
    * Parameters: String : cName
    *             int    : coursSize
    * Returns: N/A
    */
    Course(String cName, int courseSize){
        super();
        this.courseName = cName;
        this.courseID = courseSize+1;
    }

    /*
    * Method: getCourseID()
    * Description: Accessor for courseID
    * Parameters: N/A
    * Returns: this.CourseID : int
    */
    int getCourseID(){
        return this.courseID;
    }

    /*
    * Method: toString()
    * Description: override of toString()
    * Parameters: N/A
    * Returns: courseName : string
    */
    @Override
    public String toString() {
        return courseName;
    }

}

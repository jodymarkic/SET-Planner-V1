package com.example.jmarkic8674.myapplication;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
* Filename     : Container.java
* Project      : SETPlanner
* Author       : Nathan Bray, Jody Markic, Gabriel Paquette
* Date Created : 2017-02-03
* Description  : This Class holds ArrayLists of Course and Project Objects
 *
*/

/*
* Class: Container
* Description: This Class hold method for accessing and
*              updating objects stored in a Container object's
*              ArrayLists. It also preserves those objects by reading
*              and writing a Container contents to a file when called.
*
*/
class Container implements Serializable {
    private String fileName = "containerData";
    private ArrayList<Course> courseList;
    private ArrayList<Project> projList ;

    /*
    * Method: Container()
    * Description: Constructor for the Container Class
    * Parameters: N/A
    * Returns: N?A
    */
    Container () {
        this.courseList = new ArrayList<Course>();
        this.projList = new ArrayList<Project>();
    }

    /*
    * Method: addCourse()
    * Description: Calls getCourses() to add all course to
    *              an array list
    * Parameters: Course c
    * Returns: courseList : ArrayList<Course>
    */
    public void addCourse(Course c){
        courseList.add(c);
    }

    ArrayList<Course> getCourses() {

        return courseList;
    }

    /*
    * Method: courseLength()
    * Description: Accessor for the size of the courseList
    * Parameters: N/A
    * Returns: courseList.Size : int
    */
    public int courseLength(){
        return courseList.size();
    }

    /*
    * Method: addProj()
    * Description: Adds a Project object to
    *              an array list
    * Parameters: Project p
    * Returns: N/A
    */
    void addProj(Project p){
        projList.add(p);
    }

    /*
    * Method: getProj()
    * Description: An Accessor for the projList
    * Parameters: N/A
    * Returns: projList : ArrayList<Project>
    */
    ArrayList<Project> getProj(){
        return projList;
    }

    /*
    * Method: projLength()
    * Description: An Accessor for the size of the projList
    * Parameters: N/A
    * Returns: projList.size() : int
    */
    int projLength(){
        return projList.size();
    }

    /*
    * Method: save()
    * Description: Save the contents of the Container object to a file
    * Parameters: Content   : context
    *             Container : c
    * Returns: N/A
    */
    void save(Context context, Container c)
    {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(c);
            os.close();
            fos.close();
        }
        catch (IOException e)
        {
            Log.e("FileIO - save",e.getMessage());
        }
    }

    /*
    * Method: laod()
    * Description: Loads the contents of a file into a Container object
    * Parameters: Context context
    * Returns: save : Container
    */
    Container load(Context context)
    {
        Container save = new Container();
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            save = (Container) is.readObject();
            is.close();
            fis.close();
            context.deleteFile(fileName);
        } catch (Exception e) {
            Log.e("FileIO - save",e.getMessage());
        }
        return save;
    }
}



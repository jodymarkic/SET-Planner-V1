package com.example.jmarkic8674.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Filename     : ProjectArrayAdapter.java
 * Project      : SETPlanner
 * Author       : Nathan Bray, Jody Markic, Gabriel Paquette
 * Date Created : 2017-02-03
 * Description  :
 */


class ProjectArrayAdapter extends ArrayAdapter<Project> {

    private Context context;
    private List<Project> projList;
    private Container container;
    private boolean flag;


    ProjectArrayAdapter(Context c, int r, ArrayList<Project> obj, Container contain, boolean flag){
        super(c,r,obj);

        this.container = contain;
        this.context = c;
        this.projList = obj;
        this.flag = flag;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        Project proj = projList.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.project_layout, null);

        TextView courseName = (TextView) view.findViewById(R.id.courseName);
        TextView projName = (TextView) view.findViewById(R.id.projName);
        TextView date = (TextView) view.findViewById(R.id.date);
        TextView status = (TextView) view.findViewById(R.id.status);
        if(flag)
        {
            try {
                for (Course course : container.getCourses()) {
                    if (course.getCourseID() == proj.getCourseID()) {
                        courseName.setText(course.toString());
                        break;
                    }
                }
            } catch (Exception e) {
                Log.e("ArrayAdapter-CourseName", e.getMessage());
            }
         }
        else
        {
            courseName.setText("");
        }
        projName.setText(proj.getProjectName());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String d = sdf.format(proj.getDueDate());
        Resources res = context.getResources();
        String text = String.format(res.getString(R.string.dueString), d);
        date.setText(text);
        String s;
        int c;
        if(proj.getStatus()){
            s = "Complete";
            c = Color.GREEN;
        }
        else{
            String currentDate = sdf.format(new Date());

            if(sdf.format(proj.getDueDate()).compareTo(currentDate) >= 0) {
                s = "Incomplete";
                c = Color.BLUE;
            }
            else
            {
                s = "Overdue";
                c = Color.RED;
            }
        }
        status.setText(s);
        status.setTextColor(c);

        return view;
    }
}

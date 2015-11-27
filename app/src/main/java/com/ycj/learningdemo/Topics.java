package com.ycj.learningdemo;

import android.app.Activity;

import com.ycj.learningdemo.a0_support_design_library.CoordinatorActivity;

import java.util.ArrayList;

/**
 * Created by YCJ on 2015/11/26.
 */
public class Topics {


    private ArrayList<Topic> mTopicList = new ArrayList<Topic>(){
        {
            add(new Topic(CoordinatorActivity.class, "Learning CoordinatorLayout"));
        }
    };

    public static class Topic{
        Class aClass;
        String name;


        public <T extends Activity> Topic(Class<T> aClass, String name) {
            this.aClass = aClass;
            this.name = name;
        }
    }

    public int getCount(){
        return mTopicList.size();
    }

    public Topic getTopic(int index){
        return mTopicList.get(index);
    }

}

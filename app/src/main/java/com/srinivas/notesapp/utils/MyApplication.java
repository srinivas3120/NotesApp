package com.srinivas.notesapp.utils;

import android.app.Application;
import android.content.Context;

import com.srinivas.notesapp.database.Env;
import com.srinivas.notesapp.database.NotesAppDBHelper;

/**
 * Created by Mudavath Srinivas on 19-11-2015.
 */
public class MyApplication extends Application {

    private static MyApplication sInstance=null;
    public static final String TAG = MyApplication.class
            .getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;

        NotesAppDBHelper dbHelper = new NotesAppDBHelper();
        Env.init(getApplicationContext(), dbHelper, null, true);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static MyApplication getsInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }

}

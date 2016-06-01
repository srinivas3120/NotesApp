package com.srinivas.notesapp.model;

import android.os.AsyncTask;

import com.srinivas.notesapp.database.DatabaseMgr;
import com.srinivas.notesapp.database.NotesAppDBMgr;
import com.srinivas.notesapp.database.NotesItem;
import com.srinivas.notesapp.utils.Common;

import java.util.ArrayList;

/**
 * Created by Mudavath Srinivas on 29-05-2016.
 */
public class NotesListFragmentModel extends BasicModel {

    private GetNotesTask getNotesTask=null;
    public void getNotes(){
        if(getNotesTask==null){
            getNotesTask=new GetNotesTask();
            getNotesTask.execute();
        }
    }


    private class GetNotesTask extends AsyncTask<String,String,ArrayList<NotesItem>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<NotesItem> doInBackground(String... params) {
            return NotesAppDBMgr.getNotes();
        }

        @Override
        protected void onPostExecute(ArrayList<NotesItem> s) {
            super.onPostExecute(s);
            notifyObservers(s);
            getNotesTask=null;
        }
    }

}

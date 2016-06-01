package com.srinivas.notesapp.model;

import android.os.AsyncTask;
import com.srinivas.notesapp.database.NotesAppDBMgr;
import com.srinivas.notesapp.database.NotesItem;
import java.util.ArrayList;

/**
 * Created by Mudavath Srinivas on 30-05-2016.
 */
public class CreateNotesModel extends BasicModel {

    private InsertNotesItem insertNotesItem =null;

    public void insertNotes(NotesItem notesItem){
        if(insertNotesItem ==null){
            insertNotesItem =new InsertNotesItem(notesItem);
            insertNotesItem.execute();
        }
    }


    private class InsertNotesItem extends AsyncTask<String,String,String> {

        private NotesItem notesItem;
        private InsertNotesItem(NotesItem notesItem){
            this.notesItem=notesItem;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            NotesAppDBMgr.insertNotes(notesItem);
            return "ok";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            notifyObservers(s);
            insertNotesItem =null;
        }
    }

}

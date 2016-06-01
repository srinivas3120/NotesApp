package com.srinivas.notesapp.database;


import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class NotesAppDBMgr {

    private static boolean dataDirty=false;

    private static ArrayList<NotesItem> notesItems=new ArrayList<NotesItem>();
    private static String findNotes="SELECT "+
            "n."+NotesTable.NOTE_ID+","+
            "n."+NotesTable.NOTE_DATA+","+
            "n."+NotesTable.NOTE_TAG_ID+","+
            "t."+NotesTagTable.NOTE_TAG_TYPE+","+
            "n."+NotesTable.NOTE_CREATED_DATE+","+
            "n."+NotesTable.NOTE_MODIFIED_DATE+" from "+NotesTable.TABLE_NAME+" n LEFT OUTER JOIN "+
            NotesTagTable.TABLE_NAME+" t ON "+
            "n."+NotesTable.NOTE_TAG_ID+"=t."+NotesTagTable.NOTE_TAG_ID+" order by 6 DESC,4 ASC";


    private static String getNotesItem="SELECT "+NotesTable.NOTE_ID+","+
            NotesTable.NOTE_DATA+","+
            NotesTable.NOTE_CREATED_DATE+","+
            NotesTable.NOTE_MODIFIED_DATE+" from "+NotesTable.TABLE_NAME+
            " where "+NotesTable.NOTE_ID+"=";

    public static ArrayList<NotesItem> getNotes(){
        if(isDataDirty() || notesItems.size()<1) {
            notesItems.clear();
            setDataDirty(false);
            Cursor cursor=DatabaseMgr.selectRowsRawQuery(findNotes);

            if(cursor!=null && cursor.getCount()>0){ // getCount() gives no. of rows
                while(cursor.moveToNext()){
                    String notes=cursor.getString(cursor.getColumnIndex(NotesTable.NOTE_DATA));
                    long noteId=cursor.getLong(cursor.getColumnIndex(NotesTable.NOTE_ID));
                    long createdDate=cursor.getLong(cursor.getColumnIndex(NotesTable.NOTE_CREATED_DATE));
                    long modifiedDate=cursor.getLong(cursor.getColumnIndex(NotesTable.NOTE_MODIFIED_DATE));

                    long noteTagId=cursor.getLong(cursor.getColumnIndex(NotesTagTable.NOTE_TAG_ID));
                    String noteTag=cursor.getString(cursor.getColumnIndex(NotesTagTable.NOTE_TAG_TYPE));

                    notesItems.add(new NotesItem(noteId,notes,noteTag,noteTagId,createdDate,modifiedDate));
                }
            }
        }
        return notesItems;
    }

    public static void insertNotes(NotesItem notesItem){
        ContentValues values=new ContentValues();
        values.put(NotesTable.NOTE_DATA,notesItem.getNotes());
        if(notesItem.getNoteId()!=-1){
            values.put(NotesTable.NOTE_ID,notesItem.getNoteId());
        }
        values.put(NotesTable.NOTE_TAG_ID, notesItem.getNoteTagId());
        values.put(NotesTable.NOTE_CREATED_DATE,notesItem.getNoteCreatedDate());
        values.put(NotesTable.NOTE_MODIFIED_DATE, notesItem.getNoteModifiedDate());

        DatabaseMgr.insertRow(NotesTable.TABLE_NAME, values);
        setDataDirty(true);
    }

    public static boolean insertNoteTag(String noteTagType){
        ContentValues values=new ContentValues();

        values.put(NotesTagTable.NOTE_TAG_TYPE, noteTagType);

        long insert_noteTagId=DatabaseMgr.insertRow(NotesTagTable.TABLE_NAME, values);

        setDataDirty(true);

        return (insert_noteTagId>0);
    }

    public static long getNoteTagId(String noteTagType){
        String query="SELECT "+NotesTagTable.NOTE_TAG_ID+" from "+
                NotesTagTable.TABLE_NAME+" where "+NotesTagTable.NOTE_TAG_TYPE+"='"+noteTagType+"'";
        Cursor cursor=DatabaseMgr.selectRowsRawQuery(query);
        Long tagId = null;
        if(cursor.getCount()>0){ // getCount() gives no. of rows
            while(cursor.moveToNext()){
                tagId=cursor.getLong(cursor.getColumnIndex(NotesTagTable.NOTE_TAG_ID));
            }
            return tagId;
        }
        return 0;

    }

    public static List<String> findTags(){
        List<String> tags = new ArrayList<String>();
        String allTagsQuery="SELECT "+NotesTagTable.NOTE_TAG_TYPE+" from "+
                NotesTagTable.TABLE_NAME;
        Cursor cursor=DatabaseMgr.selectRowsRawQuery(allTagsQuery);

        if(cursor.getCount()>0){ // getCount() gives no. of rows
            while(cursor.moveToNext()){
                String tag=null;
                tag=cursor.getString(cursor.getColumnIndex(NotesTagTable.NOTE_TAG_TYPE));
                tags.add(tag);
            }
        }
        return tags;
    }


    public static boolean updateNoteTag(long noteTagId,String noteTagType){
         ContentValues values=new ContentValues();

        values.put(NotesTagTable.NOTE_TAG_TYPE,noteTagType);
        values.put(NotesTagTable.NOTE_TAG_ID,noteTagId);

        int update_noteTag=DatabaseMgr.insertRow(NotesTagTable.TABLE_NAME, values);

        setDataDirty(true);

        return (update_noteTag>0);
    }

    public static NotesItem getNotesItem(long notesId){

        Cursor cursor=DatabaseMgr.selectRowsRawQuery(getNotesItem + notesId);

        if(cursor!=null && cursor.getCount()>0){ // getCount() gives no. of rows
            while(cursor.moveToNext()){
                String notes=cursor.getString(cursor.getColumnIndex(NotesTable.NOTE_DATA));
                long noteId=cursor.getLong(cursor.getColumnIndex(NotesTable.NOTE_ID));
                long createdDate=cursor.getLong(cursor.getColumnIndex(NotesTable.NOTE_CREATED_DATE));
                long modifiedDate=cursor.getLong(cursor.getColumnIndex(NotesTable.NOTE_MODIFIED_DATE));

               return new NotesItem(noteId,notes,createdDate,modifiedDate);
            }
        }
        return null;
    }

    public static String getTagType(long noteRelationNoteTagId) {
        String query="SELECT "+NotesTagTable.NOTE_TAG_TYPE+" from "+NotesTagTable.TABLE_NAME+
                " where "+NotesTagTable.NOTE_TAG_ID+"="+noteRelationNoteTagId;
        Cursor cursor=DatabaseMgr.selectRowsRawQuery(query);

        String noteNoteTagType = null;
        if(cursor.getCount()>0){ // getCount() gives no. of rows
            while(cursor.moveToNext()){
                noteNoteTagType=cursor.getString(cursor.getColumnIndex(NotesTagTable.NOTE_TAG_TYPE));
                break;
            }
        }
        return noteNoteTagType;
    }

    public static boolean isDataDirty() {
        return dataDirty;
    }

    public static void setDataDirty(boolean dataDirty) {
        NotesAppDBMgr.dataDirty = dataDirty;
    }

    public static void deleteNotes(NotesItem notesItem) {
        String removerFromNoteIt=NotesTable.NOTE_ID+"="+notesItem.getNoteId();
        DatabaseMgr.deleteRow(NotesTable.TABLE_NAME,removerFromNoteIt);
        setDataDirty(true);
    }
}

package com.srinivas.notesapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.srinivas.notesapp.R;
import com.srinivas.notesapp.activity.base.BaseActivity;
import com.srinivas.notesapp.activity.base.BaseActivityController;
import com.srinivas.notesapp.database.NotesAppDBMgr;
import com.srinivas.notesapp.database.NotesItem;
import com.srinivas.notesapp.model.BasicModel;
import com.srinivas.notesapp.model.CreateNotesModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by Mudavath Srinivas on 29-05-2016.
 */
public class CreateNotesActivity extends BaseActivityController implements View.OnClickListener {

    public static final int NOTES_UPDATED = 101;
    private CreateNotesModel createNotesModel=new CreateNotesModel();
    private NotesItem notesItem;

    private EditText et_notes;
    private Spinner noteTags;
    private long noteTagId;
    private String tagType;
    private Button addTag;

    @Override
    protected void onCreatePost(Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_notes);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null && bundle.containsKey(NotesItem.NotesItemKey)){
            notesItem=(NotesItem)(bundle.get(NotesItem.NotesItemKey));
            noteTagId=notesItem.getNoteTagId();
            tagType=notesItem.getNoteTag();
        }

        initViews();
    }

    @Override
    protected BasicModel getModel() {
        return createNotesModel;
    }

    private void initViews() {
        setupToolbar(false);
        if(getSupportActionBar() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(R.string.add_note);
        }
        et_notes= (EditText) findViewById(R.id.et_notes);
        noteTags = (Spinner) findViewById(R.id.noteTags);
        addTag= (Button) findViewById(R.id.addTag);

        addTag.setOnClickListener(this);

        if(notesItem!=null){
            et_notes.setText(notesItem.getNotes());
            et_notes.setSelection(et_notes.getText().length());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setTagsToSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_notes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                if(isNotesEdited()) {
                    insertNotes();
                }else {
                    onBackPressed();
                }
                break;
            case R.id.delete:
                deleteNotes();
                break;
            case android.R.id.home:
                if(isNotesEdited()) {
                    discardNotes();
                }else {
                    onBackPressed();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteNotes() {
        if(notesItem!=null){
            NotesAppDBMgr.deleteNotes(notesItem);
        }
        onBackPressed();
    }

    private void discardNotes() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.notes_discard_msg);

        alertDialogBuilder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                onBackPressed();
            }
        });
        alertDialogBuilder.setPositiveButton(R.string.string_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.show();
    }

    private void insertNotes() {
        if(notesItem==null ){
            notesItem=NotesItem.getNew();
        }
        notesItem.setNotes(et_notes.getText().toString().trim());
        notesItem.setNoteModifiedDate(System.currentTimeMillis());
        notesItem.setNoteTag(tagType);
        notesItem.setNoteTagId(noteTagId);
        createNotesModel.insertNotes(notesItem);
    }


    private boolean isNotesEdited(){
        if(notesItem!=null){
            return !et_notes.getText().toString().trim().equals(notesItem.getNotes()) || noteTagId!=notesItem.getNoteTagId();
        }
        return !et_notes.getText().toString().trim().equals("");
    }

    @Override
    public void update(Observable observable, Object data) {
        if(data instanceof String && (data.equals("ok"))){
            onBackPressed();
            finish();
        }
    }

    private void setTagsToSpinner() {
        List<String> list = new ArrayList<String>();
        list = NotesAppDBMgr.findTags();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list) {

        };
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noteTags.setAdapter(spinnerAdapter);
        if (tagType != null)
            noteTags.setSelection(list.indexOf(tagType));
        else
            noteTags.setSelection(0);

        noteTags.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                tagType=parent.getItemAtPosition(
                        position).toString();
                noteTagId = NotesAppDBMgr.getNoteTagId(tagType);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addTag:
                startActivity(new Intent(this,AddTagActivity.class));
                break;
            default:
                break;
        }
    }
}

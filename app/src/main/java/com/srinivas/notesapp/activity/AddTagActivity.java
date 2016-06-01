package com.srinivas.notesapp.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.srinivas.notesapp.R;
import com.srinivas.notesapp.activity.base.BaseActivity;
import com.srinivas.notesapp.database.NotesAppDBMgr;

import java.util.List;

/**
 * Created by Mudavath Srinivas on 31-05-2016.
 */
public class AddTagActivity extends BaseActivity {

    private static final int MENU_DELETE_TAG_ID = 2001;
    private static final int MENU_UPDATE_TAG_ID = 2002;

    private ListView allNoteTags;
    private Button addTag;
    private EditText addTagEditText;

    private Spinner allNoteTagsSpinner;
    private EditText updateTagEditText;
    private Button updateTag;

    private long spinnerNoteTagId;
    private int currentNoteId;


    private List<String> allTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);
        initViews();
    }

    private void initViews() {
        setupToolbar(false);
        if(getSupportActionBar() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(R.string.add_tag);
        }

        allNoteTags=(ListView) findViewById(R.id.allNoteTags);
        addTag=(Button) findViewById(R.id.addTag);
        addTagEditText=(EditText) findViewById(R.id.addTagEditText);

        allNoteTagsSpinner=(Spinner) findViewById(R.id.allNoteTagsSpinner);
        updateTagEditText=(EditText) findViewById(R.id.updateTagEditText);
        updateTag=(Button) findViewById(R.id.updateTag);

        registerForContextMenu(allNoteTags);

        allNoteTagsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                spinnerNoteTagId = NotesAppDBMgr.getNoteTagId(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        updateTag.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String updateTag = updateTagEditText.getText().toString();
                Log.i("updateTag", updateTag);
                boolean success = NotesAppDBMgr.updateNoteTag(spinnerNoteTagId, updateTag);
                if (success) {
                    //update notes tag
                    Toast.makeText(getApplicationContext(), "'" + updateTag + "' Tag was Updated successfully",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error !!! '" + updateTag + "' Tag can not be Updated",
                            Toast.LENGTH_SHORT).show();
                }
                refreshTags();
            }

        });

        addTag.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String addTag = addTagEditText.getText().toString();
                boolean success = NotesAppDBMgr.insertNoteTag(addTag);
                if (success) {
                    Toast.makeText(getApplicationContext(), "'" + addTag + "' Tag was added successfully",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error !!! '" + addTag + "' Tag can not be added",
                            Toast.LENGTH_SHORT).show();
                }
                refreshTags();
            }
        });

        refreshTags();

    }

    private void refreshTags() {
        allTags=NotesAppDBMgr.findTags();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, allTags);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        allNoteTagsSpinner.setAdapter(spinnerAdapter);


        allNoteTags.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allTags));
        allNoteTags.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) menuInfo;
        currentNoteId=(int) info.id;

        menu.setHeaderTitle("Select The Action to delete Tag and Notes related to Tag permanently ");
        menu.add(0, MENU_DELETE_TAG_ID, 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId()==MENU_DELETE_TAG_ID){

            Toast.makeText(getApplicationContext(),"This Feature will be added soon !",
                    Toast.LENGTH_SHORT).show();

        }
        refreshTags();

        return super.onContextItemSelected(item);
    }

}


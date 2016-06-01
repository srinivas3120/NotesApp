package com.srinivas.notesapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.srinivas.notesapp.R;
import com.srinivas.notesapp.activity.CreateNotesActivity;
import com.srinivas.notesapp.adpater.NotesListAdapter;
import com.srinivas.notesapp.database.NotesAppDBMgr;
import com.srinivas.notesapp.database.NotesItem;
import com.srinivas.notesapp.model.BasicModel;
import com.srinivas.notesapp.model.NotesListFragmentModel;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Mudavath Srinivas on 29-05-2016.
 */
public class NotesListFragment extends AbstractFragment implements View.OnClickListener {

    private NotesListFragmentModel notesListFragmentModel=new NotesListFragmentModel();
    public static final int CREATE_NOTES_ACTIVITY=100;

    public static final String TAG_NOTE_LIST_FRAGMENT ="NotesListFragment";
    private LinearLayout ll_progress_status;
    private TextView tv_error_status;
    private ProgressBar progressBar;
    private RecyclerView rv_notes;
    private Context mContext;

    private FloatingActionButton mFloatPostButton;

    private NotesListAdapter notesListAdapter;
    private LinearLayoutManager linearLayoutManager;

    private ArrayList<NotesItem> notesItems=new ArrayList<>();
    View.OnClickListener clickListener;
    private View.OnLongClickListener longClickListener;

    @Override
    protected View onCreateViewPost(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        mContext=getContext();
    }

    @Override
    public void onResume() {
        super.onResume();
        getNotes();
    }

    private void getNotes() {
        ll_progress_status.setVisibility(View.VISIBLE);
        rv_notes.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        tv_error_status.setText("Loading...");

        notesListFragmentModel.getNotes();
    }

    private void initViews(View view) {
        ll_progress_status= (LinearLayout) view.findViewById(R.id.ll_progress_status);
        tv_error_status= (TextView) view.findViewById(R.id.tv_error_status);
        progressBar= (ProgressBar) view.findViewById(R.id.progressBar);
        rv_notes= (RecyclerView) view.findViewById(R.id.rv_notes);
        mFloatPostButton = (FloatingActionButton) view.findViewById(R.id.fab_post);

        ll_progress_status.setOnClickListener(this);
        mFloatPostButton.setOnClickListener(this);
        mClickListener();

        linearLayoutManager=new LinearLayoutManager(mContext);
        rv_notes.setLayoutManager(linearLayoutManager);
        notesListAdapter=new NotesListAdapter(notesItems,clickListener,longClickListener);
        rv_notes.setAdapter(notesListAdapter);
    }

    private void mClickListener() {
        clickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(Integer)v.getTag();
                switch (v.getId()){
                    case R.id.tv_notes:
                        startCreateNotesActivity(position);
                        break;
                    default:
                        break;
                }
            }
        };

        longClickListener=new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int position=(int)v.getTag();
                switch (v.getId()){
                    case R.id.tv_notes:
                    case R.id.tv_tag:
                    case R.id.tv_date:
                    case R.id.ll_notes:
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                        alertDialogBuilder.setMessage("Are sure want to delete notes!");

                        alertDialogBuilder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                NotesAppDBMgr.deleteNotes(notesItems.get(position));
                                getNotes();
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

                        break;
                    default:
                        break;
                }
                return false;
            }
        };
    }

    private void startCreateNotesActivity(int position) {
        Intent intent=new Intent(mContext,CreateNotesActivity.class);
        if(position>=0){
            intent.putExtra(NotesItem.NotesItemKey,notesItems.get(position));
        }
        startActivity(intent);
    }

    @Override
    protected BasicModel getModel() {
        return notesListFragmentModel;
    }

    @Override
    public void update(Observable observable, Object data) {
        if(data instanceof ArrayList){
            notesItems.clear();
            notesItems.addAll((ArrayList<NotesItem>)data);
        }

        if(notesItems.size()>0){
            ll_progress_status.setVisibility(View.GONE);
            rv_notes.setVisibility(View.VISIBLE);
            notesListAdapter.notifyDataSetChanged();
        }else {
            ll_progress_status.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            tv_error_status.setText(R.string.no_notes_msg);
            rv_notes.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_post:
                startCreateNotesActivity(-1);
                break;
            case R.id.ll_progress_status:
                break;
            default:
                break;
        }
    }

}

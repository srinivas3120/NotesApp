package com.srinivas.notesapp.adpater;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.srinivas.notesapp.R;
import com.srinivas.notesapp.database.NotesAppDBMgr;
import com.srinivas.notesapp.database.NotesItem;
import com.srinivas.notesapp.utils.Common;

import java.util.ArrayList;

/**
 * Created by Mudavath Srinivas on 29-05-2016.
 */
public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {

    ArrayList<NotesItem> notesItems=new ArrayList<>();
    private View.OnClickListener clickListener;
    private View.OnLongClickListener longClickListener;

    public NotesListAdapter(ArrayList<NotesItem> notesItems, View.OnClickListener clickListener, View.OnLongClickListener longClickListener){
        this.notesItems=notesItems;
        this.clickListener=clickListener;
        this.longClickListener=longClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final NotesItem notesItem=notesItems.get(position);

        holder.tv_tag.setText(notesItem.getNoteTag());
        holder.tv_notes.setText(notesItem.getNotes());
        holder.tv_date.setText(Common.getFormattedTime(notesItem.getNoteModifiedDate()));

        holder.tv_notes.setTag(position);
        holder.ll_notes.setTag(position);

    }

    @Override
    public int getItemCount() {
        return notesItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_tag;
        private TextView tv_notes;
        private TextView tv_date;
        private LinearLayout ll_notes;

        public ViewHolder(View v) {
            super(v);
            tv_tag = (TextView) v.findViewById(R.id.tv_tag);
            tv_notes = (TextView) v.findViewById(R.id.tv_notes);
            tv_date = (TextView) v.findViewById(R.id.tv_date);
            ll_notes= (LinearLayout) v.findViewById(R.id.ll_notes);

            tv_notes.setOnClickListener(clickListener);
            tv_tag.setOnLongClickListener(longClickListener);
            tv_date.setOnLongClickListener(longClickListener);
            tv_notes.setOnLongClickListener(longClickListener);
            ll_notes.setOnLongClickListener(longClickListener);
        }


    }
}

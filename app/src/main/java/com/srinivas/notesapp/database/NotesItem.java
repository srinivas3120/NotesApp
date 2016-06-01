package com.srinivas.notesapp.database;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotesItem implements Parcelable {

	public static final String NotesItemKey="com.srinivas.notesItem";
	private long noteId;
	private String notes;
	private String noteTag;
	private long noteTagId;
	private long noteCreatedDate;
	private long noteModifiedDate;


	public NotesItem(long noteId, String notes, long noteCreatedDate, long noteModifiedDate) {
		this.noteId = noteId;
		this.notes = notes;
		this.noteCreatedDate = noteCreatedDate;
		this.noteModifiedDate = noteModifiedDate;
	}

	public NotesItem(long noteId, String notes, String noteTag, long noteTagId, long noteCreatedDate, long noteModifiedDate) {
		this.noteId = noteId;
		this.notes = notes;
		this.noteTag = noteTag;
		this.noteTagId = noteTagId;
		this.noteCreatedDate = noteCreatedDate;
		this.noteModifiedDate = noteModifiedDate;
	}

	public static NotesItem getNew() {
		
		NotesItem note = new NotesItem();
		note.setNoteCreatedDate(System.currentTimeMillis());
		note.setNoteId(System.currentTimeMillis());
		note.setNotes("");
		return note;
		
	}

	public NotesItem(){}

	public NotesItem(Parcel in){
		noteId=in.readLong();
		notes =in.readString();
		noteTag=in.readString();
		noteTagId=in.readLong();
		noteCreatedDate=in.readLong();
		noteModifiedDate=in.readLong();

	}
	
	@Override
	public int describeContents() {	
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(noteId);
		dest.writeString(notes);
		dest.writeString(noteTag);
		dest.writeLong(noteTagId);
		dest.writeLong(noteCreatedDate);
		dest.writeLong(noteModifiedDate);

	}
	
	public static final Parcelable.Creator<NotesItem> CREATOR=
			new Parcelable.Creator<NotesItem>() {
				@Override
				public NotesItem createFromParcel(Parcel source) {
					return new NotesItem(source);
				} 

				@Override
				public NotesItem[] newArray(int size) {
					return new NotesItem[size];
				}
			};



	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public long getNoteId() {
		return noteId;
	}

	public void setNoteId(long noteId) {
		this.noteId = noteId;
	}

	public long getNoteCreatedDate() {
		return noteCreatedDate;
	}

	public void setNoteCreatedDate(long noteCreatedDate) {
		this.noteCreatedDate = noteCreatedDate;
	}

	public long getNoteModifiedDate() {
		return noteModifiedDate;
	}

	public void setNoteModifiedDate(long noteModifiedDate) {
		this.noteModifiedDate = noteModifiedDate;
	}

	public String getNoteTag() {
		return noteTag;
	}

	public void setNoteTag(String noteTag) {
		this.noteTag = noteTag;
	}

	public static String getNotesItemKey() {
		return NotesItemKey;
	}

	public long getNoteTagId() {
		return noteTagId;
	}

	public void setNoteTagId(long noteTagId) {
		this.noteTagId = noteTagId;
	}
}

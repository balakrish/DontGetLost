package com.balakrish.gpstracker.db;
/*
 * Copyright (C) 2010-2013 BalaKrish - http://facebook.com/balakrish
 *
 *
 * This file is part of DontGetLost - http://facebook.com/balakrish
 * 
 * DontGetLost is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DontGetLost is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DontGetLost.  If not, see <http://www.gnu.org/licenses/>.
 */
import android.database.Cursor;

public class Track extends AbstractTrack {
	
	private String descr;
	
	private String title;
	
	private int activity;
	
	private int recording;
	
	public Track(Cursor cursor) {
		
		super(cursor);

		this.title = cursor.getString(cursor.getColumnIndex("title"));

		this.descr = cursor.getString(cursor.getColumnIndex("descr"));

		this.activity = cursor.getInt(cursor.getColumnIndex("activity"));

		this.recording = cursor.getInt(cursor.getColumnIndex("recording"));
		
	}
	
	public Track() {
		super();
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getActivity() {
		return activity;
	}

	public void setActivity(int activity) {
		this.activity = activity;
	}

	public int getRecording() {
		return recording;
	}

	public void setRecording(int recording) {
		this.recording = recording;
	}

	
	
	
}

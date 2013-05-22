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

public class Segment extends AbstractTrack {
	
	private long trackId;

	private int segmentIndex = 0;
	
	/**
	 * Create track segment from database
	 * @param cursor
	 */
	public Segment(Cursor cursor) {
		
		super(cursor);

		this.trackId = cursor.getLong(cursor.getColumnIndex("track_id"));

		this.segmentIndex = cursor.getInt(cursor.getColumnIndex("segment_index"));
		
	}

	/**
	 * Create empty track segment 
	 */
	public Segment(long trackId, int segmentIndex) {

		super();

		this.trackId = trackId;

		this.segmentIndex = segmentIndex;
		
	}

	public long getTrackId() {
		return trackId;
	}


	public void setTrackId(long trackId) {
		this.trackId = trackId;
	}


	public int getSegmentIndex() {
		return segmentIndex;
	}


	public void setSegmentIndex(int segmentIndex) {
		this.segmentIndex = segmentIndex;
	}


}

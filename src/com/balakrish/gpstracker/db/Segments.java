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
import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.balakrish.gpstracker.utils.Utils;

public class Segments {

	public static final String TABLE_NAME = "segments";

	public static final String TABLE_CREATE =
			"CREATE TABLE " + TABLE_NAME +
					" (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
					"track_id INTEGER NOT NULL," +
					"segment_index INTEGER," +
					"distance REAL," +
					"total_time INTEGER," +
					"moving_time INTEGER," +
					"max_speed REAL," +
					"max_elevation REAL," +
					"min_elevation REAL," +
					"elevation_gain REAL," +
					"elevation_loss REAL," +
					"start_time INTEGER NOT NULL," +
					"finish_time INTEGER)";

	public static ArrayList<Segment> getAll(SQLiteDatabase db, long trackId) {

		ArrayList<Segment> segments = new ArrayList<Segment>();

		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE track_id = " + trackId;
		
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToFirst();

		while (cursor.isAfterLast() == false) {

			Segment wp = new Segment(cursor);

			segments.add(wp);

			cursor.moveToNext();
		}

		cursor.close();

		return segments;
	}

	
	public static Segment get(SQLiteDatabase db, long trackId, long segmentId, int segmentIndex) {

		String sql = "SELECT segments.*, COUNT(track_points._id) AS points_count FROM segments, track_points WHERE"
				+ " segments._id=" + segmentId + " AND segments.track_id=" + trackId
				+ " AND track_points.segment_index=" + (segmentIndex - 1)
				+ " AND segments.track_id = track_points.track_id";

		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToFirst();

		Segment segment = new Segment(cursor);

		cursor.close();

		return segment;
	}
	
	public static long insert(SQLiteDatabase db, Segment segment) {
		
		ContentValues values = new ContentValues();
		
		values.put("track_id", segment.getTrackId());
		values.put("segment_index", segment.getSegmentIndex());
		
		values.put("distance", Utils.formatNumber(segment.getDistance(), 1));
		
		values.put("total_time", segment.getTotalTime());
		values.put("moving_time",  segment.getMovingTime());
		
		values.put("max_speed", Utils.formatNumber(segment.getMaxSpeed(), 2));
		
		values.put("max_elevation", Utils.formatNumber(segment.getMaxElevation(), 1));
		values.put("min_elevation", Utils.formatNumber(segment.getMinElevation(), 1));
		
		values.put("elevation_gain", segment.getElevationGain());
		values.put("elevation_loss", segment.getElevationLoss());
		
		values.put("start_time", segment.getStartTime());
		values.put("finish_time", segment.getFinishTime());

		return db.insertOrThrow("segments", null, values);
			
	}
	
}

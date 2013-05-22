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

import com.balakrish.gpstracker.utils.MapSpan;
import com.balakrish.gpstracker.utils.Utils;

public class TrackPoints {

	public static final String TABLE_NAME = "track_points";

	public static final String TABLE_CREATE =
			"CREATE TABLE " + TABLE_NAME +
					" (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
					"track_id INTEGER NOT NULL," +
					"segment_index INTEGER," +
					"distance REAL," +
					"lat INTEGER NOT NULL," +
					"lng INTEGER NOT NULL," +
					"accuracy REAL," +
					"elevation REAL," +
					"speed REAL," +
					"time INTEGER NOT NULL)";

	/**
	 * Get total number of track points in track
	 * 
	 * @param db
	 * @param trackId
	 * @return
	 */
	public static int getCount(SQLiteDatabase db, long trackId) {

		String sql = "SELECT COUNT(*) AS count FROM track_points WHERE track_id=" + trackId + ";";
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToFirst();

		int count = cursor.getInt(cursor.getColumnIndex("count"));

		cursor.close();

		return count;
	}

	/**
	 * Get all track points for required track
	 * 
	 * @param db
	 * @param trackId
	 * @param points
	 */
	public static ArrayList<TrackPoint> getAll(SQLiteDatabase db, long trackId, MapSpan mapSpan) {

		ArrayList<TrackPoint> points = new ArrayList<TrackPoint>();
		
		String sql = "SELECT * FROM track_points WHERE track_id=" + trackId + ";";
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToFirst();

		while (cursor.isAfterLast() == false) {

			// track point object
			TrackPoint tp = new TrackPoint(cursor);

			// update map span
			mapSpan.updateMapSpan(tp.getLatE6(), tp.getLngE6());

			points.add(tp);

			cursor.moveToNext();
		}

		cursor.close();
		
		return points;

	}

	public static long insert(SQLiteDatabase db, TrackPoint trackPoint) {

		ContentValues values = new ContentValues();
		
		values.put("track_id", trackPoint.getTrackId());
		values.put("segment_index", trackPoint.getSegmentIndex());
		
		values.put("lat", trackPoint.getLatE6());
		values.put("lng", trackPoint.getLngE6());
		values.put("elevation", Utils.formatNumber(trackPoint.getElevation(), 1));
		values.put("speed", Utils.formatNumber(trackPoint.getSpeed(), 2));

		values.put("time", trackPoint.getTime());

		values.put("distance", Utils.formatNumber(trackPoint.getDistance(), 1));
		values.put("accuracy", trackPoint.getAccuracy());

		long trackPointId = db.insertOrThrow("track_points", null, values);

		return trackPointId;

	}

}

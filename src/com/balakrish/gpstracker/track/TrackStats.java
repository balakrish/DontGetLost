package com.balakrish.gpstracker.track;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.location.Location;
import android.widget.Toast;

import com.balakrish.gpstracker.Constants;
import com.balakrish.gpstracker.db.Track;
import com.balakrish.gpstracker.db.TrackPoint;
import com.balakrish.gpstracker.db.TrackPoints;
import com.balakrish.gpstracker.db.Tracks;
import com.balakrish.gpstracker.utils.AppLog;

/**
 * Track statistics class
 */
public class TrackStats extends AbstractTrackStats {

	private Track track;

	public TrackStats(Context context) {

		super(context);

		this.track = new Track();

		this.insertNewTrack();

	}

	/**
	 * Id of the track being recorded
	 */
	private long trackId;

	public void setTrackId(long tid) {
		this.trackId = tid;
	}

	public Track getTrack() {
		return this.track;
	}

	/**
	 * Add new track to application db after recording started
	 */
	public void insertNewTrack() {

		track.setTitle("New track");
		track.setActivity(Constants.ACTIVITY_TRACK);
		track.setRecording(1);
		track.setStartTime(this.trackTimeStart);
		track.setFinishTime(this.trackTimeStart);

		try {

			this.trackId = Tracks.insert(app.getDatabase(), track);

			track.setId(this.trackId);

		} catch (SQLiteException e) {
			Toast.makeText(context, "SQLiteException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			AppLog.e(app.getApplicationContext(), "TrackStats.insertNewTrack:  SQLiteException: " + e.getMessage());
		}

	}

	/**
	 * Update track data after recording finished
	 */
	protected void finishNewTrack() {

		long finishTime = (new Date()).getTime();

		String trackTitle = (new SimpleDateFormat("yyyy-MM-dd H:mm", Locale.US)).format(this.trackTimeStart) + "-"
				+ (new SimpleDateFormat("H:mm", Locale.US)).format(finishTime);

		track.setTitle(trackTitle);

		track.setDistance(this.getDistance());
		track.setTotalTime(this.getTotalTime());
		track.setMovingTime(this.getMovingTime());
		track.setMaxSpeed(this.getMaxSpeed());
		track.setMaxElevation(this.getMaxElevation());
		track.setMinElevation(this.getMinElevation());
		track.setElevationGain(this.getElevationGain());
		track.setElevationLoss(this.getElevationLoss());

		track.setRecording(Constants.TRACK_RECORDING_STOPPED);
		track.setFinishTime(finishTime);

		int affectedRows = Tracks.update(app.getDatabase(), track);
		if (affectedRows == 0) {
			Toast.makeText(context, "finishNewTrack updates failed", Toast.LENGTH_SHORT).show();
			AppLog.e(context, "finishNewTrack updates failed");
		}

	}

	/**
	 * Update track data during recording
	 */
	protected void updateNewTrack() {

		track.setDistance(this.getDistance());
		track.setTotalTime(this.getTotalTime());
		track.setMovingTime(this.getMovingTime());
		track.setMaxSpeed(this.getMaxSpeed());
		track.setMaxElevation(this.getMaxElevation());
		track.setMinElevation(this.getMinElevation());
		track.setElevationGain(this.getElevationGain());
		track.setElevationLoss(this.getElevationLoss());
		track.setFinishTime((new Date()).getTime());

		int affectedRows = Tracks.update(app.getDatabase(), track);

		if (affectedRows == 0) {
			Toast.makeText(context, "updateNewTrack failed", Toast.LENGTH_SHORT).show();
			AppLog.e(context, "updateNewTrack failed");
		}

	}

	/**
	 * Record one track point
	 * 
	 * @param location
	 *            Current location
	 */
	protected void recordTrackPoint(Location location, int segmentIndex) {

		TrackPoint trackPoint = new TrackPoint(trackId, location);
		
		trackPoint.setDistance(this.distance);
		trackPoint.setSegmentIndex(segmentIndex);

		try {

			TrackPoints.insert(app.getDatabase(), trackPoint);

		} catch (SQLiteException e) {
			Toast.makeText(context, "SQLiteException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			AppLog.e(app.getApplicationContext(), "SQLiteException: " + e.getMessage());
		}

	}

}

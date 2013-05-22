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
import java.util.Date;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.balakrish.gpstracker.db.Segment;
import com.balakrish.gpstracker.db.Segments;
import com.balakrish.gpstracker.utils.AppLog;

public class SegmentStats extends AbstractTrackStats {

	public SegmentStats(Context context) {

		super(context);

	}

	/**
	 * Index of the segment being recorded
	 */
	private long segmentIndex;

	public void setSegmentIndex(long sid) {
		this.segmentIndex = sid;
	}

	public long getSegmentIndex() {
		return this.segmentIndex;
	}

	/**
	 * Add new track to application db after recording started
	 */
	public long insertSegment(long trackId, int segmentIndex) {

		long finishTime = (new Date()).getTime();

		Segment segment = new Segment(trackId, segmentIndex);

		segment.setDistance(this.getDistance());
		segment.setTotalTime(this.getTotalTime());
		segment.setMovingTime(this.getMovingTime());
		segment.setMaxSpeed(this.getMaxSpeed());
		segment.setMaxElevation(this.getMaxElevation());
		segment.setMinElevation(this.getMinElevation());
		segment.setElevationGain(this.getElevationGain());
		segment.setElevationLoss(this.getElevationLoss());
		segment.setStartTime(this.trackTimeStart);
		segment.setFinishTime(finishTime);

		long newSegmentId = -1;
		try {

			newSegmentId = Segments.insert(app.getDatabase(), segment);
			
		} catch (SQLiteException e) {

			Toast.makeText(context, "SQLiteException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			AppLog.w(context, "SQLiteException: " + e.getMessage());

		}

		return newSegmentId;

	}

}
package com.balakrish.gpstracker;
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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.balakrish.gpstracker.R;
import com.balakrish.gpstracker.db.Segment;
import com.balakrish.gpstracker.db.Segments;
import com.balakrish.gpstracker.db.Track;
import com.balakrish.gpstracker.db.Tracks;
import com.balakrish.gpstracker.map.MyMapActivity;
import com.balakrish.gpstracker.utils.Utils;

/**
 * main application activity
 */
public class TrackDetailsActivity extends Activity {

	/**
	 * Reference to app object
	 */
	private App app;

	private long trackId;

	private int currentSegment = 0;

	private int numberOfSegments = 0;

	/**
	 * Track segments array
	 */
	private ArrayList<Segment> segments;

	private OnClickListener prevSegmentButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			if (numberOfSegments == 0) {
				Toast.makeText(TrackDetailsActivity.this, R.string.no_segments, Toast.LENGTH_SHORT).show();
				return;
			}

			if (currentSegment == 0) {
				currentSegment = numberOfSegments;
				updateSegment(currentSegment);
				return;

			}

			if (currentSegment > 1) {

				currentSegment--;
				updateSegment(currentSegment);

			} else {

				currentSegment = 0;
				updateTrack();

			}

		}

	};

	private OnClickListener nextSegmentButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			if (numberOfSegments == 0) {
				Toast.makeText(TrackDetailsActivity.this, R.string.no_segments, Toast.LENGTH_SHORT).show();
				return;
			}

			if (currentSegment < numberOfSegments) {

				currentSegment++;
				updateSegment(currentSegment);

			} else {

				currentSegment = 0;
				updateTrack();

			}

		}

	};

	/**
	 * Initialize activity
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		app = ((App) getApplicationContext());

		setContentView(R.layout.track_details);

		Bundle b = getIntent().getExtras();

		this.trackId = b.getLong("track_id", 0);

		this.getSegments();

		((Button) findViewById(R.id.prevSegment)).setOnClickListener(prevSegmentButtonClick);
		((Button) findViewById(R.id.nextSegment)).setOnClickListener(nextSegmentButtonClick);

	}

	/**
	 * 
	 */
	private void getSegments() {

		this.segments = Segments.getAll(app.getDatabase(), this.trackId);

		this.numberOfSegments = this.segments.size();

	}

	/**
	 * onResume event handler
	 */
	@Override
	protected void onResume() {

		this.updateTrack();

		super.onResume();

	}

	/**
	 * Update track details view
	 */
	protected void updateTrack() {

		Track track = Tracks.get(app.getDatabase(), this.trackId);

		((TextView) findViewById(R.id.title)).setText(track.getTitle());
		((TextView) findViewById(R.id.descr)).setText(track.getDescr());

		this.updateActivityDetails(track);

	}

	/**
	 * Update track details view
	 */
	protected void updateSegment(int segmentIndex) {

		long segmentId = segments.get(segmentIndex - 1).getId();

		Segment segment = Segments.get(app.getDatabase(), trackId, segmentId, segmentIndex);

		// update description text view
		((TextView) findViewById(R.id.descr)).setText("Segment: " + segmentIndex);

		this.updateSegmentDetails(segment);

	}

	private void updateSegmentDetails(Segment segment) {

		// measuring units
		String speedUnit = app.getPreferences().getString("speed_units", "kph");
		String speedUnitLocalized = Utils.getLocalizedSpeedUnit(TrackDetailsActivity.this, speedUnit);

		String distanceUnit = app.getPreferences().getString("distance_units", "km");

		String elevationUnit = app.getPreferences().getString("elevation_units", "m");
		String elevationUnitLocalized = Utils.getLocalizedElevationUnit(TrackDetailsActivity.this, elevationUnit);

		// localized distance unit depends on distance value
		String distanceUnitLocalized = Utils
				.getLocalizedDistanceUnit(TrackDetailsActivity.this, segment.getDistance(), distanceUnit);

		// average speed
		float averageSpeed = 0;

		long totalTime = segment.getTotalTime();
		if (totalTime != 0) {
			averageSpeed = segment.getDistance() / (totalTime / 1000f);
		}

		// moving average speed
		long movingTime = segment.getMovingTime();
		float averageMovingSpeed = 0;
		if (movingTime > 0 && segment.getDistance() > 0) {
			averageMovingSpeed = segment.getDistance() / (movingTime / 1000f);
		}

		float maxSpeed = segment.getMaxSpeed();

		// --------------------------------------------------------
		// distance
		// --------------------------------------------------------
		((TextView) findViewById(R.id.distance)).setText(Utils.formatDistance(segment.getDistance(), distanceUnit)
				+ " "
				+ distanceUnitLocalized);

		((TextView) findViewById(R.id.pointsCount)).setText(Integer.toString(segment.getPointsCount()));

		// --------------------------------------------------------
		// times
		// --------------------------------------------------------
		((TextView) findViewById(R.id.totalTime)).setText(Utils.formatInterval(
				segment.getTotalTime(), true));

		((TextView) findViewById(R.id.movingTime)).setText(Utils.formatInterval(
				segment.getMovingTime(), true));

		((TextView) findViewById(R.id.idleTime)).setText(Utils.formatInterval(
				segment.getTotalTime()
						- segment.getMovingTime(), true));

		((TextView) findViewById(R.id.startTime)).setText(DateFormat.format("yyyy-MM-dd k:mm",
				segment.getStartTime()));

		((TextView) findViewById(R.id.finishTime)).setText(DateFormat.format("yyyy-MM-dd k:mm",
				segment.getFinishTime()));

		// --------------------------------------------------------
		// speed
		// --------------------------------------------------------
		((TextView) findViewById(R.id.averageSpeed)).setText(Utils.formatSpeed(averageSpeed, speedUnit) + " "
				+ speedUnitLocalized);

		((TextView) findViewById(R.id.averageMovingSpeed)).setText(Utils.formatSpeed(averageMovingSpeed, speedUnit)
				+ " " + speedUnitLocalized);

		((TextView) findViewById(R.id.maxSpeed)).setText(Utils.formatSpeed(maxSpeed, speedUnit) + " "
				+ speedUnitLocalized);

		// --------------------------------------------------------
		// pace
		// --------------------------------------------------------
		if (averageSpeed != 0) {
			((TextView) findViewById(R.id.averagePace)).setText(Utils.formatPace(averageSpeed, speedUnit));
		}

		if (averageMovingSpeed != 0) {
			((TextView) findViewById(R.id.averageMovingPace)).setText(Utils.formatPace(averageMovingSpeed, speedUnit));
		}

		if (maxSpeed != 0) {
			((TextView) findViewById(R.id.maxPace)).setText(Utils.formatPace(maxSpeed, speedUnit));
		}

		// --------------------------------------------------------
		// elevation
		// --------------------------------------------------------
		((TextView) findViewById(R.id.maxElevation)).setText(Utils.formatElevation(
				segment.getMaxElevation(), elevationUnit)
				+ " " + elevationUnitLocalized);

		((TextView) findViewById(R.id.minElevation)).setText(Utils.formatElevation(
				segment.getMinElevation(), elevationUnit)
				+ " " + elevationUnitLocalized);

		((TextView) findViewById(R.id.elevationGain)).setText(Utils.formatElevation(
				segment.getElevationGain(), elevationUnit)
				+ " " + elevationUnitLocalized);

		((TextView) findViewById(R.id.elevationLoss)).setText(Utils.formatElevation(
				segment.getElevationLoss(), elevationUnit)
				+ " " + elevationUnitLocalized);

	}

	private void updateActivityDetails(Track track) {

		// measuring units
		String speedUnit = app.getPreferences().getString("speed_units", "kph");
		String speedUnitLocalized = Utils.getLocalizedSpeedUnit(TrackDetailsActivity.this, speedUnit);

		String distanceUnit = app.getPreferences().getString("distance_units", "km");

		String elevationUnit = app.getPreferences().getString("elevation_units", "m");
		String elevationUnitLocalized = Utils.getLocalizedElevationUnit(TrackDetailsActivity.this, elevationUnit);

		// total distance
		float distance = track.getDistance();

		// localized distance unit depends on distance value
		String distanceUnitLocalized = Utils
				.getLocalizedDistanceUnit(TrackDetailsActivity.this, distance, distanceUnit);

		// average speed
		float averageSpeed = 0;
		long totalTime = track.getTotalTime();
		if (totalTime != 0) {
			averageSpeed = distance / (totalTime / 1000f);
		}

		// moving average speed
		long movingTime = track.getMovingTime();
		float averageMovingSpeed = 0;
		if (movingTime > 0 && distance > 0) {
			averageMovingSpeed = distance / (movingTime / 1000f);
		}

		float maxSpeed = track.getMaxSpeed();

		// --------------------------------------------------------
		// distance
		// --------------------------------------------------------
		((TextView) findViewById(R.id.distance)).setText(Utils.formatDistance(distance, distanceUnit) + " "
				+ distanceUnitLocalized);

		((TextView) findViewById(R.id.pointsCount)).setText(Integer.toString(track.getPointsCount()));

		// --------------------------------------------------------
		// times
		// --------------------------------------------------------
		((TextView) findViewById(R.id.totalTime)).setText(Utils.formatInterval(
				track.getTotalTime(), true));

		((TextView) findViewById(R.id.movingTime)).setText(Utils.formatInterval(
				track.getMovingTime(), true));

		((TextView) findViewById(R.id.idleTime)).setText(Utils.formatInterval(
				track.getTotalTime() - track.getMovingTime(), true));

		((TextView) findViewById(R.id.startTime)).setText(DateFormat.format("yyyy-MM-dd k:mm",
				track.getStartTime()));

		((TextView) findViewById(R.id.finishTime)).setText(DateFormat.format("yyyy-MM-dd k:mm",
				track.getFinishTime()));

		// --------------------------------------------------------
		// speed
		// --------------------------------------------------------
		((TextView) findViewById(R.id.averageSpeed)).setText(Utils.formatSpeed(averageSpeed, speedUnit) + " "
				+ speedUnitLocalized);

		((TextView) findViewById(R.id.averageMovingSpeed)).setText(Utils.formatSpeed(averageMovingSpeed, speedUnit)
				+ " " + speedUnitLocalized);

		((TextView) findViewById(R.id.maxSpeed)).setText(Utils.formatSpeed(maxSpeed, speedUnit) + " "
				+ speedUnitLocalized);

		// --------------------------------------------------------
		// pace
		// --------------------------------------------------------
		if (averageSpeed != 0) {
			((TextView) findViewById(R.id.averagePace)).setText(Utils.formatPace(averageSpeed, speedUnit));
		}

		if (averageMovingSpeed != 0) {
			((TextView) findViewById(R.id.averageMovingPace)).setText(Utils.formatPace(averageMovingSpeed, speedUnit));
		}

		if (maxSpeed != 0) {
			((TextView) findViewById(R.id.maxPace)).setText(Utils.formatPace(maxSpeed, speedUnit));
		}

		// --------------------------------------------------------
		// elevation
		// --------------------------------------------------------
		((TextView) findViewById(R.id.maxElevation)).setText(Utils.formatElevation(
				track.getMaxElevation(), elevationUnit)
				+ " " + elevationUnitLocalized);

		((TextView) findViewById(R.id.minElevation)).setText(Utils.formatElevation(
				track.getMinElevation(), elevationUnit)
				+ " " + elevationUnitLocalized);

		((TextView) findViewById(R.id.elevationGain)).setText(Utils.formatElevation(
				track.getElevationGain(), elevationUnit)
				+ " " + elevationUnitLocalized);

		((TextView) findViewById(R.id.elevationLoss)).setText(Utils.formatElevation(
				track.getElevationLoss(), elevationUnit)
				+ " " + elevationUnitLocalized);

	}

	/**
	 * onCreateOptionsMenu handler
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.track_details_menu, menu);
		return true;
	}

	/**
	 * Make all changes to the menu before it loads
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		// MenuItem mi = menu.findItem(R.id.showSegments);

		return true;
	}

	/**
	 * Process main activity menu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Intent i;
		Bundle b;

		// Handle item selection
		switch (item.getItemId()) {

		case R.id.showOnMap:

			i = new Intent(this, MyMapActivity.class);

			// using Bundle to pass track id into new activity
			b = new Bundle();
			b.putInt("mode", Constants.SHOW_TRACK);
			b.putLong("track_id", this.trackId);
			b.putBoolean("display_info", true);

			i.putExtras(b);
			startActivity(i);

			return true;

		case R.id.showTrackChart:

			i = new Intent(this, TrackChartActivity.class);

			// using Bundle to pass track id into new activity
			b = new Bundle();
			b.putLong("track_id", this.trackId);

			i.putExtras(b);
			startActivity(i);

			return true;

		default:

			return super.onOptionsItemSelected(item);

		}

	}
}

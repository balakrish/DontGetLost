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
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.balakrish.gpstracker.R;
import com.balakrish.gpstracker.chart.ChartPoint;
import com.balakrish.gpstracker.chart.Series;
import com.balakrish.gpstracker.view.TrackChartView;

public class TrackChartActivity extends Activity {

	private long trackId;

	/**
	 * Reference to app object
	 */
	private App app;

	private Series elevationSeries;
	private Series speedSeries;

	private TrackChartView trackChartView;

	/**
	 * Initialize activity
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		app = ((App) getApplicationContext());

		setContentView(R.layout.track_chart);

		ViewGroup layout = (ViewGroup) findViewById(R.id.trackChartLayout);
		trackChartView = new TrackChartView(this);

		this.createSeries();

		trackChartView.setElevationSeries(elevationSeries);
		trackChartView.setSpeedSeries(speedSeries);

		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		layout.addView(trackChartView, params);

	}

	private void createSeries() {

		Bundle b = getIntent().getExtras();
		this.trackId = b.getLong("track_id", 0);

		// get track data
		String sql = "SELECT distance, elevation, speed FROM track_points WHERE track_id=" + this.trackId;

		Cursor cursor = app.getDatabase().rawQuery(sql, null);
		cursor.moveToFirst();

		elevationSeries = new Series(0xFFF2811D, getString(R.string.elevation));
		speedSeries = new Series(0xFFA2BF39, getString(R.string.speed));

		while (cursor.isAfterLast() == false) {

			float distance = cursor.getFloat(cursor.getColumnIndex("distance"));
			float elevation = cursor.getFloat(cursor.getColumnIndex("elevation"));
			float speed = cursor.getFloat(cursor.getColumnIndex("speed"));

			elevationSeries.addPoint(new ChartPoint(distance, elevation));
			speedSeries.addPoint(new ChartPoint(distance, speed));

			cursor.moveToNext();
		}

		cursor.close();

	}

}

package com.balakrish.gpstracker.view;
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
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.balakrish.gpstracker.chart.Series;
import com.balakrish.gpstracker.chart.TrackChart;

public class TrackChartView extends View {

	private Series elevationSeries;
	private Series speedSeries;

	public TrackChartView(Context context) {
		super(context);
	}

	public TrackChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		TrackChart trackChart = new TrackChart(this);
		trackChart.addSeries(elevationSeries);
		trackChart.addSeries(speedSeries);
		trackChart.draw(canvas);

	}

	public void setElevationSeries(Series es) {
		this.elevationSeries = es;
	}

	public void setSpeedSeries(Series ss) {
		this.speedSeries = ss;
	}

}

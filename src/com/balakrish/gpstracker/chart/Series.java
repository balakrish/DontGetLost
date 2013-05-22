package com.balakrish.gpstracker.chart;
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

public class Series {

	private ArrayList<ChartPoint> points;

	private float minValueX = Float.POSITIVE_INFINITY;
	private float maxValueX = Float.NEGATIVE_INFINITY;

	private float minValueY = Float.POSITIVE_INFINITY;
	private float maxValueY = Float.NEGATIVE_INFINITY;

	private int color;

	private String label;

	public Series(int c, String l) {

		this.color = c;

		this.label = l;

		this.points = new ArrayList<ChartPoint>();

	}

	public int getColor() {
		return color;
	}

	public String getLabel() {
		return label;
	}

	public void addPoint(ChartPoint p) {

		this.setMinMax(p);

		points.add(p);

	}

	private void setMinMax(ChartPoint p) {

		if (p.getValueX() < minValueX) {
			minValueX = p.getValueX();
		}

		if (p.getValueX() > maxValueX) {
			maxValueX = p.getValueX();
		}

		if (p.getValueY() < minValueY) {
			minValueY = p.getValueY();
		}

		if (p.getValueY() > maxValueY) {
			maxValueY = p.getValueY();
		}

	}

	public ChartPoint getPoint(int index) {

		return points.get(index);

	}

	public int getCount() {

		return points.size();

	}

	public float getRangeX() {

		return maxValueX - minValueX;

	}

	public float getRangeY() {

		return maxValueY - minValueY;

	}

	public float getMinX() {
		return minValueX;
	}

	public float getMaxX() {
		return maxValueX;
	}

	public float getMinY() {
		return minValueY;
	}

	public float getMaxY() {
		return maxValueY;
	}

}

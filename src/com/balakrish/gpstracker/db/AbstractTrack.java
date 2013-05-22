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

public class AbstractTrack {

	private long id;

	private float distance = 0;

	private long totalTime = 0;

	private long movingTime = 0;

	private float maxSpeed = 0;

	private double maxElevation = 0;

	private double minElevation = 0;

	private float elevationGain = 0;

	private float elevationLoss = 0;

	private long startTime = 0;

	private long finishTime = 0;

	private int pointsCount = 0;

	public AbstractTrack(Cursor cursor) {

		this.id = cursor.getLong(cursor.getColumnIndex("_id"));

		this.distance = cursor.getFloat(cursor.getColumnIndex("distance"));

		this.totalTime = cursor.getLong(cursor.getColumnIndex("total_time"));

		this.movingTime = cursor.getLong(cursor.getColumnIndex("moving_time"));

		this.maxSpeed = cursor.getFloat(cursor.getColumnIndex("max_speed"));

		this.maxElevation = cursor.getDouble(cursor.getColumnIndex("max_elevation"));

		this.minElevation = cursor.getDouble(cursor.getColumnIndex("min_elevation"));

		this.elevationGain = cursor.getFloat(cursor.getColumnIndex("elevation_gain"));

		this.elevationLoss = cursor.getFloat(cursor.getColumnIndex("elevation_loss"));

		this.startTime = cursor.getLong(cursor.getColumnIndex("start_time"));

		this.finishTime = cursor.getLong(cursor.getColumnIndex("finish_time"));

		if (cursor.getColumnIndex("points_count") != -1) {
			this.pointsCount = cursor.getInt(cursor.getColumnIndex("points_count"));
		}
	}

	/**
	 * Create empty track
	 */
	public AbstractTrack() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public long getMovingTime() {
		return movingTime;
	}

	public void setMovingTime(long movingTime) {
		this.movingTime = movingTime;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public double getMaxElevation() {
		return maxElevation;
	}

	public void setMaxElevation(double maxElevation) {
		this.maxElevation = maxElevation;
	}

	public double getMinElevation() {
		return minElevation;
	}

	public void setMinElevation(double minElevation) {
		this.minElevation = minElevation;
	}

	public float getElevationGain() {
		return elevationGain;
	}

	public void setElevationGain(float elevationGain) {
		this.elevationGain = elevationGain;
	}

	public float getElevationLoss() {
		return elevationLoss;
	}

	public void setElevationLoss(float elevationLoss) {
		this.elevationLoss = elevationLoss;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	public int getPointsCount() {
		return pointsCount;
	}

}

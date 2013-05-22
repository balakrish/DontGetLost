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
import android.location.Location;
import android.util.Log;

import com.balakrish.gpstracker.App;
import com.balakrish.gpstracker.Constants;
import com.balakrish.gpstracker.utils.Population;

//TODO : refactoring required

public abstract class AbstractTrackStats {

	/**
	 * Reference to application object for accessing db etc.
	 */
	protected App app;

	protected Context context;

	protected float distance = 0;

	protected float averageSpeed = 0;

	protected float acceleration = 0;

	protected float maxSpeed = 0;

	protected double oldElevation = Double.NEGATIVE_INFINITY;
	protected double currentElevation = Double.NEGATIVE_INFINITY;

	protected double minElevation = Double.POSITIVE_INFINITY;

	protected double maxElevation = Double.NEGATIVE_INFINITY;

	protected float elevationGain = 0;

	protected float elevationLoss = 0;

	protected Population speedPopulation;

	protected Population elevationPopulation;

	/**
	 * real time of the track start
	 */
	protected long trackTimeStart;

	public long getTrackTimeStart() {
		return trackTimeStart;
	}

	// --------------------------------------------------------------------------------------------------------

	public AbstractTrackStats(Context context) {

		this.context = context;

		app = ((App) context.getApplicationContext());

		this.trackTimeStart = (new Date()).getTime();

		this.speedPopulation = new Population(10);

		this.elevationPopulation = new Population(25);

	}

	/**
	 * Get average trip speed in meters per second
	 */
	public float getAverageSpeed() {

		if (this.getTotalTime() < 1000) { return 0; }

		return this.getDistance() / (this.getTotalTime() / 1000.0f);
	}

	/**
	 * @return Average moving speed in meters per second
	 */
	public float getAverageMovingSpeed() {

		if (this.getMovingTime() < 1000) { return 0; }

		return this.getDistance() / (this.getMovingTime() / 1000.0f);
	}

	/**
	 * Get total trip distance in meters
	 */
	public float getDistance() {
		return this.distance;
	}

	/**
	 * Get maximum trip speed
	 */
	public float getMaxSpeed() {
		return this.maxSpeed;
	}

	/**
	 * @return Elevation gain during track recording
	 */
	public float getElevationGain() {
		return this.elevationGain;
	}

	/**
	 * @return Elevation loss during track recording
	 */
	public float getElevationLoss() {
		return this.elevationLoss;
	}

	/**
	 * @return Max elevation during track recording
	 */
	public double getMaxElevation() {
		return this.maxElevation;
	}

	/**
	 * @return Min elevation during track recording
	 */
	public double getMinElevation() {
		return this.minElevation;
	}

	/**
	 * Get total trip time in milliseconds
	 */
	public long getTotalTime() {
		return this.currentSystemTime - this.startTime - this.totalPauseTime;
	}

	/**
	 * Get total moving trip time in milliseconds
	 */
	public long getMovingTime() {
		return this.getTotalTime() - this.totalIdleTime;
	}

	/**
	 * Calculating elevation gain/loss and min/max
	 * 
	 * @param location
	 */
	protected void processElevation(Location location) {

		// processing elevation data
		if (!location.hasAltitude()) {
			Log.v(Constants.TAG, "isSpeedValid: No elevation info");
			return;
		}

		// add current elevation to buffer
		this.elevationPopulation.addValue(location.getAltitude());

		this.currentElevation = this.elevationPopulation.getAverage();

		// max elevation
		if (this.maxElevation < this.currentElevation) {
			this.maxElevation = this.currentElevation;
		}
		// min elevation
		if (this.currentElevation < this.minElevation) {
			this.minElevation = this.currentElevation;
		}

		// on very first update old elevation is not initialized
		// so we can't calculate the difference
		if (this.oldElevation != Double.NEGATIVE_INFINITY) {

			if (this.elevationPopulation.isFull()) {
				// elevation gain/loss
				if (this.currentElevation > this.oldElevation) {
					this.elevationGain += this.currentElevation - this.oldElevation;
				} else {
					this.elevationLoss += this.oldElevation - this.currentElevation;
				}
			} else {

			}

		}

		this.oldElevation = this.currentElevation;

	}

	protected boolean isSpeedValid(Location lastLocation, Location currentLocation) {

		if (!lastLocation.hasSpeed() || !currentLocation.hasSpeed()) {
			// location does not have speed information
			Log.v(Constants.TAG, "isSpeedValid: No speed info");
			return false;
		}

		float currentSpeed = currentLocation.getSpeed();

		if (currentSpeed == 0) { return false; }

		// check from MyTracks
		if (Math.abs(currentSpeed - 128) < 1) { return false; }

		// calculate acceleration
		this.acceleration = 0;

		long timeInterval = Math.abs(currentLocation.getTime() - lastLocation.getTime()) / 1000;
		if (timeInterval > 0) {
			this.acceleration = Math.abs(lastLocation.getSpeed() - currentSpeed) / timeInterval;
		}

		// abnormal accelerations will not affect max speed
		if (this.acceleration > Constants.MAX_ACCELERATION) {
			Log.d(Constants.TAG, "this.acceleration > Constants.MAX_ACCELERATION");
			return false;
		}

		this.speedPopulation.addValue(currentSpeed);

		if (this.speedPopulation.isFull()) {

			double averageSpeed = this.speedPopulation.getAverage();

			if (currentSpeed > averageSpeed * 10) { return false; }

		} else {

			return false;

		}

		return true;

	}

	protected void processSpeed(float currentSpeed) {

		// calculating max speed
		if (currentSpeed > this.maxSpeed) {
			this.maxSpeed = currentSpeed;
		}

	}

	public void updateDistance(float d) {

		this.distance += d;

	}

	/*
	 * total idle time
	 */
	protected long totalIdleTime = 0;

	public void updateTotalIdleTime(long t) {
		this.totalIdleTime += t;
	}

	public long getTotalIdleTime() {
		return this.totalIdleTime;
	}

	/**
	 * total time recording was paused
	 */
	protected long totalPauseTime = 0;

	public void updateTotalPauseTime(long t) {
		this.totalPauseTime += t;
	}

	public long getTotalPauseTime() {
		return this.totalPauseTime;
	}

	protected long currentSystemTime = 0;

	public void setCurrentSystemTime(long cst) {
		this.currentSystemTime = cst;
	}

	protected long startTime = 0;

	public void setStartTime(long st) {
		this.startTime = st;
	}

	public long getStartTime() {
		return this.startTime;
	}

	public float getAcceleration() {
		return this.acceleration;
	}

}

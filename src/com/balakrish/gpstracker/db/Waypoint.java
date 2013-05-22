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
import java.util.Date;

import android.database.Cursor;
import android.location.Location;

public class Waypoint {

	private long id;

	private long trackId;

	private String title;

	private String descr;

	private double lat;

	private double lng;

	private float accuracy;

	private double elevation;

	private long time;

	/**
	 * Distance to current location
	 */
	private float distanceTo = 0;

	/**
	 * Simple constructor
	 */
	public Waypoint() {

	}

	public Waypoint(Cursor cursor) {

		this.id = cursor.getLong(cursor.getColumnIndex("_id"));

		this.trackId = cursor.getLong(cursor.getColumnIndex("track_id"));

		if (cursor.getColumnIndex("title") != -1) {
			this.title = cursor.getString(cursor.getColumnIndex("title"));
		}

		if (cursor.getColumnIndex("descr") != -1) {
			this.descr = cursor.getString(cursor.getColumnIndex("descr"));
		}

		this.accuracy = cursor.getFloat(cursor.getColumnIndex("accuracy"));

		this.elevation = cursor.getDouble(cursor.getColumnIndex("elevation"));

		this.lat = cursor.getInt(cursor.getColumnIndex("lat")) / 1E6;

		this.lng = cursor.getInt(cursor.getColumnIndex("lng")) / 1E6;

		this.time = cursor.getLong(cursor.getColumnIndex("time"));

	}

	/**
	 * Waypoint constructor
	 * 
	 * @param title
	 * @param lat
	 * @param lng
	 */
	public Waypoint(String title, double lat, double lng) {

		this.title = title;

		this.lat = lat;
		this.lng = lng;

		// setting current time
		this.time = (new Date()).getTime();

	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the track_id
	 */
	public long getTrackId() {
		return trackId;
	}

	/**
	 * @param track_id the track_id to set
	 */
	public void setTrack_id(long track_id) {
		this.trackId = track_id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the descr
	 */
	public String getDescr() {
		return descr;
	}

	/**
	 * @param descr the descr to set
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}

	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @return the lat in 1E6 format
	 */
	public int getLatE6() {
		return (int) (this.lat * 1E6);
	}

	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * @return the lng
	 */
	public double getLng() {
		return lng;
	}

	/**
	 * @return the lng in 1E6 format
	 */
	public int getLngE6() {
		return (int) (this.lng * 1E6);
	}

	/**
	 * @param lng the lng to set
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}

	/**
	 * @return the accuracy
	 */
	public float getAccuracy() {
		return accuracy;
	}

	/**
	 * @param accuracy the accuracy to set
	 */
	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}

	/**
	 * @return the elevation
	 */
	public double getElevation() {
		return elevation;
	}

	/**
	 * @param elevation the elevation to set
	 */
	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	public Location getLocation() {

		Location loc = new Location("gps");

		loc.setLatitude(lat);
		loc.setLongitude(lng);
		loc.setAltitude(elevation);

		return loc;

	}

	public void setDistanceTo(float d) {
		this.distanceTo = d;
	}

	public float getDistanceTo() {
		return this.distanceTo;
	}

}

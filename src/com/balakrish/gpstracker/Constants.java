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
public abstract class Constants {
	
	public static final String TAG = "AripucaTracker";

	public static final String APP_NAME = "AripucaTracker";

	public static final String DATABASE_FILE = APP_NAME + ".db";
	
	public static final String PATH_DATABASE = "database";
	public static final String PATH_WAYPOINTS = "waypoints";
	public static final String PATH_BACKUP = "backup";
	public static final String PATH_TRACKS = "tracks";
	public static final String PATH_LOGS = "logs";
	public static final String PATH_DEBUG = "debug";
	
	public static final int NOTIFICATION_TRACK_RECORDING = 1;
	public static final int NOTIFICATION_SCHEDULED_TRACK_RECORDING = 2;

	/**
	 * map modes
	 */
	public static final int SHOW_WAYPOINT = 0;
	public static final int SHOW_ALL_WAYPOINTS = 1;
	public static final int SHOW_TRACK = 2;
	public static final int SHOW_SCHEDULED_TRACK = 3;

	/**
	 * map view modes
	 */
	public static final int MAP_STREET = 0;
	public static final int MAP_SATELLITE = 1;

	/**
	 * Minimum distance between 2 consecutive track points
	 */
	public static final int MIN_DISTANCE = 5; // meters

	public static final float MAX_ACCELERATION = 19.6F;

	public static final float MIN_SPEED = 0.224F; // meters per second

	public static final int SEGMENT_NONE = 0;
	public static final int SEGMENT_PAUSE_RESUME = 1;
	public static final int SEGMENT_DISTANCE = 2;
	public static final int SEGMENT_TIME = 3;
	public static final int SEGMENT_CUSTOM_1 = 4;
	public static final int SEGMENT_CUSTOM_2 = 5;

	public static final int ORIENTATION_PORTRAIT = 1;
	public static final int ORIENTATION_LANDSCAPE = 2;
	public static final int ORIENTATION_REVERSE_LANDSCAPE = 10;
	public static final int ORIENTATION_REVERSE_PORTRAIT = 20;

	/**
	 * location providers
	 */
	public static final int GPS_PROVIDER = 0;
	public static final int GPS_PROVIDER_LAST = 1;
	public static final int NETWORK_PROVIDER = 2;
	public static final int NETWORK_PROVIDER_LAST = 3;

	/**
	 * Custom dialogs
	 */
	public static final int QUICK_HELP_DIALOG_ID = 1;

	/**
	 * intent actions
	 */
	public static final String ACTION_NEXT_LOCATION_REQUEST = "com.balakrish.gpstracker.ACTION_NEXT_LOCATION_REQUEST";
	public static final String ACTION_NEXT_TIME_LIMIT_CHECK = "com.balakrish.gpstracker.ACTION_NEXT_TIME_LIMIT_CHECK";
	public static final String ACTION_START_SENSOR_UPDATES = "com.balakrish.gpstracker.ACTION_START_SENSOR_UPDATES";
	public static final String ACTION_LOCATION_UPDATES = "com.balakrish.gpstracker.ACTION_LOCATION_UPDATES";
	public static final String ACTION_SCHEDULED_LOCATION_UPDATES = "com.balakrish.gpstracker.ACTION_SCHEDULED_LOCATION_UPDATES";
	public static final String ACTION_COMPASS_UPDATES = "com.balakrish.gpstracker.ACTION_COMPASS_UPDATES";
	
	public static final int ACTIVITY_TRACK = 0;
	public static final int ACTIVITY_SCHEDULED_TRACK = 1;
	
	public static final int TRACK_RECORDING_STOPPED = 0;
	public static final int TRACK_RECORDING_IN_PROGRESS = 1;
	
	

}
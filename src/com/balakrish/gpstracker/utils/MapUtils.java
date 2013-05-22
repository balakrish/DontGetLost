package com.balakrish.gpstracker.utils;
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
import android.hardware.GeomagneticField;
import android.location.Location;

import com.google.android.maps.GeoPoint;

public class MapUtils {

	/**
	 * Get current magnetic declination
	 * 
	 * @param location
	 * @param timestamp
	 * @return
	 */
	public static float getDeclination(Location location, long timestamp) {

		GeomagneticField field = new GeomagneticField((float) location.getLatitude(), (float) location.getLongitude(),
				(float) location.getAltitude(), timestamp);

		return field.getDeclination();
	}

	/**
	 * Convert Location object to GeoPoint
	 * 
	 * @param location
	 * @return
	 */
	public static GeoPoint locationToGeoPoint(Location location) {
		return new GeoPoint((int) (location.getLatitude() * 1E6),
				(int) (location.getLongitude() * 1E6));
	}

}

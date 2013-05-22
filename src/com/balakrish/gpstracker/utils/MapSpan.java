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
import com.google.android.maps.GeoPoint;

public class MapSpan {

	private int minLat = (int) (180 * 1E6);
	private int maxLat = (int) (-180 * 1E6);
	private int minLng = (int) (180 * 1E6);
	private int maxLng = (int) (-180 * 1E6);
	
	public MapSpan() {
		
	}
	
	/**
	 * @return the minLat
	 */
	public int getMinLat() {
		return minLat;
	}

	/**
	 * @param minLat the minLat to set
	 */
	public void setMinLat(int minLat) {
		this.minLat = minLat;
	}

	/**
	 * @return the maxLat
	 */
	public int getMaxLat() {
		return maxLat;
	}

	/**
	 * @param maxLat the maxLat to set
	 */
	public void setMaxLat(int maxLat) {
		this.maxLat = maxLat;
	}

	/**
	 * @return the minLng
	 */
	public int getMinLng() {
		return minLng;
	}

	/**
	 * @param minLng the minLng to set
	 */
	public void setMinLng(int minLng) {
		this.minLng = minLng;
	}

	/**
	 * @return the maxLng
	 */
	public int getMaxLng() {
		return maxLng;
	}

	/**
	 * @param maxLng the maxLng to set
	 */
	public void setMaxLng(int maxLng) {
		this.maxLng = maxLng;
	}

	public int getLatRange() {
		return maxLat - minLat;
	}

	public int getLngRange() {
		return maxLng - minLng;
	}
	
	/**
	 * Calculates min and max coordinates range
	 * 
	 * @param lat
	 * @param lng
	 */
	public void updateMapSpan(int lat, int lng) {

		if (lat < minLat) {
			minLat = lat;
		}
		if (lat > maxLat) {
			maxLat = lat;
		}
		if (lng < minLng) {
			minLng = lng;
		}
		if (lng > maxLng) {
			maxLng = lng;
		}

	}
	
	/**
	 * Returns the central point of the span
	 * 
	 * @return GeoPoint
	 */
	public GeoPoint getCenter() {
		
		return new GeoPoint((maxLat + minLat) / 2, (maxLng + minLng) / 2);
		
	}
	
}

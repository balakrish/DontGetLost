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
public class Population {

	private double[] values;

	private int nextIndex = 0;

	private boolean isFull = false;

	public Population(int size) {

		values = new double[size];

	}

	public void addValue(double value) {

		if (nextIndex == values.length) {
			nextIndex = 0;
		}

		values[nextIndex] = value;

		nextIndex++;
		if (nextIndex == values.length) {
			isFull = true;
		}

	}

	public boolean isFull() {
		return isFull;
	}

	public double getAverage() {

		int totalValues = isFull ? values.length : nextIndex;
		if (totalValues == 0) { return 0; }

		double sum = 0;
		for (int i = 0; i < totalValues; i++) {
			sum += values[i];
		}

		return sum / totalValues;
	}

	public void reset() {
		nextIndex = 0;
		isFull = false;
	}

}

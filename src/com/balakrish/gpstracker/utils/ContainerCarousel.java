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
import java.util.ArrayList;
import java.util.List;

public abstract class ContainerCarousel {

	protected int current = 0;

	protected List<Integer> containers;

	protected int resourceId;

	public ContainerCarousel() {

		containers = new ArrayList<Integer>();

		initialize();

	}

	protected abstract void initialize();

	public int getResourceId() {

		return resourceId;

	}

	public int getCurrentContainer() {
		return containers.get(current);
	}

	public int getNextContainer() {
		if (current < containers.size() - 1) {
			current++;
		} else {
			current = 0;
		}
		return containers.get(current);
	}

	public int getCurrentContainerId() {

		return current;

	}

	public void setCurrentContainerId(int newCurrent) {
		if (newCurrent > containers.size() - 1) {
			current = 0;
		} else {
			current = newCurrent;
		}
	}

}

package com.balakrish.gpstracker.io;
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

import com.balakrish.gpstracker.utils.Utils;

/**
 * Export to KML AsyncTask class
 */
public class TrackKmlExportTask extends TrackExportTask {

	public TrackKmlExportTask(Context c) {
		super(c);

		extension = "kml";
	}

	@Override
	protected void writeHeader() {

		pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		pw.print("<kml");
		pw.print(" xmlns=\"http://earth.google.com/kml/2.0\"");
		pw.println(" xmlns:atom=\"http://www.w3.org/2005/Atom\">");
		pw.println("<Document>");
		pw.println("<atom:author><atom:name>Aripuca GPS Tracker for Android" + "</atom:name></atom:author>");
		pw.println("<name>" + tCursor.getString(tCursor.getColumnIndex("title")) + "</name>");
		pw.println("<description>" + tCursor.getString(tCursor.getColumnIndex("descr")) + "</description>");

		// track start
		pw.println("<Placemark>");
		pw.println("<name>" + tCursor.getString(tCursor.getColumnIndex("title")) + "</name>");
		pw.println("<description>" + tCursor.getString(tCursor.getColumnIndex("descr")) + "</description>");
		pw.println("<MultiGeometry><LineString><coordinates>");

	}

	@Override
	protected void writeTrackPoint() {

		if (!segmentOpen) {
			prevSegmentIndex = tpCursor.getInt(tpCursor.getColumnIndex("segment_index"));
			segmentOpen = true;
		}

		if (prevSegmentIndex != tpCursor.getInt(tpCursor.getColumnIndex("segment_index"))) {
			pw.println("</coordinates></LineString>");
			pw.println("<LineString><coordinates>");
			segmentOpen = false;
		}

		String lat = Utils.formatCoord(tpCursor.getInt(tpCursor.getColumnIndex("lat")) / 1E6);
		String lng = Utils.formatCoord(tpCursor.getInt(tpCursor.getColumnIndex("lng")) / 1E6);

		pw.println(lng + "," + lat + ","
				+ Utils.formatNumberUS(tpCursor.getFloat(tpCursor.getColumnIndex("elevation")), 1) + " ");

	}

	@Override
	protected void writeFooter() {

		// end track
		pw.println("</coordinates></LineString></MultiGeometry>");
		pw.println("</Placemark>");

		// footer
		pw.println("</Document>");
		pw.println("</kml>");

	}

}

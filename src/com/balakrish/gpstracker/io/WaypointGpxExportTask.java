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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;

import com.balakrish.gpstracker.utils.Utils;

public class WaypointGpxExportTask extends TrackExportTask {

	private String filename;

	public WaypointGpxExportTask(Context c, String fn) {
		super(c);

		extension = "gpx";

		filename = fn;

	}

	@Override
	protected void prepareWriter() throws IOException {

		// create file named as track title on sd card
		File outputFolder = new File(app.getAppDir() + "/waypoints");

		file = new File(outputFolder, filename + "." + extension);

		if (!file.exists()) {
			file.createNewFile();
		}

		// overwrite existing file
		pw = new PrintWriter(new FileWriter(file, false));

	}

	@Override
	protected void prepareCursors() {

		// track cursor
		String sql = "SELECT * FROM waypoints;";
		tpCursor = app.getDatabase().rawQuery(sql, null);
		tpCursor.moveToFirst();

	}

	@Override
	protected void writeHeader() {

		String todayDate = (new SimpleDateFormat("yyyy-MM-dd")).format((new Date()).getTime());

		// write gpx header
		pw.format("<?xml version=\"1.0\" encoding=\"%s\" standalone=\"yes\"?>\n", Charset.defaultCharset().name());
		pw.println("<gpx");
		pw.println(" version=\"1.1\"");
		pw.println(" creator=\"AripucaTracker for Android\"");
		pw.println(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		pw.println(" xmlns=\"http://www.topografix.com/GPX/1/1\"");
		pw.print(" xmlns:topografix=\"http://www.topografix.com/GPX/Private/TopoGrafix/0/1\"");
		pw.print(" xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 ");
		pw.print("http://www.topografix.com/GPX/1/1/gpx.xsd ");
		pw.print("http://www.topografix.com/GPX/Private/TopoGrafix/0/1 ");
		pw.println("http://www.topografix.com/GPX/Private/TopoGrafix/0/1/topografix.xsd\">");

		pw.println("<time>" + todayDate + "</time>");

	}

	@Override
	protected void writeTrackPoint() {

		String wpTime = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")).format(tpCursor.getLong(tpCursor
				.getColumnIndex("time")));

		String lat = Utils.formatCoord(tpCursor.getInt(tpCursor.getColumnIndex("lat")) / 1E6);
		String lng = Utils.formatCoord(tpCursor.getInt(tpCursor.getColumnIndex("lng")) / 1E6);

		pw.println("<wpt lat=\"" + lat + "\" lon=\"" + lng + "\">");
		pw.println("<ele>" + Utils.formatNumberUS(tpCursor.getFloat(tpCursor.getColumnIndex("elevation")), 1)
				+ "</ele>");
		pw.println("<time>" + wpTime + "</time>");
		pw.println("<name><![CDATA[" + tpCursor.getString(tpCursor.getColumnIndex("title")) + "]]></name>");
		pw.println("<desc><![CDATA[" + tpCursor.getString(tpCursor.getColumnIndex("descr")) + "]]></desc>");
		// pw.println("<type>" + + "</type>");
		pw.println("</wpt>");
		

	}

	@Override
	protected void writeFooter() {

		// footer
		pw.println("</gpx>");

	}

}

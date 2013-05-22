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
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.balakrish.gpstracker.R;
import com.balakrish.gpstracker.db.Tracks;

/**
 * Scheduled tracks list activity
 */
public class ScheduledTracksListActivity extends AbstractTracksListActivity {

	// instance initialization block
	{
		this.listItemResourceId = R.layout.scheduled_track_list_item;
		this.mapMode = Constants.SHOW_SCHEDULED_TRACK;
	}

	@Override
	public void deleteAllTracks() {
		Tracks.deleteAll(app.getDatabase(), Constants.ACTIVITY_SCHEDULED_TRACK);
	}

	/**
	 * Start the track details activity
	 * 
	 * @param id
	 *            Track id
	 */
	@Override
	protected void viewTrackDetails(long id) {

		Intent intent = new Intent(this, TrackpointsListActivity.class);

		// using Bundle to pass track id into new activity
		Bundle bundle = new Bundle();
		bundle.putLong("track_id", id);

		intent.putExtras(bundle);

		startActivity(intent);

	}

	@Override
	protected void setQuery() {

		if (app.getPreferences().getBoolean("debug_on", false)) {
			// in debug mode show all track including being recorded ones
			sqlSelectAllTracks = "SELECT * FROM tracks WHERE activity=" + Constants.ACTIVITY_SCHEDULED_TRACK;
		} else {
			sqlSelectAllTracks = "SELECT * FROM tracks WHERE recording=0 AND activity="
					+ Constants.ACTIVITY_SCHEDULED_TRACK;
		}

	}

	protected void addTrackDetailsMenu(Menu menu) {
		
		menu.add(Menu.NONE, 1, 1, R.string.show_track_points);
		
	}
	
}

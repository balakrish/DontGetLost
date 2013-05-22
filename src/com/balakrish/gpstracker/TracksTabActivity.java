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
import com.balakrish.gpstracker.R;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class TracksTabActivity extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.tracks_tabs);

		Resources res = getResources();
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		
		// creating real-time tracks tab
		Intent intent1 = new Intent().setClass(this, TracksListActivity.class);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("tracks")
				.setIndicator(getString(R.string.tracks), res.getDrawable(R.drawable.ic_tab_tracks)).setContent(intent1);

		tabHost.addTab(spec);

		// scheduled tracks tab
		Intent intent2 = new Intent().setClass(this, ScheduledTracksListActivity.class);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost
				.newTabSpec("scheduled_tracks")
				.setIndicator(getString(R.string.scheduled_tracks), res.getDrawable(R.drawable.ic_tab_scheduled_tracks))
				.setContent(intent2);

		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
	}

}

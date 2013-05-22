package com.balakrish.gpstracker.service;
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
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.balakrish.gpstracker.Constants;
import com.balakrish.gpstracker.utils.AppLog;

public class AppServiceConnection {

	private Context context;

	/**
	 * GPS service connection
	 */
	private AppService appService;

	private Runnable runnable;

	/**
	 * ServiceConnection object
	 */
	private final ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {

			AppLog.d(context, "ServiceConnection: onServiceConnected " + this.toString());

			appService = ((AppService.LocalBinder) service).getService();

			// executing activity's callback
			new Handler().post(runnable);
		}

		@Override
		public void onServiceDisconnected(ComponentName className) {
			AppLog.d(context, "ServiceConnection: onServiceDisconnected");
		}

	};

	public AppServiceConnection(Context c, Runnable r) {

		this.context = c;

		this.runnable = r;

	}

	public void bindAppService() {

		if (appService != null) {
			// return;
		}

		Log.v(Constants.TAG, "AppServiceConnection: bindAppService");

		Intent i = new Intent(context, AppService.class);
		if (!context.bindService(i, serviceConnection, 0)) {
			Toast.makeText(context, "Can't connect to GPS service", Toast.LENGTH_SHORT).show();
			AppLog.d(context, "bindAppService: Can't connect to GPS service");
		}

	}

	public void unbindAppService() {

		AppLog.d(context, "AppServiceConnection: unbindAppService");

		// detach our existing connection
		context.unbindService(serviceConnection);

		appService = null;

	}

	public AppService getService() {
		return appService;
	}

	/**
	 * start application service
	 */
	public void startService() {

		Intent i = new Intent(context, AppService.class);
		context.startService(i);

	}

	/**
	 * stop application service
	 */
	public void stopService() {

		Intent i = new Intent(context, AppService.class);
		context.stopService(i);

	}

}

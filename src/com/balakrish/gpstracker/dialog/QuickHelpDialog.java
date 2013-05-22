package com.balakrish.gpstracker.dialog;
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
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.balakrish.gpstracker.App;
import com.balakrish.gpstracker.R;

/**
 * Quick help custom dialog Shows random help advice every time it's shown
 */
public class QuickHelpDialog extends Dialog {

	private Context context;

	/**
	 * Reference to Application object
	 */
	private App app;

	public QuickHelpDialog(Context context) {

		super(context);

		this.context = context;

		app = ((App) context.getApplicationContext());

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.quick_help_dialog);
		setTitle(R.string.do_you_know);

		LayoutParams params = getWindow().getAttributes();

		// params.height = LayoutParams.FILL_PARENT;
//		params.width = android.view.ViewGroup.LayoutParams.FILL_PARENT;

		getWindow().setAttributes(params);

		// setting "showNextTime" initial value
		CheckBox checkBox = (CheckBox) findViewById(R.id.showNextTime);
		checkBox.setChecked(app.getPreferences().getBoolean("quick_help", true));

		TextView text = (TextView) findViewById(R.id.helpText);
		text.setText(getNextHelpAdvice());

		// set close button event listener
		Button closeButton = (Button) findViewById(R.id.closeButton);
		closeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// check dontShowNextTime checkbox
				CheckBox checkBox = (CheckBox) findViewById(R.id.showNextTime);
				if (!checkBox.isChecked()) {

					if (app.getPreferences().getBoolean("quick_help", true) == true) {
						SharedPreferences.Editor editor = app.getPreferences().edit();
						editor.putBoolean("quick_help", false);
						editor.commit();
					}

				} else {

					if (app.getPreferences().getBoolean("quick_help", true) == false) {
						SharedPreferences.Editor editor = app.getPreferences().edit();
						editor.putBoolean("quick_help", true);
						editor.commit();
					}

				}

				dismiss();
			}
		});

		// set next button event listener
		Button nextButton = (Button) findViewById(R.id.nextButton);
		nextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// show next help advice
				TextView text = (TextView) findViewById(R.id.helpText);
				text.setText(getNextHelpAdvice());

			}
		});

	}

	private String getNextHelpAdvice() {

		String[] items = context.getResources().getStringArray(R.array.quick_help);

		int id = (int) Math.round(Math.random() * (items.length - 1));

		return items[id];

	}

}

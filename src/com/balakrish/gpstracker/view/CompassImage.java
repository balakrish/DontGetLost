package com.balakrish.gpstracker.view;
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
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CompassImage extends ImageView {

	protected float angle = 0;

	public CompassImage(Context context) {
		super(context);
	}

	public CompassImage(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		canvas.rotate(angle, this.getMeasuredWidth() / 2, this.getMeasuredHeight() / 2);
		super.onDraw(canvas);

	}

	public void setAngle(float a) {
		angle = a;
	}

	public float getAngle() {
		return angle;
	}

	@Override
	public boolean isInEditMode() {
		return false;
	}

}

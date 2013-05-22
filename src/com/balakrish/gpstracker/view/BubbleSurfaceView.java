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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.balakrish.gpstracker.R;

public class BubbleSurfaceView extends SurfaceView implements Runnable {

	private Thread thread;

	private SurfaceHolder holder;

	private boolean isRunning = false;

	private Bitmap bubble, bubbleCircle, bubbleCircleRed, bubbleCircleGreen;

	float azimuth, roll, pitch;

	float x, y;

	public BubbleSurfaceView(Context context) {
		super(context);
		init();
	}

	public BubbleSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public BubbleSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * initialization
	 * 
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 */
	private void init() {

		holder = getHolder();

		this.setZOrderOnTop(true);

		// making surface transparent
		holder.setFormat(PixelFormat.TRANSPARENT);

		bubble = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
		bubbleCircle = BitmapFactory.decodeResource(getResources(), R.drawable.bubble_circle);
		bubbleCircleRed = BitmapFactory.decodeResource(getResources(), R.drawable.bubble_circle_red);
		bubbleCircleGreen = BitmapFactory.decodeResource(getResources(), R.drawable.bubble_circle_green);

		// center of the circle
		x = y = this.getWidth() / 2;

	}

	/**
	 * sets sensor data from activity
	 * 
	 * @param a
	 * @param r
	 * @param p
	 */
	public void setSensorData(float a, float r, float p) {
		this.azimuth = a;
		this.roll = r;
		this.pitch = p;
	}

	/**
	 * thread's run method
	 */
	@Override
	public void run() {

		while (isRunning) {

			if (!holder.getSurface().isValid()) {
				continue;
			}

			try {
				Thread.sleep(100);
			} catch (Exception e) {

			}

			Canvas canvas = holder.lockCanvas();

			// scale
			float scaleRoll = this.getWidth() * 0.7F / 90F;
			float scalePitch = this.getHeight() * 0.7F / 90F;

			synchronized (this) {
				// controlling the circle bounds
				while (Math.sqrt(this.roll * this.roll + this.pitch * this.pitch) > 26) {

					if (this.roll < 0)
						this.roll += 0.01;
					else
						this.roll -= 0.01;

					if (this.pitch < 0)
						this.pitch += 0.01;
					else
						this.pitch -= 0.01;
				}
				// top left corner of the bubble
				x = this.roll * scaleRoll + this.getWidth() / 2 - bubble.getWidth() / 2;
				y = this.pitch * scalePitch + this.getHeight() / 2 - bubble.getHeight() / 2;
			}

			// clearing canvas
			canvas.drawColor(0, PorterDuff.Mode.CLEAR);

			// distance between center of the circle and center of the bubble
			float dist = (float) Math.sqrt(Math.pow(x + bubble.getWidth() / 2 - (this.getWidth() / 2), 2)
					+ Math.pow(y + bubble.getHeight() / 2 - (this.getHeight() / 2), 2));

			if (dist + bubble.getWidth() / 2 > this.getHeight() / 3) {
				// drawing bubble circle
				canvas.drawBitmap(bubbleCircleRed, 0, 0, null);
			} else {
				if (dist + bubble.getWidth() / 2 > this.getHeight() / 4) {
					// drawing bubble circle
					canvas.drawBitmap(bubbleCircleGreen, 0, 0, null);
				} else {
					// drawing bubble circle
					canvas.drawBitmap(bubbleCircle, 0, 0, null);
				}
			}

			// drawing bubble last
			canvas.drawBitmap(bubble, x, y, null);

			// drawing bubble cover
			// canvas.drawBitmap(bubbleCover, 0, 0, null);

			holder.unlockCanvasAndPost(canvas);

		}

	}

	/**
	 * pause drawing to the surface
	 */
	public void pause() {

		isRunning = false;

		while (true) {
			try {
				thread.join();
			} catch (InterruptedException e) {

			}
			break;
		}

		thread = null;
	}

	/**
	 * resume drawing
	 */
	public void resume() {

		isRunning = true;

		thread = new Thread(this);
		thread.start();

	}

}
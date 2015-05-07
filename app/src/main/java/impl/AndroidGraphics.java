package impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import framework.Graphics;

public class AndroidGraphics implements Graphics
{
	Bitmap frameBuffer;
	Canvas canvas;

	public AndroidGraphics(Bitmap frameBuffer)
	{
		this.frameBuffer = frameBuffer;
		this.canvas = new Canvas(frameBuffer);
	}

	@Override
	public Canvas getCanvas()
	{
		return canvas;
	}

	@Override
	public int getWidth()
	{
		return canvas.getWidth();
	}

	@Override
	public int getHeight()
	{
		return canvas.getHeight();
	}
}

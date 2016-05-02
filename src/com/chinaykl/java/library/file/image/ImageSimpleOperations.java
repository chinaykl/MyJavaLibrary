package com.chinaykl.java.library.file.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageSimpleOperations extends ImageBase {
	private BufferedImage mBufferedImage = null;
	private Graphics mGraphics = null;

	public ImageSimpleOperations(String path) {
		super(path);
		super.open();
	}

	static final public int COLOR_RED = 1;
	static final public int COLOR_GREEN = 2;
	static final public int COLOR_BLUE = 3;

	public void setColor(int colorNum) {
		Color color = null;
		switch (colorNum) {
		case COLOR_RED:
			color = Color.RED;
			break;
		case COLOR_GREEN:
			color = Color.GREEN;
			break;
		case COLOR_BLUE:
			color = Color.BLUE;
			break;
		default:
			color = Color.RED;
			break;
		}
		mGraphics.setColor(color);
	}

	public void drawRect(int x, int y, int width, int height) {
		mGraphics.drawRect(x, y, width, height);
	}

	public int read() {
		mBufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		mGraphics = mBufferedImage.getGraphics();
		mGraphics.drawImage(mImage, 0, 0, getWidth(), getHeight(), null);
		return 0;
	}

	static final public int IMAGE_WRITE_ERROR = -2;

	public int write(String path, String format) {
		try {
			ImageIO.write(mBufferedImage, format, new File(path));
		} catch (IOException e) {
			return IMAGE_WRITE_ERROR;
		}
		return 0;
	}
}

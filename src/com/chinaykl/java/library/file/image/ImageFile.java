package com.chinaykl.java.library.file.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.chinaykl.java.library.file.SimpleFile;

public class ImageFile extends SimpleFile {

	public static final int IMAGE_OPEN_ERROR = SIMPLEFILE_ERROR_CODE - 1;
	public static final int IMAGE_WRITE_ERROR = IMAGE_OPEN_ERROR - 1;
	private static final String DEFAULT_SAVA_FORMAT = "png";

	private Image mImage = null;
	private BufferedImage mBufferedImage = null;
	private Graphics mGraphics = null;
	private String mSaveFormat = null;

	public ImageFile(String path) {
		super(path, FILE_MODE_R);
	}

	public int open() {
		int fileState = super.open();
		if (fileState != 0) {
			return fileState;
		}

		try {
			File file = new File(getPath());
			mImage = ImageIO.read(file);
		} catch (IOException e) {
			return IMAGE_OPEN_ERROR;
		}

		mBufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		mGraphics = mBufferedImage.getGraphics();
		mGraphics.drawImage(mImage, 0, 0, getWidth(), getHeight(), null);

		mSaveFormat = DEFAULT_SAVA_FORMAT;

		return 0;
	}

	public void setPaintColor(int r, int g, int b, int a) {
		Color color = new Color(r, g, b, a);
		mGraphics.setColor(color);
	}

	public void drawRect(int x, int y, int width, int height) {
		mGraphics.drawRect(x, y, width, height);
	}

	public int getHeight() {
		return mImage == null ? 0 : mImage.getHeight(null);
	}

	public int getWidth() {
		return mImage == null ? 0 : mImage.getWidth(null);
	}

	public int closeAsNewImage(String path) {
		try {
			ImageIO.write(mBufferedImage, mSaveFormat, new File(path));
		} catch (IOException e) {
			return IMAGE_WRITE_ERROR;
		}

		int fileState = super.close();
		return fileState;
	}

	public int close() {
		return closeAsNewImage(getPath());
	}

}

package com.chinaykl.java.library.file.image;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageBase {
	private String mPath = null;
	protected Image mImage = null;

	public ImageBase(String path) {
		mPath = path;
	}

	static final public int IMAGE_OPEN_ERROR = -1;

	public int open() {
		File file = new File(mPath);
		try {
			mImage = ImageIO.read(file);
		} catch (IOException e) {
			System.out.println("Image open failed");
			return IMAGE_OPEN_ERROR;
		}

		return 0;
	}

	public int getHeight() {
		return mImage == null ? 0 : mImage.getHeight(null);
	}

	public int getWidth() {
		return mImage == null ? 0 : mImage.getWidth(null);
	}

	public String getPath() {
		return mPath;
	}

}

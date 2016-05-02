package com.chinaykl.test.java.library.file.image;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.chinaykl.java.library.file.image.ImageSimpleOperations;

public class TestImage {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		ImageSimpleOperations image = new ImageSimpleOperations("C:\\Users\\zero\\Desktop\\result\\images\\0.png");
		image.read();
		image.setColor(ImageSimpleOperations.COLOR_RED);
		image.drawRect(0, 0, 100, 100);
		image.write("C:\\Users\\zero\\Desktop\\result\\images\\0_1111.png", "png");
	}

}

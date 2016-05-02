package com.chinaykl.test.java.library.file.xml;

import static org.junit.Assert.*;

import javax.management.loading.MLet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.chinaykl.java.library.file.xml.XmlSimpleOperations;
import com.sun.org.apache.xerces.internal.impl.dtd.models.CMLeaf;

public class TestXml {

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
		XmlSimpleOperations xml = new XmlSimpleOperations("C:\\Users\\zero\\Desktop\\result\\layouts\\0.xml");
		xml.read();
		xml.goInTo(0);
		System.out.println(xml.getValue("hashcode"));
	}

}

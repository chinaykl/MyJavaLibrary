package com.chinaykl.test.java.library.file.db;

import static org.junit.Assert.*;

import java.awt.print.Printable;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.chinaykl.java.library.file.db.SqliteSimpliOperations;

import sun.security.krb5.internal.crypto.DesCbcCrcEType;

public class TestDb {

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
		SqliteSimpliOperations dbSimpliOperations = new SqliteSimpliOperations(
				"C:\\Users\\zero\\Desktop\\result\\databases\\traversal.db");
		dbSimpliOperations.read();
		int size = dbSimpliOperations.getSize("ActionInfo");
		for (int i = 0; i < size; i++) {
			System.out.println("desc = " + dbSimpliOperations.getString("ActionInfo", i, "desc"));
			System.out.println("step = " + dbSimpliOperations.getInt("ActionInfo", i, "step"));
		}
		dbSimpliOperations.close();
	}

}

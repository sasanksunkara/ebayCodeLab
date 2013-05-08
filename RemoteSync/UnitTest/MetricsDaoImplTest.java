package com.ebay.remotesync.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Timestamp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MetricsDaoImplTest {

	private MetricsDaoImpl myImpl;
	private String node;
	private Timestamp ts;
	private int coreId;
	private double utilization;

	
	@Before
	public void setUp() throws Exception {
		node = "xxx";
		myImpl = new MetricsDaoImpl();
		ts = new Timestamp(100000);
		coreId = 1;
		utilization = 80;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetCurrNode() {
		myImpl.setCurrNode(node);
		assert(myImpl.getCurrNode().equals(node));
	}
	@Test
	public void testInsertCpuInfo() {
		
		myImpl.setCurrNode(node);
		try {
			 myImpl.insertCpuInfo(coreId,utilization,ts); // checking for core id 1 and some utilization 90
			 assert(myImpl.getCpuInfo(node,coreId,ts)==utilization);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	


}

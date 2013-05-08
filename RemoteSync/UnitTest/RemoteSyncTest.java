package com.ebay.remotesync.services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ebay.remotesync.services.RemoteSync;

public class RemoteSyncTest {

	RemoteSync remoteSync ;
	JSONObject inputNodes;
	@Before
	public void setUp() throws Exception {
		remoteSync = new RemoteSync();
		inputNodes = new JSONObject();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetOperationStatus() {
		String status = remoteSync.getOperationStatus();
		assert(status!=null);
	}

	@Test
	public void testSubmitRequest() {
		String status = remoteSync.submitRequest(inputNodes);
		assert(status.equals("SUCCESS"));
	}

}

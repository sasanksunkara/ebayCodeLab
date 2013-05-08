package com.ebay.remotesync.dao;

import java.sql.Timestamp;

public interface MetricsDao {
	
	public void storeMetrics(String metrics, Timestamp timestamp);

}

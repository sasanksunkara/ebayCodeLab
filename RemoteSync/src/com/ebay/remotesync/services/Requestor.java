package com.ebay.remotesync.services;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.ebay.remotesync.dao.MetricsDao;
import com.ebay.remotesync.dao.MetricsDaoImpl;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class Requestor extends Thread {
	
	List<String> nodes;
	
	Requestor(List<String> nodes){
		this.nodes = nodes;
	}
	
	private URI getURI(String node) {
		return UriBuilder.fromUri("http://" + node + "/metric").build();	
	}

	public void run(){
		
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    String response = null;
	    MetricsDao dao = new MetricsDaoImpl();
	    for ( String node:nodes) {
	    	WebResource service = client.resource(getURI(node));
	    	response = service.accept(MediaType.APPLICATION_JSON).get(String.class);
	    	dao.storeMetrics(response, new Timestamp(Calendar.getInstance().getTimeInMillis()));
	    }
		
		
	}
}

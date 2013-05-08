package com.ebay.remotesync.services;

import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ebay.remotesync.dao.MetricsDao;
import com.ebay.remotesync.dao.MetricsDaoImpl;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class RemoteSync {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getOperationStatus() {
		return "";
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String submitRequest(JSONObject input) {
		
		/*Asserting input is not null*/
		assert(input!=null);
		/*retrieving nodes list from JSON input*/
		JSONArray nodeList = (JSONArray)input.get("nodes");
		/*asserting JSON object has array of nodes*/
		assert(nodeList!=null);
		
		List<String> nodes = new ArrayList<String>();
		Iterator<String> it = nodeList.iterator();
		while (it.hasNext()) {
			nodes.add(it.next());
		}
		/*Starting a thread which deals request data and storing metrics*/
		Requestor requestorThread = new Requestor(nodes);
		requestorThread.start();
		/*Asynchronously returning success of submission request*/
		return "SUCCESS";
	}

	

	
}
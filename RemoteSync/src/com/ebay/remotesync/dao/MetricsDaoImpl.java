package com.ebay.remotesync.dao;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Connection;	
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class MetricsDaoImpl implements MetricsDao {

	private String currNode;
	private static Connection connection=null;
	static {
		String url = "jdbc:XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
		
		try {
			/* initiliaze any classes necessary 
			 * example org.postgresql.Driver
			 */ 
			Class.forName("XXXXXXXXXXXXXXXXXXXXXXXXXXXX"); 
			// create connection
			connection = DriverManager.getConnection(url, "username", "XXXXXXX");
			
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	public void setCurrNode(String node){
		this.currNode = node;
	}
	public String getCurrNode(){
		return this.currNode;
	}
	
	public Connection getconnection(){
		return connection;
	}
	
	public void insertCpuInfo(int id, double utilization, Timestamp timestamp) throws SQLException {
		Connection connection = getconnection();
		Statement statement = connection.createStatement();
		String query = "insert into cpuInfo values ("+currNode+","+timestamp+","+id+","+utilization+");";
		statement.execute(query);
	}
	
	public double getCpuInfo(String node, int id, Timestamp timestamp)  throws SQLException{
		Connection connection = getconnection();
		Statement statement = connection.createStatement();
		String query = "select utilization from cpuInfo where nodeId LIKE node and timestamp="+timestamp+" and id="+id+";";
		ResultSet rs = statement.executeQuery(query);
		double util=0;
		while(rs.next())
			util = rs.getDouble(1);
		return util;
	}
	
	public void insertMemInfo(int used, int free, Timestamp timestamp) throws SQLException{
		Connection connection = getconnection();
		Statement statement = connection.createStatement();
		String query = "insert into memInfo values ("+currNode+","+timestamp+","+used+","+free+");";
		statement.execute(query);
	}
	
	public void updateData(JSONObject jsonObj,Timestamp timestamp) throws SQLException{
		JSONParser parser = new JSONParser();
		 
		try {
	 
			//Object obj = parser.parse(new FileReader("c:\\test.json"));
			Object obj = parser.parse("{ \"cpu\": { \"core1\":\"80%\", \"core2\":\"90%\" }, \"mem\":{\"used\":\"4340m\",\"free\":\"3240m\"}}");
			JSONObject jsonObject = (JSONObject) obj;
	 
			JSONObject cpuInfo =  (JSONObject)jsonObject.get("cpu");
			Set keys = cpuInfo.keySet();
			Iterator it = keys.iterator();
			while(it.hasNext()){
				String key = (String) it.next();
				
				String utilization = (String) cpuInfo.get(key);
				String core = key.substring(3);
				Integer coreId = Integer.parseInt(core);
				utilization = utilization.replace("%", "");
				Double util = Double.parseDouble(utilization);
				insertCpuInfo(coreId,util,timestamp);
				System.out.println(key+" "+utilization);
			}
			JSONObject memInfo =  (JSONObject)jsonObject.get("mem");
			String usedMem = (String) memInfo.get("used");
			String freeMem = (String) memInfo.get("free");
			System.out.println("used: "+usedMem+ " Free: "+freeMem);
	 
	 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	 
	  }

	
	@Override
	public void storeMetrics(String metrics, Timestamp timestamp){
		
		JSONParser parser = new JSONParser();
		
		try {
			JSONObject jsonObj = (JSONObject) parser.parse(metrics);
			updateData(jsonObj,timestamp);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

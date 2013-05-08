package com.ebay.remotesync.services;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
public class JsonSimpleExample {
     public static void main(String[] args) {
 
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
 
}
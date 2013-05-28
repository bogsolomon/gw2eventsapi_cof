package ca.bsolomon.gw2events.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

public class GW2EventsAPI {

	public static Map<String, String> eventIdToName = new HashMap<String, String>();
	public static Map<Integer, String> worldIdToName = new HashMap<Integer, String>();
	
	private static HttpClient httpclient;

	public static void generateEventIds() {
		if (httpclient == null)
			httpclient = generateClient();
		
		HttpGet httppost = new HttpGet("https://api.guildwars2.com/v1/event_names.json");
		
		try {
	        // Add your data
	        HttpResponse response = httpclient.execute(httppost);

	        BufferedReader rd = new BufferedReader
	        		  (new InputStreamReader(response.getEntity().getContent()));
	        		    
	        String longline = "";
    		String line = "";
    		while ((line = rd.readLine()) != null) {
    			longline+=line;
    		}
    		JSONArray result = (JSONArray) JSONSerializer.toJSON( longline );
    		
    		for (int i=0;i< result.size();i++) {
    			JSONObject obj = result.getJSONObject(i);
    			
    			String eventId = obj.getString("id");
    			String name = obj.getString("name");
    			
    			eventIdToName.put(eventId, name);
    		}
	    } catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	public static void generateNAWorldIds() {
		if (httpclient == null)
			httpclient = generateClient();
		
		HttpGet httppost = new HttpGet("https://api.guildwars2.com/v1/world_names.json");
		
		try {
	        // Add your data
	        HttpResponse response = httpclient.execute(httppost);

	        BufferedReader rd = new BufferedReader
	        		  (new InputStreamReader(response.getEntity().getContent()));
	        		    
	        String longline = "";
    		String line = "";
    		while ((line = rd.readLine()) != null) {
    			longline+=line;
    		}
    		JSONArray result = (JSONArray) JSONSerializer.toJSON( longline );
    		
    		for (int i=0;i< result.size();i++) {
    			JSONObject obj = result.getJSONObject(i);
    			
    			Integer worldId = obj.getInt("id");
    			String name = obj.getString("name");
    			
    			if (worldId < 2000)
    				worldIdToName.put(worldId, name);
    		}
	    } catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}

	private static HttpClient generateClient() {
		HttpClient httpclient = new DefaultHttpClient();
		
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[]{new TrustManager()}, null);
			
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);
			
			ClientConnectionManager ccm = httpclient.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", ssf, 443));
			
			httpclient = new DefaultHttpClient(ccm, httpclient.getParams());
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		return httpclient;
	}
	
	public static JSONArray queryServer(int worldId, int mapId) {
		if (httpclient == null)
			httpclient = generateClient();
		
		HttpGet httppost = new HttpGet("http://api.guildwars2.com/v1/events.json?world_id="+worldId+"&map_id="+mapId);
		
		try {
	        // Add your data
	        HttpResponse response = httpclient.execute(httppost);

	        BufferedReader rd = new BufferedReader
	        		  (new InputStreamReader(response.getEntity().getContent()));
	        		    
	        String longline = "";
    		String line = "";
    		while ((line = rd.readLine()) != null) {
    			longline+=line;
    		}
    		JSONObject json = (JSONObject) JSONSerializer.toJSON( longline );
    		JSONArray result = json.getJSONArray("events");
    		
    		return result;
		} catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }	
		
		return null;
	}
	
	public static JSONArray queryServer(int worldId,String eventId) {
		HttpClient httpclient = generateClient();
		
		HttpGet httppost = new HttpGet("http://api.guildwars2.com/v1/events.json?event_id="+eventId+"&world_id="+worldId);
		
		try {
	        // Add your data
	        HttpResponse response = httpclient.execute(httppost);

	        BufferedReader rd = new BufferedReader
	        		  (new InputStreamReader(response.getEntity().getContent()));
	        		    
	        String longline = "";
    		String line = "";
    		while ((line = rd.readLine()) != null) {
    			longline+=line;
    		}
    		JSONObject json = (JSONObject) JSONSerializer.toJSON( longline );
    		JSONArray result = json.getJSONArray("events");
    		
    		return result;
		} catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }	
		
		return null;
	}
	
	public static JSONArray queryServer(String eventId) {
		HttpClient httpclient = generateClient();
		
		HttpGet httppost = new HttpGet("http://api.guildwars2.com/v1/events.json?event_id="+eventId);
		
		try {
	        // Add your data
	        HttpResponse response = httpclient.execute(httppost);

	        BufferedReader rd = new BufferedReader
	        		  (new InputStreamReader(response.getEntity().getContent()));
	        		    
	        String longline = "";
    		String line = "";
    		while ((line = rd.readLine()) != null) {
    			longline+=line;
    		}
    		JSONObject json = (JSONObject) JSONSerializer.toJSON( longline );
    		JSONArray result = json.getJSONArray("events");
    		
    		return result;
		} catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }	
		
		return null;
	}
}
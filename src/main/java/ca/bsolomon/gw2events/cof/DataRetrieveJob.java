package ca.bsolomon.gw2events.cof;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import ca.bsolomon.gw2event.api.GW2EventsAPI;
import ca.bsolomon.gw2events.util.WorldData;

@DisallowConcurrentExecution
public class DataRetrieveJob implements Job {

	private WorldData data = new WorldData();
	
	private GW2EventsAPI api = new GW2EventsAPI();
	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		if (GW2EventsAPI.eventIdToName.size() == 0) {
			System.out.println("Generating Event IDs");
			GW2EventsAPI.generateEventIds();
			
			System.out.println("Generating World IDs");
			GW2EventsAPI.generateNAWorldIds();
		}
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss z");
		format.setCalendar(cal);
		String time = format.format(cal.getTime());
		
		//check for worlds where it is open
		JSONArray result = api.queryServer("A1182080-2599-4ACC-918E-A3275610602B");
		JSONArray result2 = api.queryServer("6A8374CF-9999-43E9-B1C7-BAB1541F2426");
		
		for (int i=0; i <result.size(); i++) {
			JSONObject obj = result.getJSONObject(i);
			String state = obj.getString("state");
			Integer worldId = obj.getInt("world_id");
			String state2 = result2.getJSONObject(i).getString("state");
			
			if (worldId > 1999)
				continue;
			
			if (state.equals("Warmup")) {
				if (data.addOpenWorld(worldId, time)) {
					data.removeEscortWorld(worldId);
				}
			} else {
				data.removeOpenWorld(worldId);
					
				if (state2.equals("Active") || state2.equals("Preparation") || state2.equals("Success") || state.equals("Active")) {
					if (data.addEscortWorld(worldId, time)) {
						data.removeEscortWarmupWorld(worldId);
					}
				}  else {
					data.removeEscortWorld(worldId);
					
					if (state2.equals("Warmup")) {
						data.addEscortWarmupWorld(worldId, time);
					} else {
						data.removeEscortWarmupWorld(worldId);
					}
				}
			}
		}
	}
}
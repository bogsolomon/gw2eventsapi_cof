package ca.bsolomon.gw2events.cof;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import ca.bsolomon.gw2events.util.GW2EventsAPI;
import ca.bsolomon.gw2events.util.WorldData;

public class DataRetrieveJob implements Job {

	private WorldData data = new WorldData();
	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		PushContext pushContext = PushContextFactory.getDefault().getPushContext();
		
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
		JSONArray result = GW2EventsAPI.queryServer("A1182080-2599-4ACC-918E-A3275610602B");
		JSONArray result2 = GW2EventsAPI.queryServer("6A8374CF-9999-43E9-B1C7-BAB1541F2426");
		
		boolean openChanged = false;
		boolean escortChanged = false;
		boolean escortWarmupChanged = false;
		
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
					openChanged = true;
					escortChanged = true;
				}
			} else {
				if (data.removeOpenWorld(worldId)) {
					openChanged = true;
				}
				
				if (state2.equals("Active") || state2.equals("Preparation") || state2.equals("Success") || state.equals("Active")) {
					if (data.addEscortWorld(worldId, time)) {
						data.removeEscortWarmupWorld(worldId);
						escortChanged = true;
						escortWarmupChanged = true;
					}
				}  else {
					if (data.removeEscortWorld(worldId)) {
						escortChanged = true;
					}
					
					if (state2.equals("Warmup")) {
						if (data.addEscortWarmupWorld(worldId, time)) {
							escortWarmupChanged = true;
						}
					} else {
						if (data.removeEscortWarmupWorld(worldId)) {
							escortWarmupChanged = true;
						}
					}
				}
			}
		}
		
		if (openChanged) {
			StringBuffer output = new StringBuffer();
			Map<Integer, String> worldsOpen = data.getOpenWorlds();
			
			for (Integer worldId:worldsOpen.keySet()) {
				output.append("["+worldsOpen.get(worldId)+"]["+GW2EventsAPI.worldIdToName.get(worldId)+"]"+"</br>");
			}
			
			pushContext.push("/OpenWorlds", output.toString());
		}
		
		if (escortChanged) {
			StringBuffer outputEscort = new StringBuffer();
			Map<Integer, String> worldsEscort = data.getEscortWorlds();
			
			for (Integer worldId:worldsEscort.keySet()) {
				outputEscort.append("["+worldsEscort.get(worldId)+"]["+GW2EventsAPI.worldIdToName.get(worldId)+"]"+"</br>");
			}
			
			pushContext.push("/EscortWorlds", outputEscort.toString());
		}
		
		if (escortWarmupChanged) {
			StringBuffer outputEscortWarmup = new StringBuffer();
			Map<Integer, String> worldsEscortWarmup = data.getEscortWarmupWorlds();
			
			for (Integer worldId:worldsEscortWarmup.keySet()) {
				outputEscortWarmup.append("["+worldsEscortWarmup.get(worldId)+"]["+GW2EventsAPI.worldIdToName.get(worldId)+"]"+"</br>");
			}
				
			pushContext.push("/EscortWarmupWorlds", outputEscortWarmup.toString());
		}
		
//		if (preChanged) {
//			StringBuffer outputPre = new StringBuffer();
//			for (Integer worldId:worldsPre.keySet()) {
//				outputPre.append("["+worldsPre.get(worldId)+"]["+GW2EventsAPI.worldIdToName.get(worldId)+"]"+"</br>");
//			}
//			
//			pushContext.push("/PreWorlds", outputPre.toString());
//		}
//		
//		if (inactiveChanged) {
//			StringBuffer outputInactive = new StringBuffer();
//			for (Integer worldId:worldsInactive.keySet()) {
//				outputInactive.append("["+worldsInactive.get(worldId)+"]["+GW2EventsAPI.worldIdToName.get(worldId)+"]"+"</br>");
//			}
//			
//			pushContext.push("/InactiveWorlds", outputInactive.toString());
//		}
	}
}
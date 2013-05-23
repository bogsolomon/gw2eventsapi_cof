package ca.bsolomon.gw2events.cof;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import net.sf.json.JSONObject;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import ca.bsolomon.gw2events.util.GW2APISchedulerListener;
import ca.bsolomon.gw2events.util.GW2EventsAPI;

public class DataRetrieveJob implements Job {

	private static Map<Integer, String> worldsOpen = new HashMap<Integer, String>();
	private static Map<Integer, String> worldsEscort = new HashMap<Integer, String>();
	private static Map<Integer, String> worldsEscortWarmup = new HashMap<Integer, String>();
	private static Map<Integer, String> worldsPre = new HashMap<Integer, String>();
	private static Map<Integer, String> worldsInactive = new HashMap<Integer, String>();
	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		PushContext pushContext = PushContextFactory.getDefault().getPushContext();
		
		if (GW2EventsAPI.eventIdToName.size() == 0) {
			System.out.println("Generating Event IDs");
			GW2EventsAPI.generateEventIds();
			
			System.out.println("Generating World IDs");
			GW2EventsAPI.generateNAWorldIds();
			
			try {
				context.getScheduler().getListenerManager().addSchedulerListener(new GW2APISchedulerListener());
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss z");
		format.setCalendar(cal);
		String time = format.format(cal.getTime());
		
		boolean openChanged = false;
		boolean escortChanged = false;
		boolean escortWarmupChanged = false;
		boolean preChanged = false;
		boolean inactiveChanged = false;
		
		//check for worlds where it is open
		for (Integer worldId:GW2EventsAPI.worldIdToName.keySet()) {
			JSONObject result = GW2EventsAPI.queryServer(worldId, "A1182080-2599-4ACC-918E-A3275610602B").getJSONObject(0);
		
			String state = result.getString("state");
			
			if (state.equals("Warmup")) {
				if (!worldsOpen.containsKey(worldId)) {
					worldsOpen.put(worldId, time);
					worldsPre.remove(worldId);
					worldsInactive.remove(worldId);
					worldsEscort.remove(worldId);
					
					openChanged = true;
				}
			} else {
				if (worldsOpen.containsKey(worldId)) {
					worldsOpen.remove(worldId);
					
					openChanged = true;
				}
				
				JSONObject result2 = GW2EventsAPI.queryServer(worldId, "6A8374CF-9999-43E9-B1C7-BAB1541F2426").getJSONObject(0);
				String state2 = result2.getString("state");
				
				if (state2.equals("Active") || state2.equals("Preparation") || state2.equals("Success") || state.equals("Active")) {
					if (!worldsEscort.containsKey(worldId)) {
						worldsEscort.put(worldId, time);
						worldsPre.remove(worldId);
						worldsInactive.remove(worldId);
						worldsEscortWarmup.remove(worldId);
						
						escortChanged = true;
					}
				}  else {
					if (worldsEscort.containsKey(worldId)) {
						worldsEscort.remove(worldId);
						
						escortChanged = true;
					}
					
					if (state2.equals("Warmup")) {
						if (!worldsEscortWarmup.containsKey(worldId)) {
							worldsEscortWarmup.put(worldId, time);
							worldsPre.remove(worldId);
							worldsInactive.remove(worldId);
							
							escortWarmupChanged = true;
						}
					} else {
						if (!worldsEscortWarmup.containsKey(worldId)) {
							worldsEscortWarmup.remove(worldId);
							
							escortWarmupChanged = true;
						}
						
						JSONObject result3 = GW2EventsAPI.queryServer(worldId, "CAA60D81-7735-47D6-9695-6952CCEB9E9F").getJSONObject(0);
						String state3 = result3.getString("state");
						
						JSONObject result4 = GW2EventsAPI.queryServer(worldId, "006A8ECE-FC43-443C-B297-C46195751EA9").getJSONObject(0);
						String state4 = result4.getString("state");
						
						JSONObject result5 = GW2EventsAPI.queryServer(worldId, "0E1E3895-B6AF-43E0-A618-0C86415A95B4").getJSONObject(0);
						String state5 = result5.getString("state");
						
						if (state3.equals("Active") || state4.equals("Active") || state5.equals("Active")) {
							if (!worldsPre.containsKey(worldId)) {
								worldsPre.put(worldId, time);
								worldsInactive.remove(worldId);
								
								preChanged = true;
							}
						} else {
							if (worldsPre.containsKey(worldId)) {
								worldsPre.remove(worldId);
								
								preChanged = true;
							}
							
							if (!worldsInactive.containsKey(worldId)) {
								worldsInactive.put(worldId, time);
								
								inactiveChanged = true;
							}
						}
					}
				}
			}
		}
		
		if (openChanged) {
			StringBuffer output = new StringBuffer();
			for (Integer worldId:worldsOpen.keySet()) {
				output.append("["+worldsOpen.get(worldId)+"]["+GW2EventsAPI.worldIdToName.get(worldId)+"]"+"</br>");
			}
			
			pushContext.push("/OpenWorlds", output.toString());
		}
		
		if (escortChanged) {
			StringBuffer outputEscort = new StringBuffer();
			for (Integer worldId:worldsEscort.keySet()) {
				outputEscort.append("["+worldsEscort.get(worldId)+"]["+GW2EventsAPI.worldIdToName.get(worldId)+"]"+"</br>");
			}
			
			pushContext.push("/EscortWorlds", outputEscort.toString());
		}
		
		if (escortWarmupChanged) {
			StringBuffer outputEscortWarmup = new StringBuffer();
			for (Integer worldId:worldsEscortWarmup.keySet()) {
				outputEscortWarmup.append("["+worldsEscortWarmup.get(worldId)+"]["+GW2EventsAPI.worldIdToName.get(worldId)+"]"+"</br>");
			}
				
			pushContext.push("/EscortWarmupWorlds", outputEscortWarmup.toString());
		}
		
		if (preChanged) {
			StringBuffer outputPre = new StringBuffer();
			for (Integer worldId:worldsPre.keySet()) {
				outputPre.append("["+worldsPre.get(worldId)+"]["+GW2EventsAPI.worldIdToName.get(worldId)+"]"+"</br>");
			}
			
			pushContext.push("/PreWorlds", outputPre.toString());
		}
		
		if (inactiveChanged) {
			StringBuffer outputInactive = new StringBuffer();
			for (Integer worldId:worldsInactive.keySet()) {
				outputInactive.append("["+worldsInactive.get(worldId)+"]["+GW2EventsAPI.worldIdToName.get(worldId)+"]"+"</br>");
			}
			
			pushContext.push("/InactiveWorlds", outputInactive.toString());
		}
	}
}
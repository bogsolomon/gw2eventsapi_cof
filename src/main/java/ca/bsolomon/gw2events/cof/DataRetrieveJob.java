package ca.bsolomon.gw2events.cof;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import ca.bsolomon.gw2event.api.GW2EventsAPI;
import ca.bsolomon.gw2event.api.dao.Event;
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
		
		getCoFStatus(time);
		getCoEStatus(time);
	}

	private void getCoEStatus(String time) {
		List<Event> result = api.queryServer("9752677E-FAE7-4F56-A48A-275329095B8A");
		List<Event> result2 = api.queryServer("ED8ADFBF-6D6A-4B98-BD49-741D39A04871");
		
		for (int i=0; i <result.size(); i++) {
			Event obj = result.get(i);
			String state = obj.getState();
			Integer worldId = Integer.parseInt(obj.getWorldId());
			String state2 = result2.get(i).getState();
			
			if (worldId > 1999)
				continue;
			
			if (state.equals("Warmup")) {
				if (data.addCoEOpenWorld(worldId, time)) {
					data.removeCoEEscortWorld(worldId);
				}
			} else {
				data.removeCoEOpenWorld(worldId);
					
				if (state2.equals("Active") || state2.equals("Preparation") || state2.equals("Success") || state.equals("Active")) {
					if (data.addCoEEscortWorld(worldId, time)) {
						data.removeCoEEscortWarmupWorld(worldId);
					}
				}  else {
					data.removeCoEEscortWorld(worldId);
					
					if (state2.equals("Warmup")) {
						data.addCoEEscortWarmupWorld(worldId, time);
					} else {
						data.removeCoEEscortWarmupWorld(worldId);
					}
				}
			}
		}
	}

	private void getCoFStatus(String time) {
		List<Event> result = api.queryServer("A1182080-2599-4ACC-918E-A3275610602B");
		List<Event> result2 = api.queryServer("6A8374CF-9999-43E9-B1C7-BAB1541F2426");
		
		for (int i=0; i <result.size(); i++) {
			Event obj = result.get(i);
			String state = obj.getState();
			Integer worldId = Integer.parseInt(obj.getWorldId());
			String state2 = result2.get(i).getState();
			
			if (worldId > 1999)
				continue;
			
			if (state.equals("Warmup")) {
				if (data.addCoFOpenWorld(worldId, time)) {
					data.removeCoFEscortWorld(worldId);
				}
			} else {
				data.removeCoFOpenWorld(worldId);
					
				if (state2.equals("Active") || state2.equals("Preparation") || state2.equals("Success") || state.equals("Active")) {
					if (data.addCoFEscortWorld(worldId, time)) {
						data.removeCoFEscortWarmupWorld(worldId);
					}
				}  else {
					data.removeCoFEscortWorld(worldId);
					
					if (state2.equals("Warmup")) {
						data.addCoFEscortWarmupWorld(worldId, time);
					} else {
						data.removeCoFEscortWarmupWorld(worldId);
					}
				}
			}
		}
	}
}
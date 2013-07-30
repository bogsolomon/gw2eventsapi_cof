package ca.bsolomon.gw2events.cof;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ca.bsolomon.gw2event.api.GW2EventsAPI;
import ca.bsolomon.gw2events.util.WorldData;

@ManagedBean(name="cofEventBean")
@ViewScoped
public class CoFEventBean {
	
	private WorldData data = new WorldData();
	
	public String getCoFOpenServers() {
		return formatEventResult(data.getCoFOpenWorlds());
	}
	
	public String getCoFEscortServers() {
		return formatEventResult(data.getCoFEscortWorlds());
	}
	
	public String getCoFEscortWarmupServers() {
		return formatEventResult(data.getCoFEscortWarmupWorlds());
	}
	
	public String getCoEOpenServers() {
		return formatEventResult(data.getCoEOpenWorlds());
	}
	
	public String getCoEEscortServers() {
		return formatEventResult(data.getCoEEscortWorlds());
	}
	
	public String getCoEEscortWarmupServers() {
		return formatEventResult(data.getCoEEscortWarmupWorlds());
	}

	private String formatEventResult(Map<Integer, String> map) {
		StringBuffer output = new StringBuffer();
		for (Integer worldId:map.keySet()) {
			output.append("["+map.get(worldId)+"]["+GW2EventsAPI.worldIdToName.get(worldId)+"]"+"</br>");
			//output.append(""+GW2EventsAPI.worldIdToName.get(worldId)+""+"</br>");
		}
		
		return output.toString();
	}
	
}

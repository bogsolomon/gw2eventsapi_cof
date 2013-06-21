package ca.bsolomon.gw2events.util;

import java.util.Comparator;

import ca.bsolomon.gw2event.api.GW2EventsAPI;

public class WorldNamesComparator implements Comparator<Integer> {

	@Override
	public int compare(Integer o1, Integer o2) {
		String name1 = "";
		if (o1 != null)
			name1 = GW2EventsAPI.worldIdToName.get(o1);
		
		String name2 = "";
		if (o2 != null)
			name2 = GW2EventsAPI.worldIdToName.get(o2);
		
		return name1.compareTo(name2);
	}

}

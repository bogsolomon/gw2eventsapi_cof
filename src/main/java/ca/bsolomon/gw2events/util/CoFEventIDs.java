
package ca.bsolomon.gw2events.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CoFEventIDs {

	public static Map<String, String> eventIDs = new HashMap<String, String>();
	
	static {
        eventIDs.put("CAA60D81-7735-47D6-9695-6952CCEB9E9F", "Defend Sancia Blastfire");
        eventIDs.put("006A8ECE-FC43-443C-B297-C46195751EA9", "Protect Veragha the Shadow");
        eventIDs.put("6A8374CF-9999-43E9-B1C7-BAB1541F2426", "Escort Razen the Raider");
        
        eventIDs.put("0E1E3895-B6AF-43E0-A618-0C86415A95B4", "Prevent the relighting of the braziers");
        eventIDs.put("A1182080-2599-4ACC-918E-A3275610602B", "Hold the Gates of Flame");
        
        eventIDs = Collections.unmodifiableMap(eventIDs);
	}
	
}

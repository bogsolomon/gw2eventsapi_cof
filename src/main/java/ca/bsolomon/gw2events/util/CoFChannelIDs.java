
package ca.bsolomon.gw2events.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CoFChannelIDs {

	public static Map<String, String> channelIDs = new HashMap<String, String>();
	
	static {
		channelIDs.put("81F89AC2-4764-49CB-A4F1-8B7546201DC7", "DriftglassInstigator");
		channelIDs.put("72F93CD8-94AC-4234-8D86-996CCAC76A46", "KielInstigator");
        
		channelIDs.put("AB17125F-A0D6-4103-99FD-FF236D03679A", "PrideVictim");
		channelIDs.put("F6ECAE88-EE84-4B64-8A4E-0BFC3FABE486", "OwainVictim");
        channelIDs.put("5B7F1A45-27DB-45D5-803F-57B6FBB5DBE8", "LionVictim");
        channelIDs.put("68CFE130-BB99-4297-B376-0B9E4E0B1E5B", "KielVictim");
        channelIDs.put("834B249D-8541-4DE9-B040-845C0A349303", "DriftglassVictim");
        channelIDs.put("C037718C-0300-4357-8071-C543B9C9BE57", "CampKarkaVictim");
        
        channelIDs.put("4AB50247-DB57-4C6D-9922-7E4D4FFC1F58", "DriftglassKarka");
        channelIDs.put("42B3E918-03F3-4070-8115-902773C13C0D", "KielKarka");
        channelIDs.put("F6563016-3832-4EBA-82F1-535BFF96422D", "CampKarkaKarka");
        
        channelIDs.put("C98B3487-A842-4DF3-B712-7520DA227DBE", "PrideFactions");
        channelIDs.put("5C591A3E-900C-4B74-94CB-E2AD4E98B79A", "LionFactions");
        channelIDs.put("F91C5E5D-F654-43B3-9DEE-EE502A7C5E81", "OwainFactions");
        channelIDs.put("510DBF11-BF20-4E38-A763-BAD9A0307CB5", "CampKarkaFactions");
        channelIDs.put("F63CFAF5-14AB-4DB5-9FCC-4CCC5C8658BD", "KielFactions");
        channelIDs.put("821557D1-CFEB-4D6D-9DDA-D4C17CE6794B", "DriftglassFactions");
        
        channelIDs = Collections.unmodifiableMap(channelIDs);
	}
	
}

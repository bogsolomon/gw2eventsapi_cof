package ca.bsolomon.gw2events.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WorldData {

	private static ConcurrentMap<Integer, String> worldsOpen = new ConcurrentHashMap<Integer, String>(16, 0.9f, 1);
	private static ConcurrentMap<Integer, String> worldsEscort = new ConcurrentHashMap<Integer, String>(16, 0.9f, 1);
	private static ConcurrentMap<Integer, String> worldsEscortWarmup = new ConcurrentHashMap<Integer, String>(16, 0.9f, 1);
	
	public boolean addOpenWorld(Integer worldId, String time) {
		String timeAdded = worldsOpen.putIfAbsent(worldId, time);
		
		if (time.equals(timeAdded) || timeAdded == null) {
			return true;
		}
		
		return false;
	}
	
	public boolean addEscortWorld(Integer worldId, String time) {
		String timeAdded = worldsEscort.putIfAbsent(worldId, time);
		
		if (time.equals(timeAdded) || timeAdded == null) {
			return true;
		}
		
		return false;
	}
	
	public boolean addEscortWarmupWorld(Integer worldId, String time) {
		String timeAdded = worldsEscortWarmup.putIfAbsent(worldId, time);
		
		if (time.equals(timeAdded) || timeAdded == null) {
			return true;
		}
		
		return false;
	}
	
	public boolean removeOpenWorld(Integer worldId) {
		if (worldsOpen.remove(worldId) == null)
			return false;
		
		return true;
	}
	
	public boolean removeEscortWorld(Integer worldId) {
		if (worldsEscort.remove(worldId) == null) {
			return false;
		}
		
		return true;
	}
	
	public boolean removeEscortWarmupWorld(Integer worldId) {
		if (worldsEscortWarmup.remove(worldId) == null) {
			return false;
		}
		
		return true;
	}
	
	public Map<Integer, String> getOpenWorlds() {
		return sortedMap(worldsOpen);
	}
	
	public Map<Integer, String> getEscortWorlds() {
		return sortedMap(worldsEscort);
	}
	
	public Map<Integer, String> getEscortWarmupWorlds() {
		return sortedMap(worldsEscortWarmup);
	}
	
	private Map<Integer, String> sortedMap(
			ConcurrentMap<Integer, String> inMap) {
		List<Integer> keys = new LinkedList<Integer>(inMap.keySet());
        Collections.sort(keys, new WorldNamesComparator());
     
        //LinkedHashMap will keep the keys in the order they are inserted
        //which is currently sorted on natural ordering
        Map<Integer,String> sortedMap = new LinkedHashMap<Integer,String>();
        for(Integer key: keys){
            sortedMap.put(key, inMap.get(key));
        }
     
        return sortedMap;
	}

	
}

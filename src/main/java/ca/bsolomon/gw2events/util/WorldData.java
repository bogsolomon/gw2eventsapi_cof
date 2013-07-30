package ca.bsolomon.gw2events.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WorldData {

	private static ConcurrentMap<Integer, String> cofWorldsOpen = new ConcurrentHashMap<Integer, String>(16, 0.9f, 1);
	private static ConcurrentMap<Integer, String> cofWorldsEscort = new ConcurrentHashMap<Integer, String>(16, 0.9f, 1);
	private static ConcurrentMap<Integer, String> cofWorldsEscortWarmup = new ConcurrentHashMap<Integer, String>(16, 0.9f, 1);
	
	private static ConcurrentMap<Integer, String> coeWorldsOpen = new ConcurrentHashMap<Integer, String>(16, 0.9f, 1);
	private static ConcurrentMap<Integer, String> coeWorldsEscort = new ConcurrentHashMap<Integer, String>(16, 0.9f, 1);
	private static ConcurrentMap<Integer, String> coeWorldsEscortWarmup = new ConcurrentHashMap<Integer, String>(16, 0.9f, 1);
	
	public boolean addCoFOpenWorld(Integer worldId, String time) {
		String timeAdded = cofWorldsOpen.putIfAbsent(worldId, time);
		
		if (time.equals(timeAdded) || timeAdded == null) {
			return true;
		}
		
		return false;
	}
	
	public boolean addCoFEscortWorld(Integer worldId, String time) {
		String timeAdded = cofWorldsEscort.putIfAbsent(worldId, time);
		
		if (time.equals(timeAdded) || timeAdded == null) {
			return true;
		}
		
		return false;
	}
	
	public boolean addCoFEscortWarmupWorld(Integer worldId, String time) {
		String timeAdded = cofWorldsEscortWarmup.putIfAbsent(worldId, time);
		
		if (time.equals(timeAdded) || timeAdded == null) {
			return true;
		}
		
		return false;
	}
	
	public boolean removeCoFOpenWorld(Integer worldId) {
		if (cofWorldsOpen.remove(worldId) == null)
			return false;
		
		return true;
	}
	
	public boolean removeCoFEscortWorld(Integer worldId) {
		if (cofWorldsEscort.remove(worldId) == null) {
			return false;
		}
		
		return true;
	}
	
	public boolean removeCoFEscortWarmupWorld(Integer worldId) {
		if (cofWorldsEscortWarmup.remove(worldId) == null) {
			return false;
		}
		
		return true;
	}
	
	public Map<Integer, String> getCoFOpenWorlds() {
		return sortedMap(cofWorldsOpen);
	}
	
	public Map<Integer, String> getCoFEscortWorlds() {
		return sortedMap(cofWorldsEscort);
	}
	
	public Map<Integer, String> getCoFEscortWarmupWorlds() {
		return sortedMap(cofWorldsEscortWarmup);
	}
	
	public boolean addCoEOpenWorld(Integer worldId, String time) {
		String timeAdded = coeWorldsOpen.putIfAbsent(worldId, time);
		
		if (time.equals(timeAdded) || timeAdded == null) {
			return true;
		}
		
		return false;
	}
	
	public boolean addCoEEscortWorld(Integer worldId, String time) {
		String timeAdded = coeWorldsEscort.putIfAbsent(worldId, time);
		
		if (time.equals(timeAdded) || timeAdded == null) {
			return true;
		}
		
		return false;
	}
	
	public boolean addCoEEscortWarmupWorld(Integer worldId, String time) {
		String timeAdded = coeWorldsEscortWarmup.putIfAbsent(worldId, time);
		
		if (time.equals(timeAdded) || timeAdded == null) {
			return true;
		}
		
		return false;
	}
	
	public boolean removeCoEOpenWorld(Integer worldId) {
		if (coeWorldsOpen.remove(worldId) == null)
			return false;
		
		return true;
	}
	
	public boolean removeCoEEscortWorld(Integer worldId) {
		if (coeWorldsEscort.remove(worldId) == null) {
			return false;
		}
		
		return true;
	}
	
	public boolean removeCoEEscortWarmupWorld(Integer worldId) {
		if (coeWorldsEscortWarmup.remove(worldId) == null) {
			return false;
		}
		
		return true;
	}
	
	public Map<Integer, String> getCoEOpenWorlds() {
		return sortedMap(coeWorldsOpen);
	}
	
	public Map<Integer, String> getCoEEscortWorlds() {
		return sortedMap(coeWorldsEscort);
	}
	
	public Map<Integer, String> getCoEEscortWarmupWorlds() {
		return sortedMap(coeWorldsEscortWarmup);
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

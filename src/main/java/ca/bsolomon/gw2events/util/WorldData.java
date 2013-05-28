package ca.bsolomon.gw2events.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WorldData {

	private static ConcurrentMap<Integer, String> worldsOpen = new ConcurrentHashMap<Integer, String>(16, 0.9f, 1);
	private static ConcurrentMap<Integer, String> worldsEscort = new ConcurrentHashMap<Integer, String>(16, 0.9f, 1);
	private static ConcurrentMap<Integer, String> worldsEscortWarmup = new ConcurrentHashMap<Integer, String>(16, 0.9f, 1);
	
	public boolean addOpenWorld(Integer worldId, String time) {
		worldsOpen.putIfAbsent(worldId, time);
	}
	
	public void addEscortWorld(Integer worldId, String time) {
		worldsEscort.putIfAbsent(worldId, time);
	}
	
	public void addEscortWarmupWorld(Integer worldId, String time) {
		worldsEscortWarmup.putIfAbsent(worldId, time);
	}
	
	public void removeOpenWorld(Integer worldId) {
		worldsOpen.remove(worldId);
	}
	
	public void removeEscortWorld(Integer worldId) {
		worldsEscort.remove(worldId);
	}
	
	public void removeEscortWarmupWorld(Integer worldId) {
		worldsEscortWarmup.remove(worldId);
	}
	
	public Map<Integer, String> getOpenWorlds() {
		return worldsOpen;
	}
	
	public Map<Integer, String> getEscortWorlds() {
		return worldsEscort;
	}
	
	public Map<Integer, String> getEscortWarmupWorlds() {
		return worldsEscortWarmup;
	}
}

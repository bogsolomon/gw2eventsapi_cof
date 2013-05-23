package ca.bsolomon.gw2events.util;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;
import org.quartz.listeners.SchedulerListenerSupport;

public class GW2APISchedulerListener extends SchedulerListenerSupport {

	public void schedulerShuttingdown() {
		PushContext pushContext = PushContextFactory.getDefault().getPushContext();
		
		pushContext.push("/Alerts", "Server is going down. Please wait a couple of minutes and refresh page.");
	}
}

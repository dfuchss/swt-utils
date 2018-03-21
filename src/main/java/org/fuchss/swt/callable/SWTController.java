package org.fuchss.swt.callable;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;
import org.fuchss.swt.cmd.Job;
import org.fuchss.swt.cmd.JobMapper;

public abstract class SWTController<Info> extends JobMapper implements SWTCallable<Info>, Listener {

	protected SWTCallableShell<Info> decoupler;

	protected SWTController(SWTCallableShell<Info> decoupler) {
		super(decoupler.getQueue());
		this.decoupler = decoupler;
	}

	public void addControl(Widget widget, int eventType, Job cmd) {
		widget.addListener(eventType, this);
		this.addJob(widget, eventType, cmd);
	}

	@Override
	public void handleEvent(Event event) {
		if (event.widget != null) {
			this.doAction(event.widget, event);
		}
	}

	protected void sendInfo(Info info) {
		this.decoupler.decouple(info, this);
	}

}
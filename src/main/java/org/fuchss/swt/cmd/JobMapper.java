package org.fuchss.swt.cmd;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;

import java.util.HashMap;
import java.util.Map;

public abstract class JobMapper {

	private final Map<Widget, Map<Integer, Job>> cmdMap = new HashMap<>();
	private final WorkerQueue queue;

	protected JobMapper(WorkerQueue q) {
		this.queue = q;
	}

	protected void addJob(Widget eventSource, int eventType, Job cmd) {
		Map<Integer, Job> eventMap;
		if ((eventMap = this.cmdMap.get(eventSource)) == null) {
			this.cmdMap.put(eventSource, eventMap = new HashMap<>());
		}
		eventMap.put(eventType, cmd);
	}

	protected void doAction(Widget widget, Event event) {
		Job cmd;
		Map<Integer, Job> eventMap;
		if (widget == null || (eventMap = this.cmdMap.get(widget)) == null || (cmd = eventMap.get(event.type)) == null) {
			return;
		}
		if (cmd.init(widget, event)) {
			this.queue.put(cmd);
		}
	}

	protected void doAction(Job cmd, Widget widget, Event event) {
		if (cmd == null) {
			return;
		}
		if (cmd.init(widget, event)) {
			this.queue.put(cmd);
		}
	}

	public WorkerQueue getQueue() {
		return this.queue;
	}

}
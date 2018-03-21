package org.fuchss.swt.cmd;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;

public interface Job {

	void execute();

	default int waitMsec() {
		return 0;
	}

	default boolean init(Widget widget, Event event) {
		return true;
	}

	default boolean isReducedBy(Job job) {
		return false;
	}

}

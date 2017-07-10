package org.fuchss.swt.callable.cmd;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;

public interface Job {

	void execute();

	int waitMsec();

	boolean init(Widget widget, Event event);

	boolean isReducedBy(Job job);

}

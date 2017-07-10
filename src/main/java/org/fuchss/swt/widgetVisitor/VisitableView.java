package org.fuchss.swt.widgetVisitor;

import org.fuchss.swt.callable.SWTCallableShell;

public interface VisitableView<State, Model> extends VisitableWidget {
	void createController(SWTCallableShell<State> shell, Model model);
}
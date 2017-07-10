package org.fuchss.swt.widgetVisitor;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;

public interface WidgetVisitor {
	void visit(Composite composite);

	void visit(Dialog dialog);
}

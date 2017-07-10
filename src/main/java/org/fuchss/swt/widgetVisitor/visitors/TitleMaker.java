package org.fuchss.swt.widgetVisitor.visitors;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.fuchss.swt.widgetVisitor.WidgetVisitor;
import org.fuchss.swt.widgetVisitor.visitors.initializers.Initializer;

public class TitleMaker implements WidgetVisitor {

	private Initializer headerInitializer;
	private String header;

	@Override
	public void visit(Composite composite) {
		this.initializeTitleMaker(composite);
	}

	@Override
	public void visit(Dialog dialog) {
		this.initializeTitleMaker(dialog);

	}

	private void initializeTitleMaker(Object obj) {
		this.headerInitializer = new Initializer(obj);
		this.getHeader();
	}

	public String getHeader() {
		if (this.header == null && (this.header = this.headerInitializer.getHeader()) == null) {
			return "";
		}
		return this.header;
	}
}

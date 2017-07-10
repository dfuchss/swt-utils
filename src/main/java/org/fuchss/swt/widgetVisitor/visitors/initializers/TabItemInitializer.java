package org.fuchss.swt.widgetVisitor.visitors.initializers;

import org.eclipse.swt.widgets.TabItem;

public class TabItemInitializer extends Initializer {

	public TabItemInitializer(Object widget) {
		super(widget);
	}

	private TabItem item;
	private String name;

	@Override
	public void initializeField() {
		String text = this.getString(this.name, "text");
		if (text != null) {
			this.item.setText(text);
		}
	}

	@Override
	public void setFieldObject(Object fieldObject, String name) {
		this.item = (TabItem) fieldObject;
		this.name = this.prefix + "." + name;
	}
}

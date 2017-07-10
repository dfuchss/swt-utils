package org.fuchss.swt.widgetVisitor.visitors.initializers;

import org.eclipse.swt.widgets.Label;

public class LabelInitializer extends Initializer {

	private Label label;
	private String name;

	public LabelInitializer(Object widget) {
		super(widget);
	}

	@Override
	public void initializeField() {
		String text = this.getString(this.name, "text");
		if (text != null) {
			this.label.setText(text);
		}
	}

	@Override
	public void setFieldObject(Object fieldObject, String name) {
		this.label = (Label) fieldObject;
		this.name = this.prefix + "." + name;
	}

}

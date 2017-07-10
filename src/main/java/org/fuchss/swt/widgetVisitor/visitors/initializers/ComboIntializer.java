package org.fuchss.swt.widgetVisitor.visitors.initializers;

import java.util.Arrays;

import org.eclipse.swt.widgets.Combo;

public class ComboIntializer extends Initializer {
	private Combo combo;
	private String name;

	public ComboIntializer(Object widget) {
		super(widget);
	}

	@Override
	public void initializeField() {
		String[] items = this.getStringArray(this.name, "items", 1);
		if (items != null) {
			this.combo.setItems(items);
		}
	}

	@Override
	public void setFieldObject(Object fieldObject, String name) {
		this.combo = (Combo) fieldObject;
		this.name = this.prefix + "." + name;
	}

	@Override
	public void disableField(String state) {
		String[] states = this.getStringArray(this.name, "enable", 1);
		if (states != null) {
			if (Arrays.asList(states).contains(state)) {
				this.combo.setEnabled(true);
			} else {
				this.combo.setEnabled(false);
			}
		} else {
			this.combo.setEnabled(true);
		}
	};
}

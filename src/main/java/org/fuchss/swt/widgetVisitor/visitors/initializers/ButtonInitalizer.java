package org.fuchss.swt.widgetVisitor.visitors.initializers;

import java.util.Arrays;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;

public class ButtonInitalizer extends Initializer {

	private Button button;
	private String name;

	public ButtonInitalizer(Object obj) {
		super(obj);
	}

	@Override
	public void initializeField() {
		String str = this.getString(this.name, "text");
		if (str != null) {
			this.button.setText(str);
		}

		str = this.getString(this.name, "selection");
		if (str != null) {
			this.button.setSelection("true".equals(str.toLowerCase()));
		}

		Integer[] size = this.getIntegerArray(this.name, "size", 2);
		if (size != null) {
			((GridData) this.button.getLayoutData()).widthHint = size[0];
			((GridData) this.button.getLayoutData()).heightHint = size[1];
		}
	}

	@Override
	public void setFieldObject(Object fieldObject, String name) {
		this.button = (Button) fieldObject;
		this.name = this.prefix + "." + name;
	}

	@Override
	public void disableField(String state) {
		String[] states = this.getStringArray(this.name, "enable", 1);
		if (states != null) {
			if (Arrays.asList(states).contains(state)) {
				this.button.setEnabled(true);
			} else {
				this.button.setEnabled(false);
			}
		} else {
			this.button.setEnabled(true);
		}
	}

	@Override
	public void hideField(String state) {
		String[] states = this.getStringArray(this.name, "hide", 1);
		if (states != null) {
			if (Arrays.asList(states).contains(state)) {
				this.button.setVisible(false);
			} else {
				this.button.setVisible(true);
			}
		} else {
			this.button.setVisible(true);
		}
	}

}

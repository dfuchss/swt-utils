package org.fuchss.swt.widgetVisitor.visitors.initializers;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;

import java.util.Arrays;

public class TextInitializer extends Initializer {

	private Text text;
	private String name;

	public TextInitializer(Object widget) {
		super(widget);
	}

	@Override
	public void initializeField() {
		String str = this.getString(this.name, "text");
		if (str != null) {
			this.text.setText(str);
		}

		str = this.getString(this.name, "message");
		if (str != null) {
			this.text.setMessage(str);
		}

		str = this.getString(this.name, "editable");
		if (str != null) {
			this.text.setEditable("true".equalsIgnoreCase(str));
		}

		Integer size = this.getInteger(this.name, "width");
		if (size != null) {
			((GridData) this.text.getLayoutData()).widthHint = size;
		}
	}

	@Override
	public void setFieldObject(Object fieldObject, String name) {
		this.text = (Text) fieldObject;
		this.name = this.prefix + "." + name;
	}

	@Override
	public void disableField(String state) {
		String[] states = this.getStringArray(this.name, "enable", 1);
		if (states != null) {
			this.text.setEnabled(Arrays.asList(states).contains(state));
		} else {
			this.text.setEnabled(true);
		}
	}

}

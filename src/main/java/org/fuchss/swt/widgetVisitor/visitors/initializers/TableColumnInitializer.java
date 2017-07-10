package org.fuchss.swt.widgetVisitor.visitors.initializers;

import org.eclipse.swt.widgets.TableColumn;

public class TableColumnInitializer extends Initializer {

	private TableColumn column;
	private String name;

	public TableColumnInitializer(Object widget) {
		super(widget);
	}

	@Override
	public void initializeField() {
		Integer width = this.getInteger(this.name, "width");
		String text = this.getString(this.name, "text");
		if (width != null) {
			this.column.setWidth(width);
		}
		if (text != null) {
			this.column.setText(text);
		}
	}

	@Override
	public void setFieldObject(Object fieldObject, String name) {
		this.column = (TableColumn) fieldObject;
		this.name = this.prefix + "." + name;
	}

}

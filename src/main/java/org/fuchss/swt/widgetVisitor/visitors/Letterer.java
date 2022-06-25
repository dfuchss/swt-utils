package org.fuchss.swt.widgetVisitor.visitors;

import org.eclipse.swt.widgets.*;
import org.fuchss.swt.widgetVisitor.WidgetVisitor;
import org.fuchss.swt.widgetVisitor.visitors.initializers.*;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Letterer implements WidgetVisitor {

	private Object obj;
	private Map<Class<?>, Initializer> initializers;

	@Override
	public void visit(Composite composite) {
		this.letterObject(composite);
	}

	@Override
	public void visit(Dialog dialog) {
		this.letterObject(dialog);
	}

	private void initInitializers() {
		this.initializers = new HashMap<>();
		this.initializers.put(Button.class, new ButtonInitalizer(this.obj));
		this.initializers.put(Combo.class, new ComboIntializer(this.obj));
		this.initializers.put(Label.class, new LabelInitializer(this.obj));
		this.initializers.put(TabItem.class, new TabItemInitializer(this.obj));
		this.initializers.put(TableColumn.class, new TableColumnInitializer(this.obj));
		this.initializers.put(Text.class, new TextInitializer(this.obj));
	}

	private void letterObject(Object obj) {
		this.obj = obj;
		this.initInitializers();
		this.initializeContent();
	}

	private void initializeContent() {
		List<Field> fields = new ArrayList<>();
		this.possibleFields(this.obj.getClass(), fields);
		for (Field field : fields) {
			this.initializeField(field);
		}
	}

	private void initializeField(Field field) {
		try {
			field.setAccessible(true);
			Type type = field.getGenericType();
			Initializer initializer = this.initializers.get(type);
			if (initializer != null) {
				initializer.setFieldObject(field.get(this.obj), this.propId(field));
				initializer.initializeField();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

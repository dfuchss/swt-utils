package org.fuchss.swt.widgetVisitor.visitors;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Text;
import org.fuchss.swt.widgetVisitor.WidgetVisitor;
import org.fuchss.swt.widgetVisitor.visitors.initializers.ButtonInitalizer;
import org.fuchss.swt.widgetVisitor.visitors.initializers.ComboIntializer;
import org.fuchss.swt.widgetVisitor.visitors.initializers.Initializer;
import org.fuchss.swt.widgetVisitor.visitors.initializers.TextInitializer;

public class Disabler implements WidgetVisitor {

	private Object obj;
	private Map<Class<?>, Initializer> initializers;

	private String state;

	public Disabler(String state) {
		this.state = state;
	}

	@Override
	public void visit(Composite composite) {
		this.disableObject(composite);
	}

	@Override
	public void visit(Dialog dialog) {
		this.disableObject(dialog);
	}

	private void initInitializers() {
		this.initializers = new HashMap<>();
		this.initializers.put(Button.class, new ButtonInitalizer(this.obj));
		this.initializers.put(Combo.class, new ComboIntializer(this.obj));
		this.initializers.put(Text.class, new TextInitializer(this.obj));
	}

	private void disableObject(Object obj) {
		this.obj = obj;
		this.initInitializers();
		this.disableContent();
	}

	private void disableContent() {
		Field[] fields = this.obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			this.disableField(field);
			this.hideField(field);
		}

	}

	private void disableField(Field field) {
		try {
			field.setAccessible(true);
			Type type = field.getGenericType();
			Initializer initializer = this.initializers.get(type);
			if (initializer != null) {
				initializer.setFieldObject(field.get(this.obj), field.getName());
				initializer.disableField(this.state);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void hideField(Field field) {
		try {
			field.setAccessible(true);
			Type type = field.getGenericType();
			Initializer initializer = this.initializers.get(type);
			if (initializer != null) {
				initializer.setFieldObject(field.get(this.obj), field.getName());
				initializer.hideField(this.state);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package org.fuchss.swt.widgetVisitor.visitors.initializers;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.fuchss.swt.widgetVisitor.visitors.annotation.Item;
import org.fuchss.swt.widgetVisitor.visitors.annotation.Resource;

public class Initializer {

	protected ResourceBundle resource;
	protected String prefix;

	public Initializer(Object obj) {
		Resource resource = obj.getClass().getAnnotation(Resource.class);
		this.prefix = (obj.getClass().getAnnotation(Item.class) == null) ? "" : obj.getClass().getAnnotation(Item.class).item();
		if (resource == null) {
			this.resource = null;
		} else {
			this.resource = ResourceBundle.getBundle(resource.res());
		}
	}

	protected String[] getStringArray(String name, String extension, int size) {
		try {
			String[] str = this.resource.getString(name + "." + extension).split(",");
			return (str.length >= size ? str : null);
		} catch (Exception e) {
		}
		return null;
	}

	protected String getString(String name, String extension) {
		try {
			return this.resource.getString(name + "." + extension);
		} catch (MissingResourceException e) {
		}
		return null;
	}

	protected Integer[] getIntegerArray(String name, String extension, int size) {
		try {
			String[] str = this.resource.getString(name + "." + extension).split(",");
			Integer[] integer = new Integer[str.length];
			for (int idx = 0; idx < str.length; idx++) {
				integer[idx] = Integer.parseInt(str[idx]);
			}
			return (integer.length >= size ? integer : null);
		} catch (Exception e) {
		}
		return null;
	}

	protected Integer getInteger(String name, String extension) {
		try {
			String str = this.resource.getString(name + "." + extension);
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return null;
	}

	public String getHeader() {
		return this.getString(this.prefix, "header");
	}

	public void setFieldObject(Object fieldObject, String name) {
	}

	public void initializeField() {
	}

	public void disableField(String state) {
	}

	public void hideField(String state) {
	}

}

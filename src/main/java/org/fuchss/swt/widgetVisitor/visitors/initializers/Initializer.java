package org.fuchss.swt.widgetVisitor.visitors.initializers;

import java.lang.annotation.Annotation;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.fuchss.swt.widgetVisitor.visitors.annotation.Elem;
import org.fuchss.swt.widgetVisitor.visitors.annotation.Resource;

public class Initializer {

	protected ResourceBundle resource;
	protected String prefix;

	public Initializer(Object obj) {
		Resource resource = this.getAnnotation(obj.getClass(), Resource.class);
		Elem item = this.getAnnotation(obj.getClass(), Elem.class);
		this.prefix = (item == null) ? "" : item.value();
		if (resource == null) {
			this.resource = null;
		} else {
			this.resource = ResourceBundle.getBundle(resource.value());
		}
	}

	private <A extends Annotation> A getAnnotation(Class<?> clazz, Class<A> type) {
		if ((clazz == null) || clazz.equals(Object.class))
			return null;
		A annotation = clazz.getAnnotation(type);
		return (annotation != null) ? annotation : this.getAnnotation(clazz.getSuperclass(), type);
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

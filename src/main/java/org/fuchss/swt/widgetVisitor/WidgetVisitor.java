package org.fuchss.swt.widgetVisitor;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.fuchss.swt.widgetVisitor.visitors.annotation.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface WidgetVisitor {
	void visit(Composite composite);

	void visit(Dialog dialog);

	default void possibleFields(Class<?> clazz, List<Field> possibleFields) {
		possibleFields.addAll(Arrays.asList(clazz.getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(Id.class)).collect(Collectors.toList()));
		Class<?> nextClass = clazz.getSuperclass();
		if ((nextClass == null) || nextClass.equals(Object.class))
			return;
		possibleFields(nextClass, possibleFields);
	}

	default String propId(Field field) {
		String id = (id = field.getAnnotation(Id.class).value()).isEmpty() ? field.getName() : id;
		return id;
	}

}

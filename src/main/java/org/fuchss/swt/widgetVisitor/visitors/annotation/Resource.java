package org.fuchss.swt.widgetVisitor.visitors.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Resource {
	String value();
}

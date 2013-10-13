package com.ag.spring_extensions.properties;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field to be set from the given property value.
 * If the property changes during runtime then the ReloadablePropertyPostProcessor will reset
 * the specified field with the updated property value.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ReloadableProperty {

	String value();

}

package com.ag.spring_extensions.properties.testBeans;

import org.springframework.stereotype.Component;

import com.ag.spring_extensions.properties.ReloadableProperty;

@Component
public class ReloadingAutowiredPropertyBean {

	@ReloadableProperty("dynamicProperty.stringValue")
	private String stringProperty;

	public String getStringProperty() {
		return this.stringProperty;
	}
}

package com.ag.spring_extensions.properties.testBeans;

import org.springframework.stereotype.Component;

import com.ag.spring_extensions.properties.ReloadableProperty;

@Component
public class FinalFieldBean {

	@SuppressWarnings("unused")
	@ReloadableProperty("dynamicProperty.intValue")
	private final Integer intObjectProperty = 999;
}

package com.ag.spring_extensions.properties.testBeans;

import org.springframework.stereotype.Component;

import com.ag.spring_extensions.properties.ReloadableProperty;

@Component
public class MissingProperty {

	@SuppressWarnings("unused")
	@ReloadableProperty("does.not.exist")
	private String hasNoDefaultValue;

}

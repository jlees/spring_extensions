package com.ag.spring_extensions.properties.testBeans;

import org.joda.time.Period;
import org.springframework.stereotype.Component;

import com.ag.spring_extensions.properties.ReloadableProperty;

@Component
public class BadValue {

	@SuppressWarnings("unused")
	@ReloadableProperty("invalid.period")
	private Period period;

}

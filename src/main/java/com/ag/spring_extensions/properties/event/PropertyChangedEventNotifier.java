package com.ag.spring_extensions.properties.event;

import com.ag.spring_extensions.properties.bean.PropertyModifiedEvent;
import com.ag.spring_extensions.properties.internal.ReloadablePropertyPostProcessor;

public interface PropertyChangedEventNotifier {

	void post(PropertyModifiedEvent propertyChangedEvent);

	void unregister(ReloadablePropertyPostProcessor reloadablePropertyProcessor);

	void register(ReloadablePropertyPostProcessor reloadablePropertyProcessor);

}

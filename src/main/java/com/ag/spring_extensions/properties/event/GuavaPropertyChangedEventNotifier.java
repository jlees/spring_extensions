package com.ag.spring_extensions.properties.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;
import com.ag.spring_extensions.properties.bean.PropertyModifiedEvent;
import com.ag.spring_extensions.properties.internal.ReloadablePropertyPostProcessor;

/**
 * Implementation of {@link PropertyChangedEventNotifier} that uses an instance
 * of Guava's EventBus to post PropertyModifiedEvent instances
 */

@Component("reloadablePropertyEventNotifier")
public class GuavaPropertyChangedEventNotifier implements PropertyChangedEventNotifier {

	private final EventBus eventBus;

	public GuavaPropertyChangedEventNotifier()
    {
        super();
        this.eventBus = new EventBus();
	}

	@Override
	public void post(final PropertyModifiedEvent propertyChangedEvent)
    {
		this.eventBus.post(propertyChangedEvent);
	}

	@Override
	public void unregister(final ReloadablePropertyPostProcessor ReloadablePropertyPostProcessor)
    {
		this.eventBus.unregister(ReloadablePropertyPostProcessor);
	}

	@Override
	public void register(final ReloadablePropertyPostProcessor ReloadablePropertyPostProcessor)
    {
		this.eventBus.register(ReloadablePropertyPostProcessor);
	}

}

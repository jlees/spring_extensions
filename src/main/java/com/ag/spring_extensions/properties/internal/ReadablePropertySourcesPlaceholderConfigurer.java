package com.ag.spring_extensions.properties.internal;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.ag.spring_extensions.properties.bean.PropertyModifiedEvent;
import com.ag.spring_extensions.properties.event.PropertyChangedEventNotifier;
import com.ag.spring_extensions.properties.internal.PropertiesWatcher.EventPublisher;
import com.ag.spring_extensions.properties.resolver.PropertyResolver;

/**
 * Extension of {@link PropertySourcesPlaceholderConfigurer} that can react to changes in the
 * resources specified. The watching process does not start by default, it is triggered
 * by calling <code>ReadablePropertySourcesPlaceholderConfigurer.startWatching()</code>
 */
public class ReadablePropertySourcesPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer implements EventPublisher {

	protected static Logger log = LoggerFactory.getLogger(ReadablePropertySourcesPlaceholderConfigurer.class);

	private final PropertyChangedEventNotifier eventNotifier;
	private final PropertyResolver propertyResolver;

	private Properties properties;
	private Resource[] locations;

	@Autowired
	public ReadablePropertySourcesPlaceholderConfigurer(final PropertyChangedEventNotifier eventNotifier, final PropertyResolver propertyResolver) {
		this.eventNotifier = eventNotifier;
		this.propertyResolver = propertyResolver;
	}

	@Override
	protected void loadProperties(final Properties props) throws IOException {
		super.loadProperties(props);
		this.properties = props;
	}

	@Override
	public void setLocations(final Resource[] locations) {
		super.setLocations(locations);
		this.locations = locations;
	}

	@Override
	public void onResourceChanged(final Resource resource) {
        final Properties reloadedProperties = new Properties();
        for (Resource currentResource : this.locations){
            try {
                Properties currentResourceProperties = PropertiesLoaderUtils.loadProperties(currentResource);
                for (Object propertyKey : currentResourceProperties.keySet()){
                    reloadedProperties.put(propertyKey, currentResourceProperties.get(propertyKey));
                }
            } catch (final IOException e) {
                log.error("Failed to reload properties file once change", e);
            }
        }
        for (final String property : this.properties.stringPropertyNames()) {

            final String oldValue = this.properties.getProperty(property);
            final String newValue = reloadedProperties.getProperty(property);

            if (propertyExistsAndNotNull(property, newValue) && propertyChange(oldValue, newValue)) {

                // Update locally stored copy of properties
                this.properties.setProperty(property, newValue);

                // Post change event to notify any potential listeners
                this.eventNotifier.post(new PropertyModifiedEvent(property, oldValue, newValue));
            }
        }
	}

	public Properties getProperties() {
		return this.properties;
	}

	public void startWatching() {
		if (null == this.eventNotifier) {
			throw new BeanInitializationException("Event bus not setup, you should not be calling this method...!");
		}
		try {
			// Here we actually create and set a FileWatcher to monitor the given locations
			Executors.newSingleThreadExecutor()
				.execute(new PropertiesWatcher(this.locations, this));
		}
		catch (final IOException e) {
			log.error("Unable to start properties file watcher", e);
		}
	}

	public Object resolveProperty(final Object property) {
		final Object resolvedPropertyValue = this.properties.get(this.propertyResolver.resolveProperty(property));
		if (this.propertyResolver.requiresFurtherResolution(resolvedPropertyValue)) {
			return resolveProperty(resolvedPropertyValue);
		}
		return resolvedPropertyValue;
	}

	private boolean propertyChange(final String oldValue, final String newValue) {
		return null == oldValue || !oldValue.equals(newValue);
	}

	private boolean propertyExistsAndNotNull(final String property, final String newValue) {
		return this.properties.containsKey(property) && null != newValue;
	}

}

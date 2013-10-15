package com.ag.spring_extensions.properties.resolver;

import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Implementation of a {@link PropertyResolver} resolving property keys via substitution.
 * Substitution occurs only for properly formatted markers e.g
 * <code>${...}</code>
 * 
 * <pre>
 * project.property 		    = PropertyValue
 * project.property.substitute  = ${project.property}
 * </pre>
 *
 */
@Component("reloadablePropertyPropertyResolver")
public class SubstitutingPropertyResolver implements PropertyResolver {

	protected static Logger log = LoggerFactory.getLogger(SubstitutingPropertyResolver.class);

	@Override
	public String resolveProperty(final Object property)
    {
        final String stringProperty = (property == null) ? "" : property.toString();
		// if property is a ${} then substitute it for the property it refers to
		final String resolvedProperty =
                (stringProperty.length() > 2 && propertyRequiresSubstitution(stringProperty))
				    ? stringProperty.substring(2, stringProperty.length() - 1)
				    : stringProperty;

		log.info("Property Resolved from [{}] to [{}]", new Object[] { property, resolvedProperty });
		return resolvedProperty;
	}

	@Override
	public boolean requiresFurtherResolution(final Object property)
    {
        final String stringProperty = (property == null) ? "" : property.toString();
		final boolean propertyRequiresSubstitution = propertyRequiresSubstitution(stringProperty);
		if (propertyRequiresSubstitution) {
			log.info("Further resolution required for property value [{}]", new Object[] { property });
		}
		return propertyRequiresSubstitution;
	}

	/**
	 * Tests whether the given property is a ${...} property and therefore requires further resolution
	 */
	private boolean propertyRequiresSubstitution(final String property)
    {
        return Objects.firstNonNull(property, "").startsWith("${") && property.endsWith("}");
	}
}

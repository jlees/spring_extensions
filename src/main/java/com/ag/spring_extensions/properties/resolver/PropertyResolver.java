package com.ag.spring_extensions.properties.resolver;

/**
 * Interface for applying any special property resolution techniques on the given object,
 * see {@link SubstitutingPropertyResolver} for default implementation
 */
public interface PropertyResolver {

	/**
	 * @param property The property to resolve by substitution, if required
	 * @return The result of the property resolution, or the property itself if no substitution was required
	 */
	String resolveProperty(final Object property);

	/**
	 * Used to check whether a property requires further resolution
	 * 
	 * @param property The property to resolve by substitution, if required
	 * @return true if the chosen {@link PropertyResolver} performs custom resolution
	 */
	boolean requiresFurtherResolution(final Object property);

}

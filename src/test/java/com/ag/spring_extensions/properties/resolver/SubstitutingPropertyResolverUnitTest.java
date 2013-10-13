package com.ag.spring_extensions.properties.resolver;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.ag.spring_extensions.properties.resolver.SubstitutingPropertyResolver;

public class SubstitutingPropertyResolverUnitTest {

	private SubstitutingPropertyResolver substitutingPropertyResolver;

	@Before
	public void setUp() throws Exception {
		this.substitutingPropertyResolver = new SubstitutingPropertyResolver();
	}

	@Test
	public void testResolveProperty() {
		assertThat(this.substitutingPropertyResolver.resolveProperty(""), is(""));
		assertThat(this.substitutingPropertyResolver.resolveProperty("project.property"), is("project.property"));
		assertThat(this.substitutingPropertyResolver.resolveProperty("${project.property}"), is("project.property"));
	}

	@Test
	public void expectedNPEWhenNullPropertyResolved() {
		assertThat(this.substitutingPropertyResolver.resolveProperty(null), is(""));
	}
}

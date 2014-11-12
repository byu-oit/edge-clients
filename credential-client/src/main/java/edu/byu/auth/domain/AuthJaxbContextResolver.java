package edu.byu.auth.domain;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;

import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 08/29/2014
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 08/29/2014
 */
public class AuthJaxbContextResolver implements ContextResolver<JAXBContext> {

	private final JAXBContext context;
	private final Set<Class> classes;

	public AuthJaxbContextResolver() {
		final Class[] classArray = {Credential.class, Identity.class, Nonce.class, WsSession.class, CalendarConverter.class};
		final Set<Class> set = new HashSet<Class>(16, .999999f);
		set.addAll(Arrays.asList(classArray));
		this.classes = Collections.unmodifiableSet(set);
		try {
			this.context = new JSONJAXBContext(JSONConfiguration.mapped().attributeAsElement("*").build(), classArray);
		} catch (final JAXBException e) {
			throw new RuntimeException("Unable to create JAXB Context Resolver", e);
		}
	}

	@Override
	public JAXBContext getContext(final Class<?> type) {
		return classes.contains(type) ? context : null;
	}
}

package edu.byu.edge.client.pro.domain.personSummary;

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
 * Date: 5/25/12
 */
public class JaxbContextResolver implements ContextResolver<JAXBContext> {
	private final JAXBContext context;
	private final Set<Class> classes;

	public JaxbContextResolver() {
		final Class[] classArray = {ClassType.class, ClassesType.class, ContactInformationType.class, DateHiredType.class, EmployeeInformationType.class, ErrorType.class, ErrorsType.class, IdentifiersType.class, JaxbContextResolver.class, MailingAddressType.class, NamesType.class, ObjectFactory.class, PersonSummaryLineType.class, PersonSummaryServiceType.class, PersonalInformationType.class, RelationshipType.class, RelationshipsType.class, RequestType.class, ResponseType.class, SecondaryRoleType.class, StudentInformationType.class, UniversityAffiliationType.class, VisaType.class, WorkAddressType.class, ClassType.class, ClassesType.class, ContactInformationType.class, DateHiredType.class, EmployeeInformationType.class, ErrorType.class, ErrorsType.class, IdentifiersType.class, JaxbContextResolver.class, MailingAddressType.class, NamesType.class, ObjectFactory.class, PersonSummaryLineType.class, PersonSummaryServiceType.class, PersonalInformationType.class, RelationshipType.class, RelationshipsType.class, RequestType.class, ResponseType.class, SecondaryRoleType.class, StudentInformationType.class, UniversityAffiliationType.class, VisaType.class, WorkAddressType.class};
		this.classes = Collections.unmodifiableSet(new HashSet<Class>(Arrays.asList(classArray)));
		try {
			this.context = new JSONJAXBContext(JSONConfiguration.mapped().build(), classArray);
		} catch (JAXBException e) {
			throw new RuntimeException("Unable to create JAXB Context Resolver", e);
		}
	}

	@Override
	public JAXBContext getContext(final Class<?> type) {
		return classes.contains(type) ? context : null;
	}
}

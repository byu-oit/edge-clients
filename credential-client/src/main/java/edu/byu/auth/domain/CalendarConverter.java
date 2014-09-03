package edu.byu.auth.domain;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 6/7/12
 */
public class CalendarConverter extends XmlAdapter<XMLGregorianCalendar, Calendar> {

	@Override
	public Calendar unmarshal(final XMLGregorianCalendar v) throws Exception {
		if (v == null) return null;
		return v.toGregorianCalendar();
	}

	@Override
	public XMLGregorianCalendar marshal(final Calendar v) throws Exception {
		if (v == null) return null;
		final DatatypeFactory factory = DatatypeFactory.newInstance();
		if (GregorianCalendar.class.isAssignableFrom(v.getClass())) {
			return factory.newXMLGregorianCalendar((GregorianCalendar) v);
		}
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(v.getTime());
		return factory.newXMLGregorianCalendar(cal);
	}
}

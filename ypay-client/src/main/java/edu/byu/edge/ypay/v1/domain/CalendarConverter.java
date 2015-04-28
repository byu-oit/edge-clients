package edu.byu.edge.ypay.v1.domain;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by eric on 4/28/15.
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

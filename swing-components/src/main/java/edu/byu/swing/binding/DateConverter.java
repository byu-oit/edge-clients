package edu.byu.swing.binding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author tylers2
 */
public class DateConverter extends Converter<Date, String> {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    private final static Logger LOG = Logger.getLogger(DateConverter.class);

    @Override
    public Date convertReverse(String arg0) {
        try {
            return sdf.parse(arg0);
        } catch (ParseException pe) {
            LOG.warn("Unable to parse date [" + arg0 + "]");
        }
        return null;
    }

    @Override
    public String convertForward(Date arg0) {
        return sdf.format(arg0);
    }
}
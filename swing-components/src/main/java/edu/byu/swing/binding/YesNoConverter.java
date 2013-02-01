package edu.byu.swing.binding;

import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author jmooreoa
 */
public class YesNoConverter extends Converter<Boolean, String> {

    @Override
    public String convertForward(Boolean value) {
        return (value) ? "Yes" : "No";
    }

    @Override
    public Boolean convertReverse(String value) {
        return value.equalsIgnoreCase("Yes");
    }

}

package edu.byu.swing.binding;

import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author jmooreoa
 */
public class IntegerConverter extends Converter<Integer, String> {

        public IntegerConverter() {
        }

        @Override
        public String convertForward(Integer value) {
            return value.toString();
        }

        @Override
        public Integer convertReverse(String value) {
            return Integer.valueOf(value);
        }
}

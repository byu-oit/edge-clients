package edu.byu.swing.binding;

import java.math.BigDecimal;
import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author jmooreoa
 */
public class BigDecimalConverter extends Converter <BigDecimal, String>{

    @Override
    public String convertForward(BigDecimal value) {
        return value.toPlainString();
    }

    @Override
    public BigDecimal convertReverse(String value) {
        return new BigDecimal(value);
    }

}

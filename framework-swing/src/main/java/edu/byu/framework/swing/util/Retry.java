package edu.byu.framework.swing.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author jmooreoa
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Retry {
    public static final int MAX_RETRIES = 10;
    public static final int MIN_RETRIES = 0;
    Class<? extends Throwable>[] retry() default {Throwable.class};
    Class<? extends Throwable>[] die() default {};
    int tries() default 3;
}

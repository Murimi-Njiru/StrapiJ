package co.ke.tsunairo.strapij.annotations;

import java.lang.annotation.*;

/**
 * @author Murimi Njiru
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.FIELD})
public @interface Attribute {
	String alias() default "";
	boolean dataId() default false;
}

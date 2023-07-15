package co.ke.tsunairo.strapij.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Murimi Njiru
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Attribute {
	public PopulateFields field() default PopulateFields.RELATION;
	public PopulateEntries entry() default PopulateEntries.LIST;
	public Class<?> mapper() default Object.class;

}

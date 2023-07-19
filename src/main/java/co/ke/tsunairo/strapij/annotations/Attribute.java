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
	PopulateFields field() default PopulateFields.NONE;
	PopulateEntries entry() default PopulateEntries.NONE;
	Class<?> componentMapper() default Object.class;
	Class<?>[] dynamicZoneMappers() default {};
	String alias() default "";
}

package co.ke.tsunairo.strapij.annotations;

import java.lang.annotation.*;

/**
 * @author Murimi Njiru
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.FIELD})
public @interface Attribute {
	String alias() default "";
	boolean useDataId() default false;
	Class<?>[] dynamicZoneMappers() default {};
	Class<?> componentMapper() default Class.class;
	String mediaHost() default "";
	boolean isRelation() default false;
	boolean isSubComponent() default false;
}

package co.ke.tsunairo.strapij.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Media {
	String host() default "";
	MediaFormat format() default MediaFormat.NONE;
	MediaFormat[] formats() default {};
}
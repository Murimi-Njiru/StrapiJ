package co.ke.tsunairo.strapij.annotations;

import java.lang.reflect.Field;

/**
 * @author Murimi Njiru
 */

public class AnnotationProcessor {
	public PopulateFields getField(Field field) throws Exception {
		String fieldType = "";
		field.setAccessible(true);
		if (field.isAnnotationPresent(Attribute.class)) {
			return field.getAnnotation(Attribute.class).field();
		}

		return null;
	}

	public PopulateEntries getEntry(Field field) throws Exception {
		field.setAccessible(true);
		if (field.isAnnotationPresent(Attribute.class)) {
			return field.getAnnotation(Attribute.class).entry();
		}

		return null;
	}

	public String getContentApiId(Class<?> clazz) {
		String apiId = "";
		if (clazz.isAnnotationPresent(Content.class)) {
			apiId = clazz.getAnnotation(Content.class).apiId();
		}

		return apiId;
	}

	public Class<?> getMapper(Field field) {
		Class<?> entryType = null;
		field.setAccessible(true);
		if (field.isAnnotationPresent(Attribute.class)) {
			entryType = field.getAnnotation(Attribute.class).mapper();
		}

		return entryType;
	}

	public String getAlias(Field field) {
		String alias = "";
		field.setAccessible(true);
		if (field.isAnnotationPresent(Attribute.class)) {
			alias = field.getAnnotation(Attribute.class).alias();
		}

		return alias;
	}
}

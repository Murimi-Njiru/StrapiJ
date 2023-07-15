package co.ke.tsunairo.strapij.annotations;

import java.lang.reflect.Field;

/**
 * @author Murimi Njiru
 */

public class AnnotationProcessor {
	public String getField(Field field) throws Exception {
		String fieldType = "";
		field.setAccessible(true);
		if (field.isAnnotationPresent(Attribute.class)) {
			fieldType = field.getAnnotation(Attribute.class).field().toString();
		}

		return fieldType;
	}

	public String getEntry(Field field) throws Exception {
		String entryType = "";
		field.setAccessible(true);
		if (field.isAnnotationPresent(Attribute.class)) {
			entryType = field.getAnnotation(Attribute.class).entry().toString();
		}

		return entryType;
	}

	public String getContentApiId(Class<?> clazz) {
		String apiId = "";
		if (clazz.isAnnotationPresent(Content.class)) {
			apiId = clazz.getAnnotation(Content.class).apiId();
		}

		return apiId;
	}

	public Class<?> getMapper(Field field) {
		Class entryType = null;
		field.setAccessible(true);
		if (field.isAnnotationPresent(Attribute.class)) {
			entryType = field.getAnnotation(Attribute.class).mapper();
		}

		return entryType;
	}
}

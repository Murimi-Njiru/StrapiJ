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

	public Class<?> getComponentMapper(Field field) {
		Class<?> clazz = null;
		field.setAccessible(true);
		if (field.isAnnotationPresent(Attribute.class)) {
			clazz = field.getAnnotation(Attribute.class).componentMapper();
		}

		return clazz;
	}

	public Class<?>[] getDynamicZoneMappers(Field field) {
		Class<?>[] clazzes = null;
		field.setAccessible(true);
		if (field.isAnnotationPresent(Attribute.class)) {
			clazzes = field.getAnnotation(Attribute.class).dynamicZoneMappers();
		}

		return clazzes;
	}

	public String getAlias(Field field) {
		String alias = "";
		field.setAccessible(true);
		if (field.isAnnotationPresent(Attribute.class)) {
			alias = field.getAnnotation(Attribute.class).alias();
		}

		return alias;
	}

	public String getComponentName(Class<?> clazz) {
		String name = "";
		if (clazz.isAnnotationPresent(ComponentMapper.class)) {
			name = clazz.getAnnotation(ComponentMapper.class).name();
		}

		return name;
	}
}

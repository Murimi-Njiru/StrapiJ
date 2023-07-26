package co.ke.tsunairo.strapij.annotations;

import java.lang.reflect.Field;

/**
 * @author Murimi Njiru
 */

public class AnnotationProcessor {

	public String getContentApiId(Class<?> clazz) {
		String apiId = "";
		if (clazz.isAnnotationPresent(Content.class)) {
			apiId = clazz.getAnnotation(Content.class).apiId();
		}

		return apiId;
	}

	public PopulateEntries getEntries(Field field) throws Exception {
		field.setAccessible(true);
		if (field.isAnnotationPresent(Attribute.class)) {
			return field.getAnnotation(Attribute.class).entries();
		}

		return null;
	}

	public Class<?> getComponentMapper(Field field) {
		Class<?> clazz = null;
		field.setAccessible(true);
		if (field.isAnnotationPresent(Component.class)) {
			clazz = field.getAnnotation(Component.class).mapper();
		}

		return clazz;
	}

	public Class<?>[] getDynamicZoneMappers(Field field) {
		Class<?>[] clazzes = null;
		field.setAccessible(true);
		if (field.isAnnotationPresent(DynamicZone.class)) {
			clazzes = field.getAnnotation(DynamicZone.class).mappers();
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

	public String getMediaHost(Field field) {
		String host = "";
		field.setAccessible(true);
		if (field.isAnnotationPresent(Media.class)) {
			host = field.getAnnotation(Media.class).host();
		}

		return host;
	}

	public String getFieldType(Field field) {
		if (field.isAnnotationPresent(Relation.class)) {
			return "relation";
		}
		else if (field.isAnnotationPresent(Component.class)) {
			return "component";
		}
		else if (field.isAnnotationPresent(DynamicZone.class)) {
			return "dynamic_zone";
		}
		else if (field.isAnnotationPresent(Media.class)) {
			return "media";
		}
		else if (field.isAnnotationPresent(SubComponent.class)) {
			return "sub_component";
		}
		else {
			return null;
		}
	}

	public String getComponentName(Class<?> clazz) {
		String name = "";
		if (clazz.isAnnotationPresent(ComponentMapper.class)) {
			name = clazz.getAnnotation(ComponentMapper.class).name();
		}

		return name;
	}
}

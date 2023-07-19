package co.ke.tsunairo.strapij.parsers;

import co.ke.tsunairo.strapij.annotations.AnnotationProcessor;
import co.ke.tsunairo.strapij.beans.Data;
import co.ke.tsunairo.strapij.beans.Entries;
import co.ke.tsunairo.strapij.beans.Entry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Murimi Njiru
 */

public class Parser<Type> {
	private final Class<Type> clazz;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final AnnotationProcessor annotationProcessor =new AnnotationProcessor();

	public Parser(Class<Type> clazz) {
		this.clazz = clazz;
	}

	private <FieldClass> FieldClass parseField(Object value, Field field) throws Exception {
		if(annotationProcessor.getField(field) != null && value != null) {
			return switch (annotationProcessor.getField(field)) {
				case MEDIA, COMPONENT -> parseComponent(value, field);
				case DYNAMIC_ZONE -> parseDynamicZone(value, field);
				case RELATION_MANY -> parseRelation(value, field);
				case SUB_COMPONENT -> parseSubComponent(value, field);
				case RELATION_ONE -> parseSingleRelation(value, field);
				case NONE -> (FieldClass) value;
			};
		}
		else  {
			return (FieldClass) value;
		}
	}

	private <Component> Component parseComponent(Object value, Field field) throws Exception {

		switch (annotationProcessor.getEntry(field)) {
			case LIST -> {
				List<Map<String, Object>> components = (List<Map<String, Object>>) value;

				return (Component) components.stream().map(component -> {
					try {
						return mapComponent(component, new Class<?>[] {annotationProcessor.getComponentMapper(field)});
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
					return null;
				}).collect(Collectors.toList());
			}
			case SINGLE -> {
				Map<String, Object> component = (Map<String, Object>) value;
				return (Component) mapComponent(component, new Class<?>[] {annotationProcessor.getComponentMapper(field)});
			}
			case NONE -> {
				return (Component) value;
			}
			default -> throw new IllegalStateException("Unexpected value: " + annotationProcessor.getEntry(field));
		}
	}
	private <DynamicZone> DynamicZone parseDynamicZone(Object value, Field field) throws Exception {
		List<Map<String, Object>> components = (List<Map<String, Object>>) value;

		switch (annotationProcessor.getEntry(field)) {
			case LIST -> {
				return (DynamicZone) components.stream().map(component -> {
					try {
						return mapComponent(component,  annotationProcessor.getDynamicZoneMappers(field));
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
					return null;
				});
			}
			case SINGLE -> {
				return (DynamicZone) mapComponent(components.get(0), annotationProcessor.getDynamicZoneMappers(field));
			}
		}
		return null;
	}

	private String getClassName(ParameterizedType pType) {
		if(pType.getActualTypeArguments().length > 1) {
			//Map<>
			return pType.getActualTypeArguments()[1].getTypeName();
		}
		else {
			//List<>
			return pType.getActualTypeArguments()[0].getTypeName();
		}
	}

	private Class<?> getFieldParameterizedClass(Field field) {
		java.lang.reflect.Type type = field.getGenericType();

		Class<?> parameterClass = null;
		if (type instanceof ParameterizedType pType) {
			try {
				parameterClass = Class.forName(getClassName(pType));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return parameterClass;
	}

	private <Relation> Relation parseSingleRelation(Object value, Field field) throws JsonProcessingException {
		Map<String, Object> valueMap = (Map<String, Object>) value;
		Map<Object, Object> data = (Map<Object, Object>) valueMap.get("data");

		return (Relation) parseAttributes((Map<String, Object>) data.get("attributes"), field.getType());
	}

	private <Relation> Relation parseListRelation(Object value, Field field) throws JsonProcessingException {
		Map<String, Object> valueMap = (Map<String, Object>) value;
		List<Map<Object, Object>> data = (List<Map<Object, Object>>) valueMap.get("data");

		return (Relation) data.stream().map(datum -> {
			try {
				return parseAttributes((Map<String, Object>) datum.get("attributes"), getFieldParameterizedClass(field));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
	}

 	private <Relation> Relation parseRelation(Object value, Field field) throws Exception {
	    if (value != null) {
			switch (annotationProcessor.getEntry(field)) {
				case SINGLE -> {
					return parseSingleRelation(value, field);
				}
				case LIST -> {
					return parseListRelation(value, field);
				}
				case NONE -> {
					return (Relation) value;
				}
			}
	    }
		else {
			return null;
	    }
	    return null;
    }

	private <SubComponent> SubComponent parseSubComponent(Object value, Field field) throws Exception {
		switch (annotationProcessor.getEntry(field)) {
			case LIST -> {
				List<Map<String, Object>> components = (List<Map<String, Object>>) value;
				return (SubComponent)components.stream().map(component -> {
					try {
						return parseAttributes(component, getFieldParameterizedClass(field));
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}).collect(Collectors.toList());
			}
			case SINGLE -> {
				Map<String, Object> component = (Map<String, Object>) value;

				return (SubComponent)parseAttributes(component, field.getClass());
			}
		}
		return null;
	}

	private Object mapComponent(Map<String, Object> component, Class<?>[] mappers) throws JsonProcessingException {
		String [] unwantedKeys = {"id", "__component"};
		Map<String, Object> parsedComponent = new HashMap<>();

		try {
			String componentName = (String) component.get("__component");

			Class<?> mapper = Arrays.stream(mappers).filter(mapp -> componentName.equals(annotationProcessor.getComponentName(mapp))).findFirst().orElse(null);
			component.forEach((key, value) -> {
				try {
					if(mapper != null) {
						if(Arrays.stream(mapper.getDeclaredFields()).anyMatch(field -> field.getName().equals(key))) {
							Field field = mapper.getDeclaredField(key);

							parsedComponent.put(key, parseField(value, field));
						}
					}
					else {
						parsedComponent.put(key, value);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		Arrays.stream(unwantedKeys).forEach(component::remove);

		return objectMapper.readValue(new Gson().toJson(parsedComponent), Object.class);
	}

	private <Attribute> Attribute parseAttributes(Map<String, Object> attributes, Class<Attribute> attributeClass) throws JsonProcessingException {
		Map<String, Object> parsedAttributes = new HashMap<>();

		Arrays.stream(attributeClass.getDeclaredFields()).forEach(field -> {
			String fieldKey = annotationProcessor.getAlias(field).isEmpty() ? field.getName() : annotationProcessor.getAlias(field);
			try {
				parsedAttributes.put(field.getName(), parseField(attributes.get(fieldKey), field));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return objectMapper.readValue(new Gson().toJson(parsedAttributes), attributeClass);
	}

	public List<Type> parseCollection(List<Data> data) {
		return data.stream().map(datum -> {
			try {
				return parseAttributes(datum.getAttributes(), clazz);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
	}

	public Type parseSingle(Data data) {
		try {
			return parseAttributes(data.getAttributes(), clazz);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}

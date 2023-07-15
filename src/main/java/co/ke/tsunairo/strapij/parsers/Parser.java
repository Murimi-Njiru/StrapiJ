package co.ke.tsunairo.strapij.parsers;

import co.ke.tsunairo.strapij.annotations.AnnotationProcessor;
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
	private Class<Type> clazz;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private AnnotationProcessor annotationProcessor =new AnnotationProcessor();

	public Parser(Class<Type> clazz) {
		this.clazz = clazz;
	}

	private <FieldClass> FieldClass parseField(Object value, Field field) throws Exception {
		String fieldType = annotationProcessor.getField(field);

		switch(fieldType.toLowerCase()) {
			case "media":

			case "component":
				return parseComponent(value, field);
			case "dynamic_zone":
				return parseDynamicZone(value, field);
			case "relation":
				return parseRelation(value, field);
			default:
				return (FieldClass) value;
		}
	}

	private <Component> Component parseComponent(Object value, Field field) throws Exception {
		if(annotationProcessor.getEntry(field).toLowerCase().equals("single")) {
			Map<String, Object> component = (Map<String, Object>) value;
			return (Component) mapComponent(component, annotationProcessor.getMapper(field));
		}
		else {
			List<Map<String, Object>> components = (List<Map<String, Object>>) value;

			return (Component) components.stream().map(component -> {
				try {
					return mapComponent(component, annotationProcessor.getMapper(field));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				return null;
			});

		}
	}
	private <DynamicZone> DynamicZone parseDynamicZone(Object value, Field field) throws Exception {
		List<Map<String, Object>> components = (List<Map<String, Object>>) value;

		if(annotationProcessor.getEntry(field).toLowerCase().equals("single")) {

			return (DynamicZone) mapComponent(components.get(0), annotationProcessor.getMapper(field));
		}

		return (DynamicZone) components.stream().map(component -> {
			try {
				return mapComponent(component,  annotationProcessor.getMapper(field));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return null;
		});
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

	private Class getFieldParameter(Field field) {
		java.lang.reflect.Type type = field.getGenericType();

		Class parameterClass = null;
		if (type instanceof ParameterizedType pType) {
			try {
				parameterClass = Class.forName(getClassName(pType));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return parameterClass;
	}
	private <Relation> Relation parseRelation(Object value, Field field) throws Exception {
		if(annotationProcessor.getEntry(field).toLowerCase().equals("single")) {
			Map<String, Object> valueMap = (Map<String, Object>) value;
			Map<Object, Object> data = (Map<Object, Object>) valueMap.get("data");
			return (Relation) parseAttributes((Map<String, Object>) data.get("attributes"), "1", field.getClass());
		}
		else {
			Map<String, Object> valueMap = (Map<String, Object>) value;
			List<Map<Object, Object>> data = (List<Map<Object, Object>>) valueMap.get("data");
			return (Relation) data.stream().map(datum -> {
				try {
					return parseAttributes((Map<String, Object>) datum.get("attributes"), "1", getFieldParameter(field));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				return null;
			}).collect(Collectors.toList());
		}
	}

	private Object mapComponent(Map<String, Object> component, Class mapper) throws JsonProcessingException {
		String [] unwantedKeys = {"id", "__component"};
		Arrays.stream(unwantedKeys).forEach(component::remove);
		Map<String, Object> parsedComponent = new HashMap<>();
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
		return objectMapper.readValue(new Gson().toJson(parsedComponent), Object.class);
	}

	private <Attribute> Attribute parseAttributes(Map<String, Object> attributes, String id, Class<Attribute> attributeClass) throws JsonProcessingException {
		Map<String, Object> parsedAttributes = new HashMap<>();
		parsedAttributes.put("id", id);
		attributes.forEach((key, value) -> {
			try {
				if(Arrays.stream(attributeClass.getDeclaredFields()).anyMatch(field -> field.getName().equals(key))) {
					Field field = attributeClass.getDeclaredField(key);

					parsedAttributes.put(key, parseField(value, field));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return objectMapper.readValue(new Gson().toJson(parsedAttributes), attributeClass);
	}

	public List<Type> parseCollection(List<Map<String, Object>> data) {
		return data.stream().map(datum -> {
			try {
				return parseAttributes((Map<String, Object>) datum.get("attributes"), "1", clazz);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
	}

	public Type parseSingle(Map<String, Object> data) {
		try {
			return parseAttributes((Map<String, Object>) data.get("attributes"), "1", clazz);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}

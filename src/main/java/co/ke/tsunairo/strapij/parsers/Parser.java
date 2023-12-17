package co.ke.tsunairo.strapij.parsers;

import co.ke.tsunairo.strapij.annotations.AnnotationProcessor;
import co.ke.tsunairo.strapij.beans.Data;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Murimi Njiru
 */

public class Parser<Type> {
	private final Class<Type> clazz;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final AnnotationProcessor annotationProcessor =new AnnotationProcessor();
	private final String hostUrl;

	public Parser(Class<Type> clazz, String hostUrl) {
		this.clazz = clazz;
		this.hostUrl = hostUrl;
	}

	public List<Type> parseCollection(List<Data> data) {
		return data.stream().map(datum -> {
			try {
				return parseAttributes(datum.getAttributes(), "" + datum.getId(), clazz);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
	}

	public Type parseSingle(Data data) {
		try {
			return parseAttributes(data.getAttributes(), "" + data.getId(), clazz);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private <Attribute> Attribute parseAttributes(Map<String, Object> attributes, String dataId,  Class<Attribute> attributeClass) throws JsonProcessingException {
		Map<String, Object> parsedAttributes = new HashMap<>();

		Arrays.stream(attributeClass.getDeclaredFields()).forEach(field -> {
			String fieldKey = annotationProcessor.getAlias(field).isEmpty() ? field.getName() : annotationProcessor.getAlias(field);
			try {
				if(annotationProcessor.useDataId(field)) {
					parsedAttributes.put(field.getName(), dataId);
				}
				else {
					parsedAttributes.put(field.getName(), parseField(attributes.get(fieldKey), field));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return objectMapper.readValue(new Gson().toJson(parsedAttributes), attributeClass);
	}

	private <FieldClass> FieldClass parseField(Object value, Field field) throws Exception {
		String fieldType = annotationProcessor.getFieldType(field);
		if(fieldType != null && value != null) {
			return switch (fieldType.toUpperCase()) {
				case "MEDIA" -> parseMedia(value, field);
				case "COMPONENT" -> parseComponent(value, field);
				case "DYNAMIC_ZONE" -> parseDynamicZone(value, field);
				case "RELATION" -> parseRelation(value, field);
				case "SUB_COMPONENT" -> parseSubComponent(value, field);
				default -> (FieldClass) value;
			};
		}
		else  {
			return (FieldClass) value;
		}
	}

	private <Media> Media parseMedia(Object value, Field field) throws Exception {
		if (value != null) {
			if(isEntriesList(field)) {
				Map<String, Object> valueMap = (Map<String, Object>) value;
				List<Map<Object, Object>> data = (List<Map<Object, Object>>) valueMap.get("data");

				return (Media) data.stream().map(datum -> {
					try {
						Map<String, Object> attributes = (Map<String, Object>) datum.get("attributes");
						String url = (String) attributes.get("url");
						String host = annotationProcessor.getMediaHost(field).isBlank() ? hostUrl.substring(0, hostUrl.length()-1) : annotationProcessor.getMediaHost(field);
						attributes.put("url", host + url);
						return parseAttributes(attributes, "", getFieldParameterizedClass(field));
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
					return null;
				}).collect(Collectors.toList());
			}
			else {
				Map<String, Object> valueMap = (Map<String, Object>) value;
				Map<Object, Object> data = (Map<Object, Object>) valueMap.get("data");

				Map<String, Object> attributes = (Map<String, Object>) data.get("attributes");
				String url = (String) attributes.get("url");
				String host = annotationProcessor.getMediaHost(field).isBlank() ? hostUrl.substring(0, hostUrl.length()-1) : annotationProcessor.getMediaHost(field);

				attributes.put("url", host + url);
				return (Media) parseAttributes(attributes, "", field.getType());
			}
		}
		return null;
	}

	private <DynamicZone> DynamicZone parseDynamicZone(Object value, Field field) throws Exception {
		if(value != null) {
			List<Map<String, Object>> components = (List<Map<String, Object>>) value;
			return isEntriesList(field) ? (DynamicZone) components.stream().map(component -> {
				try {
					return mapDynamicZoneComponent(component,  annotationProcessor.getDynamicZoneMappers(field));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				return null;
			}) : components.size() > 0 ? (DynamicZone) mapDynamicZoneComponent(components.get(0), annotationProcessor.getDynamicZoneMappers(field)) : null;
		}
		return null;
	}

 	private <Relation> Relation parseRelation(Object value, Field field) throws Exception {
	    if (value != null) {
		    if(isEntriesList(field)) {
			    Map<String, Object> valueMap = (Map<String, Object>) value;
			    List<Map<Object, Object>> data = (List<Map<Object, Object>>) valueMap.get("data");

			    return (Relation) data.stream().map(datum -> {
				    try {
					    return parseAttributes((Map<String, Object>) datum.get("attributes"), datum.get("id").toString(), getFieldParameterizedClass(field));
				    } catch (JsonProcessingException e) {
					    e.printStackTrace();
				    }
				    return null;
			    }).collect(Collectors.toList());
		    }
		    else {
			    Map<String, Object> valueMap = (Map<String, Object>) value;
			    Map<Object, Object> data = (Map<Object, Object>) valueMap.get("data");

			    return (Relation) parseAttributes((Map<String, Object>) data.get("attributes"), data.get("id").toString(), field.getType());
		    }
	    }
		return null;
    }

	private <Component> Component parseComponent(Object value, Field field) throws Exception {
		if (value != null) {
			if(isEntriesList(field)) {
				List<Map<String, Object>> components = (List<Map<String, Object>>) value;

				return (Component) components.stream().map(component -> {
					try {
						return parseAttributes(component, "", getFieldParameterizedClass(field));
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
					return null;
				}).collect(Collectors.toList());
			}
			else {
				Map<String, Object> component = (Map<String, Object>) value;
				return (Component) parseAttributes(component, "", field.getType());
			}
		}
		return null;
	}

	private Object mapDynamicZoneComponent(Map<String, Object> component, Class<?>[] mappers) throws JsonProcessingException {
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

		Arrays.stream(unwantedKeys).forEach(parsedComponent::remove);

		return objectMapper.readValue(new Gson().toJson(parsedComponent), Object.class);
	}

	private <SubComponent> SubComponent parseSubComponent(Object value, Field field) throws Exception {
		if (value != null) {
			if(isEntriesList(field)) {
				List<Map<String, Object>> components = (List<Map<String, Object>>) value;
				return (SubComponent)components.stream().map(component -> {
					try {
						return parseAttributes(component, "", getFieldParameterizedClass(field));
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}).collect(Collectors.toList());
			}
			else {
				Map<String, Object> component = (Map<String, Object>) value;

				return (SubComponent)parseAttributes(component, "", field.getClass());
			}
		}
		return null;
	}

	private boolean isEntriesList(Field field) throws Exception {
		return Collection.class.isAssignableFrom(field.getType());
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
}

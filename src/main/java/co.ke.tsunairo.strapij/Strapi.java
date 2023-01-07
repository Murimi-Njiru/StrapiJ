package co.ke.tsunairo.strapij;

import co.ke.tsunairo.strapij.retrofit.RetrofitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Observable;
import lombok.SneakyThrows;
import org.apache.commons.text.CaseUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Murimi Njiru
 */
public class Strapi<Attributes> {
	private final Class<Attributes> attributesClass;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final String entryType;
	private StrapiHTTPClient strapiHTTPClient;
	private final String [] components;

	public Strapi(Class<Attributes> attributesClass, String entryType, String [] components) {
		this.attributesClass = attributesClass;
		this.entryType = entryType;
		this.components = components;
	}

	public void initStrapiRestClient(String url) {
		strapiHTTPClient = new RetrofitService().getRetrofitInstance(url).create(StrapiHTTPClient.class);
	}

	public Observable<List<Attributes>> getCollectionEntries (StrapiQuery strapiQuery, Map<String, Class> componentMapper) {
		return strapiHTTPClient.getCollectionEntries(entryType, strapiQuery == null ? new HashMap<>() : strapiQuery.getQuery())
				.map(entriesResponse -> entriesResponse.getData().stream().map(data -> mapAttributesToModel(data.getAttributes(), strapiQuery, componentMapper))
						.collect(Collectors.toList()));
	}

	public Observable<Attributes> getCollectionEntry (String id, StrapiQuery strapiQuery, Map<String, Class> componentMapper) {
		return strapiHTTPClient.getCollectionEntry(entryType, id, strapiQuery.getQuery())
				.map(entryResponse -> mapAttributesToModel(entryResponse.getStrapiData().getAttributes(), strapiQuery, componentMapper));
	}

	public Observable<Attributes> updateCollectionEntry (String id, StrapiQuery strapiQuery, Map<String, Class> componentMapper) {
		return strapiHTTPClient.updateCollectionEntry(entryType, id, strapiQuery.getQuery())
				.map(entryResponse -> mapAttributesToModel(entryResponse.getStrapiData().getAttributes(), strapiQuery, componentMapper));
	}

	public Observable<Attributes> createCollectionEntry (String id, StrapiQuery strapiQuery, Map<String, Class> componentMapper) {
		return strapiHTTPClient.createCollectionEntry(entryType, id, strapiQuery.getQuery())
				.map(entryResponse -> mapAttributesToModel(entryResponse.getStrapiData().getAttributes(), strapiQuery, componentMapper));
	}

	public Observable<Attributes> deleteCollectionEntry (String id, StrapiQuery strapiQuery, Map<String, Class> componentMapper) {
		return strapiHTTPClient.deleteCollectionEntry(entryType, id, strapiQuery.getQuery())
				.map(entryResponse -> mapAttributesToModel(entryResponse.getStrapiData().getAttributes(), strapiQuery, componentMapper));
	}

	public Observable<Attributes> getSingleEntry (String id, StrapiQuery strapiQuery, Map<String, Class> componentMapper) {
		return strapiHTTPClient.getSingleEntry(id, strapiQuery.getQuery())
				.map(entryResponse -> mapAttributesToModel(entryResponse.getStrapiData().getAttributes(), strapiQuery, componentMapper));
	}

	public Observable<Attributes> updateEntry (String id, StrapiQuery strapiQuery, Map<String, Class> componentMapper) {
		return strapiHTTPClient.updateSingleEntry(id, strapiQuery.getQuery())
				.map(entryResponse -> mapAttributesToModel(entryResponse.getStrapiData().getAttributes(), strapiQuery, componentMapper));
	}

	public Observable<Attributes> createEntry (String id, StrapiQuery strapiQuery, Map<String, Class> componentMapper) {
		return strapiHTTPClient.updateSingleEntry(id, strapiQuery.getQuery())
				.map(entryResponse -> mapAttributesToModel(entryResponse.getStrapiData().getAttributes(), strapiQuery, componentMapper));
	}

	public Observable<Attributes> deleteEntry (String id, StrapiQuery strapiQuery, Map<String, Class> componentMapper) {
		return strapiHTTPClient.deleteSingleEntry(id, strapiQuery.getQuery())
				.map(entryResponse -> mapAttributesToModel(entryResponse.getStrapiData().getAttributes(), strapiQuery, componentMapper));
	}

	@SneakyThrows
	private Attributes mapAttributesToModel(Map<String, Object> rawAttributes, StrapiQuery strapiQuery, Map<String, Class> componentMapper) {
		Map<String, Object> attributes = new HashMap<>();

		if(strapiQuery != null) {
			List<String> fieldsToPopulate = strapiQuery.getPopulateQueries();
			if(!fieldsToPopulate.isEmpty()) {
				return populateRawAttributes(rawAttributes, attributesClass, fieldsToPopulate, componentMapper);
			}
		}
		return objectMapper.readValue(new Gson().toJson(cleanAttributes(rawAttributes, attributesClass)), new TypeReference<Attributes>(){});
	}

	private Map<String, Object> cleanAttributes(Map<String, Object> attributes, Class clazz) {
		String className = clazz.getTypeName().split("\\.")[clazz.getTypeName().split("\\.").length - 1];
		String attributesId = CaseUtils.toCamelCase(className, false)+"Id";
		List<String> unwantedKeys = attributes.keySet().stream().filter(key -> key.endsWith("At") || key.equals("id")).toList();
		unwantedKeys.forEach(attributes::remove);
		if(attributes.get(attributesId) != null) {
			String id = attributes.get(attributesId).toString();
			attributes.put("id", id);
			attributes.remove(attributesId);
		}

		return attributes;
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

	private <T> T populateRawAttributes(Map<String, Object> rawAttributes, Class<T> clazz, List<String> fieldsToPopulate, Map<String, Class> componentMapper) throws JsonProcessingException {
		cleanAttributes(rawAttributes, clazz);
		Map<String, Object> attributes = new HashMap<>();
		Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
			String fieldName = field.getName();

			if(components != null) {
				if(Arrays.stream(components).toList().contains(fieldName)){
					List<Map<String, Object>> rawComponents = (List<Map<String, Object>>) rawAttributes.get(fieldName);

					if(rawComponents.size() > 1) {
						attributes.put(fieldName, rawComponents.stream()
								.map(rawComponent -> convertComponentToMap(rawComponent, fieldsToPopulate, componentMapper))
								.collect(Collectors.toList()));
					}
					else
					{
						attributes.put(fieldName, convertComponentToMap(rawComponents.get(0), fieldsToPopulate, componentMapper));
					}
				}
			}
			else if(fieldsToPopulate.contains(fieldName)) {
				Type type = field.getGenericType();
				Map<String, Object> attributeValueMap = (Map<String, Object>) rawAttributes.get(fieldName);
				// a List<> or a Map<>
				if (type instanceof ParameterizedType pType) {
					List<Map<String, Map<String, Object>>> attributeValueData = (List<Map<String, Map<String, Object>>>) attributeValueMap.get("data");
					Class clazzz = null;

					try {
						clazzz = Class.forName(getClassName(pType));
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

					Class finalClazz = clazzz;
					attributes.put(fieldName, attributeValueData.stream()
							.map(data -> {
								try
								{
									return populateRawAttributes(data.get("attributes"), finalClazz, fieldsToPopulate, componentMapper);
								} catch (JsonProcessingException e)
								{
									e.printStackTrace();
								}
								return null;
							})
							.collect(Collectors.toList()));
				}
				// An Object without <>
				else {
					Map<String, Map<String,Object>> attributeValueDatum = (Map<String, Map<String,Object>>) attributeValueMap.get("data");
					Class clazzz = null;

					try{
						clazzz = Class.forName(type.getTypeName());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					try
					{
						attributes.put(fieldName, populateRawAttributes(attributeValueDatum.get("attributes"), clazzz, fieldsToPopulate, componentMapper));
					} catch (JsonProcessingException e)
					{
						e.printStackTrace();
					}
				}
			}
			else {
				attributes.put(fieldName, rawAttributes.get(fieldName));
			}
		});

		return objectMapper.readValue(new Gson().toJson(attributes), clazz);
	}

	private Map<String, Object> convertComponentToMap(Map<String, Object> component, List<String> fieldsToPopulate, Map<String, Class> componentMapper) {
		String [] unwantedKeys = {"id", "__component"};
		Arrays.stream(unwantedKeys).forEach(component::remove);
		Map<String, Object> componentMap = new HashMap<>();

		component.entrySet().forEach(rawSyncProp -> {
			String key = rawSyncProp.getKey();

			if(componentMapper != null) {
				if(componentMapper.containsKey(key)) {
					Map<String, Object> componentValue = (Map<String, Object>)rawSyncProp.getValue();
					List<Map<String, Map<String, Object>>> componentValueData = (List<Map<String, Map<String, Object>>>) componentValue.get("data");

					componentMap.put(key, componentValueData.stream().map(componentValueDatum -> {
						try
						{
							return populateRawAttributes(componentValueDatum.get("attributes"), componentMapper.get(key), fieldsToPopulate, componentMapper);
						} catch (JsonProcessingException e)
						{
							e.printStackTrace();
						}
						return null;
					}).collect(Collectors.toList()));
				}
				else {
					componentMap.put(key, rawSyncProp.getValue());
				}
			}
			else {
				componentMap.put(key, rawSyncProp.getValue());
			}
		});
		return componentMap;
	}
}

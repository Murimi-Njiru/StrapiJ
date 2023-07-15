package co.ke.tsunairo.strapij;

import co.ke.tsunairo.strapij.annotations.Content;
import co.ke.tsunairo.strapij.clients.CollectionTypeClient;
import co.ke.tsunairo.strapij.clients.SingleTypeClient;
import co.ke.tsunairo.strapij.clients.StrapiClient;
import co.ke.tsunairo.strapij.parsers.Parser;
import co.ke.tsunairo.strapij.services.RetrofitService;
import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Murimi Njiru
 */

public class StrapiJ<ContentType> {
	private String url;
	private Class<ContentType> contentTypeClass;
	private CollectionTypeClient collectionTypeClient;
	private SingleTypeClient singleTypeClient;
	private Parser<ContentType> parser;

	public StrapiJ(Class<ContentType> contentTypeClass, String url) {
		this.contentTypeClass = contentTypeClass;
		this.url = url;

		this.parser = new Parser<>(contentTypeClass);

		initCollectionTypeClient();
		initSingleTypeClient();
	}

	private String getApiId() {
		String apiId = "";
		if (contentTypeClass.isAnnotationPresent(Content.class)) {
			apiId = contentTypeClass.getAnnotation(Content.class).apiId();
		}

		return apiId;
	}

	private void initCollectionTypeClient() {
		collectionTypeClient = new RetrofitService().getRetrofitInstance(url).create(CollectionTypeClient.class);
	}

	private void initSingleTypeClient() {
		singleTypeClient = new RetrofitService().getRetrofitInstance(url).create(SingleTypeClient.class);
	}

	public Observable<List<ContentType>> getCollectionEntries (StrapiQuery strapiQuery) {
		return collectionTypeClient.getEntries(getApiId(), strapiQuery.getQuery())
				.map(entriesResponse -> parser.parseCollection(entriesResponse.getData()));
	}

	public Observable<ContentType> getCollectionEntry (String id, StrapiQuery strapiQuery) {
		return collectionTypeClient.getEntry(getApiId(), id, strapiQuery.getQuery())
				.map(entryResponse -> parser.parseSingle(entryResponse.getData()));
	}
}

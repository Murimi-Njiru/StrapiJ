package co.ke.tsunairo.strapij;

import co.ke.tsunairo.strapij.annotations.AnnotationProcessor;
import co.ke.tsunairo.strapij.clients.CollectionTypeClient;
import co.ke.tsunairo.strapij.clients.SingleTypeClient;
import co.ke.tsunairo.strapij.parsers.Parser;
import co.ke.tsunairo.strapij.services.RetrofitService;
import io.reactivex.rxjava3.core.Observable;
import java.util.List;

/**
 * @author Murimi Njiru
 */

public class StrapiJ<ContentType> {
	private final String url;
	private final Class<ContentType> contentTypeClass;
	private final Parser<ContentType> parser;
	private final AnnotationProcessor annotationProcessor;
	private CollectionTypeClient collectionTypeClient;
	private SingleTypeClient singleTypeClient;

	public StrapiJ(Class<ContentType> contentTypeClass, String url) {
		this.contentTypeClass = contentTypeClass;
		this.url = url;

		this.parser = new Parser<>(contentTypeClass, url);
		this.annotationProcessor = new AnnotationProcessor();
	}

	public void initCollectionTypeClient(String url) {
		collectionTypeClient = new RetrofitService().getRetrofitInstance(url).create(CollectionTypeClient.class);
	}

	public void initSingleTypeClient(String url) {
		singleTypeClient = new RetrofitService().getRetrofitInstance(url).create(SingleTypeClient.class);
	}

	public Observable<List<ContentType>> getCollectionEntries (StrapiQuery strapiQuery) {
		if(collectionTypeClient == null) {
			initCollectionTypeClient(url);
		}
		return collectionTypeClient.getEntries(annotationProcessor.getContentApiId(contentTypeClass), strapiQuery.getQuery())
				.map(entries -> parser.parseCollection(entries.getData()));
	}

	public Observable<ContentType> getCollectionEntry (String id, StrapiQuery strapiQuery) {
		if(collectionTypeClient == null) {
			initCollectionTypeClient(url);
		}
		return collectionTypeClient.getEntry(annotationProcessor.getContentApiId(contentTypeClass), id, strapiQuery.getQuery())
				.map(entry -> parser.parseSingle(entry.getData()));
	}

	public Observable<ContentType> getSingleEntry(StrapiQuery strapiQuery) {
		if(singleTypeClient == null) {
			initSingleTypeClient(url);
		}

		return singleTypeClient.getSingleEntry(annotationProcessor.getContentApiId(contentTypeClass), strapiQuery.getQuery())
				.map(entry -> parser.parseSingle(entry.getData()));
	}
}

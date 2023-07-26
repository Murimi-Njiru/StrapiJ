package co.ke.tsunairo.strapij.clients;

import co.ke.tsunairo.strapij.beans.Entries;
import co.ke.tsunairo.strapij.beans.Entry;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.*;

import java.util.Map;

/**
 * @author Murimi Njiru
 */

public interface CollectionTypeClient {
	@GET("api/{pluralApiId}")
	Observable<Entries> getEntries(@Path("pluralApiId") String pluralApiId, @QueryMap(encoded=true) Map<String,String> queryMap);

	@GET("api/{pluralApiId}/{documentId}")
	Observable<Entry> getEntry(@Path("pluralApiId") String pluralApiId, @Path("documentId") String documentId, @QueryMap Map<String,String> queryMap);

	@PUT("api/{pluralApiId}/{documentId}")
	Observable<Entry> updateEntry(@Path("pluralApiId") String pluralApiId, @Path("documentId") String documentId, @QueryMap Map<String,String> queryMap);

	@POST("api/{pluralApiId}/{documentId}")
	Observable<Entry> createEntry(@Path("pluralApiId") String pluralApiId, @Path("documentId") String documentId, @QueryMap Map<String,String> queryMap);

	@DELETE("api/{pluralApiId}/{documentId}")
	Observable<Entry> deleteEntry(@Path("pluralApiId") String pluralApiId, @Path("documentId") String documentId, @QueryMap Map<String,String> queryMap);

}

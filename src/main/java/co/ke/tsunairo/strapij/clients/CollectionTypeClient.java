package co.ke.tsunairo.strapij.clients;

import co.ke.tsunairo.strapij.EntriesResponse;
import co.ke.tsunairo.strapij.EntryResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.*;

import java.util.Map;

/**
 * @author Murimi Njiru
 */

public interface CollectionTypeClient extends StrapiClient {
	@GET("{pluralApiId}")
	Observable<EntriesResponse> getEntries(@Path("pluralApiId") String pluralApiId, @QueryMap(encoded=true) Map<String,String> queryMap);

	@GET("{pluralApiId}/{documentId}")
	Observable<EntryResponse> getEntry(@Path("pluralApiId") String pluralApiId, @Path("documentId") String documentId, @QueryMap Map<String,String> queryMap);

	@PUT("{pluralApiId}/{documentId}")
	Observable<EntryResponse> updateEntry(@Path("pluralApiId") String pluralApiId, @Path("documentId") String documentId, @QueryMap Map<String,String> queryMap);

	@POST("{pluralApiId}/{documentId}")
	Observable<EntryResponse> createEntry(@Path("pluralApiId") String pluralApiId, @Path("documentId") String documentId, @QueryMap Map<String,String> queryMap);

	@DELETE("{pluralApiId}/{documentId}")
	Observable<EntryResponse> deleteEntry(@Path("pluralApiId") String pluralApiId, @Path("documentId") String documentId, @QueryMap Map<String,String> queryMap);

}

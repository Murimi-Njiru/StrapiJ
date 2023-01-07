package co.ke.tsunairo.strapij;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.*;

import java.util.Map;

/**
 * @author Murimi Njiru
 */

public interface StrapiHTTPClient
{
	@GET("{type}")
	Observable<EntriesResponse>  getCollectionEntries(@Path("type") String type, @QueryMap(encoded=true)  Map<String,String> queryMap);

	@GET("{type}/{id}")
	Observable<EntryResponse> getCollectionEntry(@Path("type") String type, @Path("id") String id, @QueryMap Map<String,String> queryMap);

	@PUT("{type}/{id}")
	Observable<EntryResponse> updateCollectionEntry(@Path("type") String type, @Path("id") String id, @QueryMap Map<String,String> queryMap);

	@POST("{type}/{id}")
	Observable<EntryResponse> createCollectionEntry(@Path("type") String type, @Path("id") String id, @QueryMap Map<String,String> queryMap);

	@DELETE("{type}/{id}")
	Observable<EntryResponse> deleteCollectionEntry(@Path("type") String type, @Path("id") String id, @QueryMap Map<String,String> queryMap);

	@GET("{id}")
	Observable<EntryResponse> getSingleEntry(@Path("id") String id, @QueryMap Map<String,String> queryMap);

	@PUT("{id}")
	Observable<EntryResponse> updateSingleEntry(@Path("id") String id, @QueryMap Map<String,String> queryMap);

	@DELETE("{id}")
	Observable<EntryResponse> deleteSingleEntry(@Path("id") String id, @QueryMap Map<String,String> queryMap);
}

package co.ke.tsunairo.strapij.clients;

import co.ke.tsunairo.strapij.EntryResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.*;

import java.util.Map;

/**
 * @author Murimi Njiru
 */

public interface SingleTypeClient extends StrapiClient {
	@GET("{singularApiId}")
	Observable<EntryResponse> getSingleEntry(@Path("singularApiId") String singularApiId, @QueryMap Map<String,String> queryMap);

	@PUT("{singularApiId}")
	Observable<EntryResponse> updateSingleEntry(@Path("singularApiId") String singularApiId, @QueryMap Map<String,String> queryMap);

	@DELETE("{singularApiId}")
	Observable<EntryResponse> deleteSingleEntry(@Path("singularApiId") String singularApiId, @QueryMap Map<String,String> queryMap);
}

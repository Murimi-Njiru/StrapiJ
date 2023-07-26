package co.ke.tsunairo.strapij.clients;

import co.ke.tsunairo.strapij.beans.Entry;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.*;

import java.util.Map;

/**
 * @author Murimi Njiru
 */

public interface SingleTypeClient {
	@GET("api/{singularApiId}")
	Observable<Entry> getSingleEntry(@Path("singularApiId") String singularApiId, @QueryMap Map<String,String> queryMap);

	@PUT("api/{singularApiId}")
	Observable<Entry> updateSingleEntry(@Path("singularApiId") String singularApiId, @QueryMap Map<String,String> queryMap);

	@DELETE("api/{singularApiId}")
	Observable<Entry> deleteSingleEntry(@Path("singularApiId") String singularApiId, @QueryMap Map<String,String> queryMap);
}

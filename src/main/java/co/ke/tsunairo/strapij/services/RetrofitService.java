package co.ke.tsunairo.strapij.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Murimi Njiru
 */

public class RetrofitService {
	public Retrofit getRetrofitInstance(String url) {
		Gson gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
				.create();

		return new Retrofit.Builder()
				.baseUrl(url)
				.addCallAdapterFactory(RxJava3CallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create(gson))
				.build();
	}
}

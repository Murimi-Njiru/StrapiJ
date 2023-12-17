package co.ke.tsunairo.strapij.test;

import co.ke.tsunairo.strapij.StrapiJ;
import co.ke.tsunairo.strapij.StrapiQuery;
import com.google.gson.Gson;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Murimi Njiru
 */

public class Test {

	public void tesst() {
		StrapiJ<App> appStrapiJ = new StrapiJ<>(App.class, "https://dev-muriminjiru.tsunairo.com/ui-cms/");
		StrapiQuery strapiQuery = new StrapiQuery();
		AtomicReference<App> responseAtomicReference = new AtomicReference<>();

		strapiQuery.addFiltersToQuery("[appId]","$eq", "germale");
		strapiQuery.addPopulateToQuery("populate", "deep, 8");
		appStrapiJ.getCollectionEntries(strapiQuery).blockingSubscribe(apps -> {
			System.out.println(new Gson().toJson(apps.get(0)));
		});
	}
}

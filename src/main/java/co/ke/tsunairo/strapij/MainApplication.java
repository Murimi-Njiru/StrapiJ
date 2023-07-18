package co.ke.tsunairo.strapij;

import co.ke.tsunairo.strapij.test.App;
import com.google.gson.Gson;

/**
 * @author Murimi Njiru
 */

public class MainApplication {
	public static void main(String []args) {
		System.out.println("StrapiJ");

		//Test
		StrapiJ<App> strapiJ = new StrapiJ<>(App.class, "https://tsunairo.com/web-ui-cms/api/");
		StrapiQuery strapiQuery = new StrapiQuery();
		strapiQuery.addPopulateToQuery("populate[frames][populate][0]", "panels.elements.syncProps.inputs.syncProps");
		strapiQuery.addPopulateToQuery("populate[theme][populate][1]", "theme");

		strapiJ.getCollectionEntries( strapiQuery).blockingSubscribe(themes -> {
			System.out.println(new Gson().toJson(themes));
		}, t -> {
			t.printStackTrace();
		});
	}
}

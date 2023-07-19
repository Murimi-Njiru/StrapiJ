package co.ke.tsunairo.strapij.test;

import co.ke.tsunairo.strapij.annotations.Attribute;
import co.ke.tsunairo.strapij.annotations.Content;
import co.ke.tsunairo.strapij.annotations.PopulateEntries;
import co.ke.tsunairo.strapij.annotations.PopulateFields;
import co.ke.tsunairo.strapij.test.syncProps.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Murimi Njiru
 */

@Content(apiId = "elements")
public @Data class Element {
	@Attribute(alias = "elementId")
	private String id;
	private String name;
	private String label;
	@Attribute(field = PopulateFields.DYNAMIC_ZONE, entry = PopulateEntries.SINGLE,
			dynamicZoneMappers = {FeatureCards.class, Form.class, Hero.class, MenuView.class, TextContent.class})
	private Map<String,Object> syncProps;
	private String query;
	private String command;
	private String wsDestination;
	private int size;
	private String icon;
	private Map<String, Object> meta;
	private int index;
}

class ElementSyncPropsMappers {
	private List<Class<?>> mappers;

	public ElementSyncPropsMappers() {
		this.mappers = new ArrayList<>();

		mappers.add(FeatureCards.class);
		mappers.add(Form.class);
		mappers.add(Hero.class);
		mappers.add(MenuView.class);
		mappers.add(TextContent.class);
	}

	public Class<?>[] getMappers() {
		return (Class<?>[]) mappers.toArray();
	}
}

package co.ke.tsunairo.strapij.test;

import co.ke.tsunairo.strapij.annotations.Attribute;
import co.ke.tsunairo.strapij.annotations.Content;
import co.ke.tsunairo.strapij.annotations.DynamicZone;
import co.ke.tsunairo.strapij.test.syncProps.*;
import lombok.Data;

import java.util.Map;

/**
 * @author Murimi Njiru
 */

@Content(apiId = "elements")
public @Data class Element {
	@Attribute(useDataId = true)
	private String id;
	private String name;
	private String label;
	@DynamicZone(mappers = {FeatureCards.class, Form.class, Hero.class, MenuView.class, TextContent.class, TextInput.class,
	NumberInput.class})
	private Map<String,Object> syncProps;
	private String query;
	private String command;
	private String wsDestination;
	private int size;
	private String icon;
	private Map<String, Object> meta;
}

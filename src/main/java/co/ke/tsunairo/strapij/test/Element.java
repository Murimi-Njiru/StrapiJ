package co.ke.tsunairo.strapij.test;

import co.ke.tsunairo.strapij.annotations.*;
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
	@DynamicZone(mappers = {FeatureCards.class, Form.class, Hero.class, MenuView.class, TextContent.class})
	private Map<String,Object> syncProps;
	private String query;
	private String command;
	private String wsDestination;
	private int size;
	private String icon;
	private Map<String, Object> meta;
	private int index;
}

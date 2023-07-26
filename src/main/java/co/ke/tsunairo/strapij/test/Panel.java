package co.ke.tsunairo.strapij.test;

import co.ke.tsunairo.strapij.annotations.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Murimi Njiru
 */

@Content(apiId = "panels")
public @Data class Panel
{
	@Attribute(alias = "panelId")
	private String id;
	private String label;
	@Relation
	private List<Element> elements;
	@Relation
	private List<Panel> panels;
	private int index;
	private String type;
	private String icon;
	private Map<String, Object> meta;
}

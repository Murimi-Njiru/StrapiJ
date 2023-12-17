package co.ke.tsunairo.strapij.test;

import co.ke.tsunairo.strapij.annotations.Attribute;
import co.ke.tsunairo.strapij.annotations.Content;
import co.ke.tsunairo.strapij.annotations.Relation;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Murimi Njiru
 */

@Content(apiId = "panels")
public @Data class Panel
{
	@Attribute(useDataId = true)
	private String id;
	private String label;
	@Relation
	private List<Element> elements;
	@Relation
	private List<Panel> panels;
	private String type;
	private String icon;
	private Map<String, Object> meta;
	private Boolean isIndex;
}

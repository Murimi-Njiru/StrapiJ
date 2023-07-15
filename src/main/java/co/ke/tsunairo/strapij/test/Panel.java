package co.ke.tsunairo.strapij.test;

import co.ke.tsunairo.strapij.annotations.Attribute;
import co.ke.tsunairo.strapij.annotations.Content;
import co.ke.tsunairo.strapij.annotations.PopulateEntries;
import co.ke.tsunairo.strapij.annotations.PopulateFields;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Murimi Njiru
 */

@Content(apiId = "panels")
public @Data class Panel
{
	private String id;
	private String label;
	@Attribute(field = PopulateFields.RELATION, entry = PopulateEntries.LIST)
	private List<Element> elements;
	@Attribute(field = PopulateFields.RELATION, entry = PopulateEntries.LIST)
	private List<Panel> panels;
	private int index;
	private String type;
	private String icon;
	private Map<String, Object> meta;
}

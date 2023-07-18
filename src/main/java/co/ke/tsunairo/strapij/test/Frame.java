package co.ke.tsunairo.strapij.test;

import co.ke.tsunairo.strapij.annotations.Content;
import co.ke.tsunairo.strapij.annotations.Attribute;
import co.ke.tsunairo.strapij.annotations.PopulateEntries;
import co.ke.tsunairo.strapij.annotations.PopulateFields;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Murimi Njiru
 */

@Content(apiId = "frames")
public @Data class Frame
{
	@Attribute(alias = "frameId")
	private String id;
	private String label;
	private List<String> navStart;
	private List<String> navEnd;
	@Attribute(field = PopulateFields.RELATION_MANY, entry = PopulateEntries.LIST)
	private List<Panel> panels;
	private String type;
	private Map<String, Object> meta;
	private String icon;
	private int index;
}

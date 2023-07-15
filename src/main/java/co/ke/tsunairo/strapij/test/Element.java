package co.ke.tsunairo.strapij.test;

import co.ke.tsunairo.strapij.annotations.Attribute;
import co.ke.tsunairo.strapij.annotations.Content;
import co.ke.tsunairo.strapij.annotations.PopulateEntries;
import co.ke.tsunairo.strapij.annotations.PopulateFields;
import lombok.Data;

import java.util.Map;

/**
 * @author Murimi Njiru
 */

@Content(apiId = "elements")
public @Data class Element {
	private String id;
	private String name;
	private String label;
	@Attribute(field = PopulateFields.DYNAMIC_ZONE, entry = PopulateEntries.SINGLE, mapper = FormSyncPropsMapper.class)
	private Map<String,Object> syncProps;
	private String query;
	private String command;
	private String wsDestination;
	private int size;
	private String icon;
	private Map<String, Object> meta;
	private int index;
}

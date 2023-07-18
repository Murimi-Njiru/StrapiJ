package co.ke.tsunairo.strapij.test;

import co.ke.tsunairo.strapij.annotations.Content;
import co.ke.tsunairo.strapij.annotations.Attribute;
import co.ke.tsunairo.strapij.annotations.PopulateEntries;
import co.ke.tsunairo.strapij.annotations.PopulateFields;
import lombok.Data;

import java.util.List;

/**
 * @author Murimi Njiru
 */

@Content(apiId = "apps")
public @Data class App {
	@Attribute(alias = "appId")
	private String id;
	private String name;
	private String version;
	private String description;
	private String logo;
	private String domain;
	@Attribute(field = PopulateFields.RELATION_MANY, entry = PopulateEntries.LIST)
	private List<Frame> frames;
	@Attribute(field = PopulateFields.RELATION_ONE)
	private Theme theme;
}

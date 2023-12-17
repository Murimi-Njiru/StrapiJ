package co.ke.tsunairo.strapij.test;

import co.ke.tsunairo.strapij.annotations.Attribute;
import co.ke.tsunairo.strapij.annotations.Content;
import co.ke.tsunairo.strapij.annotations.Media;
import co.ke.tsunairo.strapij.annotations.Relation;
import lombok.Data;

import java.util.List;

/**
 * @author Murimi Njiru
 */

@Content(apiId = "apps")
public @Data class App {
	@Attribute(useDataId = true)
	private String id;
	private String name;
	private String version;
	private String description;
	@Media
	private Logo logo;
	@Relation
	private List<Frame> frames;
	@Relation
	private Theme theme;
}
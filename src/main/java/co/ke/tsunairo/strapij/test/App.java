package co.ke.tsunairo.strapij.test;

import co.ke.tsunairo.strapij.annotations.*;
import co.ke.tsunairo.strapij.test.beans.Image;
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
	@Media
	private Image logo;
	private String domain;
	@Relation
	private List<Frame> frames;
	@Relation
	private Theme theme;
}

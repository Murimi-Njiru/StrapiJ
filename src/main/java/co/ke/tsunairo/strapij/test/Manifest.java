package co.ke.tsunairo.strapij.test;

import co.ke.tsunairo.strapij.annotations.*;
import co.ke.tsunairo.strapij.test.beans.Image;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Murimi Njiru
 */

@Content(apiId = "manifests")
public @Data class Manifest
{
	@Attribute(alias = "frameId")
	private String id;
	private String short_name;
	private String name;
	@Media
	private List<Icon> icons;
	private String start_url;
	private String display;
	private String theme_color;
	private String background_color;
}

@Data class Icon {
	@Attribute(alias = "mime")
	private String type;
	@Attribute(alias = "alternativeText")
	private String sizes;
	@Attribute(alias = "url")
	private String src;
	@Attribute(alias = "caption")
	private String purpose;
}

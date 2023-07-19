package co.ke.tsunairo.strapij.test.beans;

import co.ke.tsunairo.strapij.annotations.Attribute;
import lombok.Data;

/**
 * @author Murimi Njiru
 */

public @Data class Image {
	private String name;
	@Attribute(alias = "alternativeText")
	private String altText;
	private String url;
	private String ext;
	private String mime;
	private String size;
}
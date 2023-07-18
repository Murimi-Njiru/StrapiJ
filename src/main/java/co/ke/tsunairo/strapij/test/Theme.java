package co.ke.tsunairo.strapij.test;

import co.ke.tsunairo.strapij.annotations.Attribute;
import co.ke.tsunairo.strapij.annotations.Content;
import lombok.Data;

import java.util.Map;

/**
 * @author Murimi Njiru
 */

@Content(apiId = "themes")
public @Data class Theme {
	@Attribute(alias = "themeId")
	private String id;
	private Map<String, Object> colors;
}
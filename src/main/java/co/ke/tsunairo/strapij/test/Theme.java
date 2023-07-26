package co.ke.tsunairo.strapij.test;

import co.ke.tsunairo.strapij.annotations.Attribute;
import co.ke.tsunairo.strapij.annotations.Component;
import co.ke.tsunairo.strapij.annotations.Content;
import co.ke.tsunairo.strapij.annotations.PopulateEntries;
import lombok.Data;

import java.util.Map;

/**
 * @author Murimi Njiru
 */

@Content(apiId = "themes")
public @Data class Theme {
	@Attribute(alias = "themeId")
	private String id;
	@Component
	private Colors colors;
}

@Data class Colors {
	private String primary;
	private String secondary;
	private String accent;
}
package co.ke.tsunairo.strapij.test.syncProps;

import co.ke.tsunairo.strapij.annotations.*;
import co.ke.tsunairo.strapij.test.beans.Image;
import lombok.Data;

import java.util.List;

/**
 * @author Murimi Njiru
 */

@ComponentMapper(name = "sync-props.feature-cards-sync-props")
public @Data class FeatureCards {
	@SubComponent
	private List<Feature> features;
}

@Data class Feature {
	private String title;
	private String description;
	@Media
	private Image image;
}

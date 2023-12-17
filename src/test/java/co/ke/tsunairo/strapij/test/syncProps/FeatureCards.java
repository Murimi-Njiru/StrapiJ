package co.ke.tsunairo.strapij.test.syncProps;

import co.ke.tsunairo.strapij.annotations.ComponentMapper;
import co.ke.tsunairo.strapij.annotations.Media;
import co.ke.tsunairo.strapij.annotations.SubComponent;
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
	private HeroImage image;
}

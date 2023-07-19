package co.ke.tsunairo.strapij.test.syncProps;

import co.ke.tsunairo.strapij.annotations.Attribute;
import co.ke.tsunairo.strapij.annotations.ComponentMapper;
import co.ke.tsunairo.strapij.annotations.PopulateEntries;
import co.ke.tsunairo.strapij.annotations.PopulateFields;
import co.ke.tsunairo.strapij.test.beans.Image;
import lombok.Data;

import java.util.List;

/**
 * @author Murimi Njiru
 */

@ComponentMapper(name = "sync-props.feature-cards-sync-props")
public @Data class FeatureCards {
	@Attribute(field = PopulateFields.SUB_COMPONENT, entry = PopulateEntries.LIST)
	private List<Feature> features;
}

@Data class Feature {
	private String title;
	private String description;
	@Attribute(field = PopulateFields.RELATION_ONE)
	private Image image;
}

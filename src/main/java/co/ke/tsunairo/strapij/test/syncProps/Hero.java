package co.ke.tsunairo.strapij.test.syncProps;

import co.ke.tsunairo.strapij.annotations.Attribute;
import co.ke.tsunairo.strapij.annotations.ComponentMapper;
import co.ke.tsunairo.strapij.annotations.PopulateEntries;
import co.ke.tsunairo.strapij.annotations.PopulateFields;
import co.ke.tsunairo.strapij.test.beans.Image;
import lombok.Data;

/**
 * @author Murimi Njiru
 */

@ComponentMapper(name = "sync-props.hero-sync-props")
public @Data class Hero {
	private String subtitle;
	@Attribute(field = PopulateFields.RELATION_ONE)
	private Image image;
}

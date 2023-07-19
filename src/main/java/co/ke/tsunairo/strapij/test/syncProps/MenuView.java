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

@ComponentMapper(name = "sync-props.menu-view-sync-props")
public @Data class MenuView {
	@Attribute(field = PopulateFields.RELATION_MANY, entry = PopulateEntries.LIST)
	private List<Image> images;
}

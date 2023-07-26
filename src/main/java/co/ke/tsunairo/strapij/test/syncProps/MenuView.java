package co.ke.tsunairo.strapij.test.syncProps;

import co.ke.tsunairo.strapij.annotations.*;
import co.ke.tsunairo.strapij.test.beans.Image;
import lombok.Data;

import java.util.List;

/**
 * @author Murimi Njiru
 */

@ComponentMapper(name = "sync-props.menu-view-sync-props")
public @Data class MenuView {
	@Relation
	private List<Image> images;
}

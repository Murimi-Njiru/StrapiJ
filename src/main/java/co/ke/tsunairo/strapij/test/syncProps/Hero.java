package co.ke.tsunairo.strapij.test.syncProps;

import co.ke.tsunairo.strapij.annotations.*;
import co.ke.tsunairo.strapij.test.beans.Image;
import lombok.Data;

/**
 * @author Murimi Njiru
 */

@ComponentMapper(name = "sync-props.hero-sync-props")
public @Data class Hero {
	private String subtitle;
	@Media
	private Image image;
}

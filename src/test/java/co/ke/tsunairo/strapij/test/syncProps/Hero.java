package co.ke.tsunairo.strapij.test.syncProps;

import co.ke.tsunairo.strapij.annotations.ComponentMapper;
import co.ke.tsunairo.strapij.annotations.Media;
import lombok.Data;

/**
 * @author Murimi Njiru
 */

@ComponentMapper(name = "sync-props.hero-sync-props")
public @Data class Hero {
	private String subtitle;
	@Media
	private HeroImage image;
}

@Data class HeroImage {
	private String name;
	private String alternativeText;
	private String url;
	private String ext;
	private String mime;
	private String size;
}

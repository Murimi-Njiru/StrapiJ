package co.ke.tsunairo.strapij.test.syncProps;

import co.ke.tsunairo.strapij.annotations.ComponentMapper;
import co.ke.tsunairo.strapij.annotations.Media;
import lombok.Data;

import java.util.List;

/**
 * @author Murimi Njiru
 */

@ComponentMapper(name = "sync-props.menu-view-sync-props")
public @Data class MenuView {
	@Media
	private List<HeroImage> images;
}

@Data class Image{
	private String name;
	private String alternativeText;
	private String url;
	private String ext;
	private String mime;
	private String size;
}

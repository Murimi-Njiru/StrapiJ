package co.ke.tsunairo.strapij.test.syncProps;

import co.ke.tsunairo.strapij.annotations.ComponentMapper;
import lombok.Data;

/**
 * @author Murimi Njiru
 */

@ComponentMapper(name = "sync-props.text-content-sync-props")
public @Data class TextContent {
	private String subtitle;
	private String title;
}

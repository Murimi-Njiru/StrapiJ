package co.ke.tsunairo.strapij;

import lombok.Data;

import java.util.Map;

/**
 * @author Murimi Njiru
 */

public @Data class Theme
{
	private String id;
	private Map<String, Object> colors;
}

package co.ke.tsunairo.strapij;

import lombok.Data;

import java.util.Map;

/**
 * @author Murimi Njiru
 */

public @Data class StrapiData
{
	private String id;
	private Map<String, Object> attributes;
	private Map<String, Object> meta;
}

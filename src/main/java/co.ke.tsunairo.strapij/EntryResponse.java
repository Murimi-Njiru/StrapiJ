package co.ke.tsunairo.strapij;

import lombok.Data;

import java.util.Map;

/**
 * @author Murimi Njiru
 */

public @Data class EntryResponse
{
	private StrapiData strapiData;
	private Map<String, Object> meta;
	private StrapiError strapiError;
}

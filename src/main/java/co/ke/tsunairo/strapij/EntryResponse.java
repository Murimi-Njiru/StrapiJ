package co.ke.tsunairo.strapij;

import lombok.Data;

import java.util.Map;

/**
 * @author Murimi Njiru
 */

public @Data class EntryResponse
{
	private Map<String, Object> data;
	private Map<String, Object> meta;
	private Map<String, Object> error;
}

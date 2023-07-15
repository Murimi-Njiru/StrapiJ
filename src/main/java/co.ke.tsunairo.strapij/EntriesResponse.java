package co.ke.tsunairo.strapij;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Murimi Njiru
 */

public @Data class EntriesResponse
{
	private List<Map<String, Object>> data;
	private Map<String, Object> meta;
	private Map<String, Object> error;
}

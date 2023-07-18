package co.ke.tsunairo.strapij.beans;

import java.util.List;
import java.util.Map;

/**
 * @author Murimi Njiru
 */

public @lombok.Data class Entries {
	private String id;
	private List<Data> data;
	private Map<String,Object> meta;
}

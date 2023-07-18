package co.ke.tsunairo.strapij.beans;

import lombok.Data;

import java.util.Map;

/**
 * @author Murimi Njiru
 */

public @Data class Entry {
	private String id;
	private co.ke.tsunairo.strapij.beans.Data data;
	private Map<String,Object> Meta;
}

package co.ke.tsunairo.strapij.test;

import co.ke.tsunairo.strapij.annotations.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Murimi Njiru
 */

@Content(apiId = "frames")
public @Data class Frame
{
	@Attribute(alias = "frameId")
	private String id;
	private String label;
	private List<String> navStart;
	private List<String> navEnd;
	@Relation
	private List<Panel> panels;
	private String type;
	private Map<String, Object> meta;
	private String icon;
	private int index;
}

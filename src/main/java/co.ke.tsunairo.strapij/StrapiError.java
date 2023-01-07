package co.ke.tsunairo.strapij;

import lombok.Data;

import java.util.Map;

/**
 * @author Murimi Njiru
 */

public @Data class StrapiError
{
	private String status;
	private String name;
	private String message;
	private Map<String, String> details;
}

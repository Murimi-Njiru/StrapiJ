package co.ke.tsunairo.strapij.test.syncProps;

import co.ke.tsunairo.strapij.annotations.ComponentMapper;
import lombok.Data;

/**
 * @author Murimi Njiru
 */
@ComponentMapper(name = "sync-props.number-input-sync-props")
public @Data class NumberInput {
	String hint;
	String defaultValue;
	String formKey;
	Boolean required;
}

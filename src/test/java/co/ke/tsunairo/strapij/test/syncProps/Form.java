package co.ke.tsunairo.strapij.test.syncProps;

import co.ke.tsunairo.strapij.annotations.ComponentMapper;
import co.ke.tsunairo.strapij.annotations.Relation;
import co.ke.tsunairo.strapij.test.Element;
import lombok.Data;

import java.util.List;

/**
 * @author Murimi Njiru
 */

@ComponentMapper(name = "sync-props.form-sync-props")
public @Data class Form {
	private String type;
	@Relation
	private List<Element> inputs;
}

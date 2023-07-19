package co.ke.tsunairo.strapij.test.syncProps;

import co.ke.tsunairo.strapij.annotations.Attribute;
import co.ke.tsunairo.strapij.annotations.ComponentMapper;
import co.ke.tsunairo.strapij.annotations.PopulateEntries;
import co.ke.tsunairo.strapij.annotations.PopulateFields;
import co.ke.tsunairo.strapij.test.Element;
import lombok.Data;

import java.util.List;

/**
 * @author Murimi Njiru
 */

@ComponentMapper(name = "sync-props.form-sync-props")
public @Data class Form {
	private String type;
	@Attribute(field = PopulateFields.RELATION_MANY, entry = PopulateEntries.LIST)
	private List<Element> inputs;
}

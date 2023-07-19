package co.ke.tsunairo.strapij.test;

import co.ke.tsunairo.strapij.annotations.Attribute;
import co.ke.tsunairo.strapij.annotations.ComponentMapper;
import co.ke.tsunairo.strapij.annotations.PopulateEntries;
import co.ke.tsunairo.strapij.annotations.PopulateFields;

import java.util.List;

/**
 * @author Murimi Njiru
 */

@ComponentMapper(name = "sync-props.form-sync-props")
public class FormSyncPropsMapper {
	@Attribute(field = PopulateFields.RELATION_MANY, entry = PopulateEntries.LIST)
	private List<Element> inputs;
}

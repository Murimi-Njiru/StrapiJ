package co.ke.tsunairo.strapij.test;

import co.ke.tsunairo.strapij.annotations.Attribute;
import co.ke.tsunairo.strapij.annotations.PopulateEntries;
import co.ke.tsunairo.strapij.annotations.PopulateFields;

import java.util.List;

/**
 * @author Murimi Njiru
 */

public class FormSyncPropsMapper {
	@Attribute(field = PopulateFields.RELATION, entry = PopulateEntries.LIST)
	private List<Element> inputs;
}

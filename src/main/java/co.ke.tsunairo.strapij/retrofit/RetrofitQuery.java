package co.ke.tsunairo.strapij.retrofit;

import lombok.Data;

import java.util.*;

/**
 * @author Murimi Njiru
 */

public @Data class RetrofitQuery extends HashMap<String, Object>
{
	private Map<String, String> query = new HashMap<String, String>();

	public Map<String, String> addSortToQuery () {
		return query;
	}

	public Map<String, String> addFieldsToQuery(List<String> fields) {
		query.put("fields", String.join(",", fields));
		return query;
	}

	public Map<String, String> addFiltersToQuery (String field, String operator, String value) {
		query.put("filters["+field+"]["+operator+"]", value);
		return query;
	}

	public Map<String, String> addPopulateToQuery (String key, String value) {
		query.put(key, value);
		return query;
	}
	public Map<String, String> addPaginationToQuery (int start, int limit) {
		query.put("pagination[start]", ""+start);
		query.put("pagination[limit]", ""+limit);
		return query;
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		Set<Entry<String, Object>> originSet = super.entrySet();
		Set<Entry<String, Object>> newSet = new HashSet<>();

		for (Entry<String, Object> entry : originSet) {
			String entryKey = entry.getKey();
			if (entryKey == null) {
				throw new IllegalArgumentException("Query map contained null key.");
			}
			Object entryValue = entry.getValue();
			if (entryValue == null) {
				throw new IllegalArgumentException(
						"Query map contained null value for key '" + entryKey + "'.");
			}
			else if(entryValue instanceof List) {
				for(Object arrayValue:(List)entryValue)  {
					if (arrayValue != null) { // Skip null values
						Entry<String, Object> newEntry = new SimpleEntry<>(entryKey, arrayValue);
						newSet.add(newEntry);
					}
				}
			}
			else {
				Entry<String, Object> newEntry = new SimpleEntry<>(entryKey, entryValue);
				newSet.add(newEntry);
			}
		}
		return newSet;
	}
}

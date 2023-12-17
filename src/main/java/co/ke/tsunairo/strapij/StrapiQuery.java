package co.ke.tsunairo.strapij;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Murimi Njiru
 */

public class StrapiQuery {
	private final Map<String, String> query = new HashMap<String, String>();

	public Map<String, String> addSortToQuery () {
		return query;
	}

	public Map<String, String> addFieldsToQuery(List<String> fields) {
		query.put("fields", String.join(",", fields));
		return query;
	}

	public Map<String, String> addFiltersToQuery (String field, String operator, String value) {
		query.put("filters"+field+"["+operator+"]", value);
		return query;
	}

	protected List<String> getPopulateQueries() {
		List<String> fieldsToPopulate = new ArrayList<>();

		query.forEach((key, value) -> {
			if (key.contains("populate"))
			{
				Pattern pattern = Pattern.compile("\\[(.*?)]");
				Matcher matcher = pattern.matcher(key);

				while (matcher.find())
				{
					String match = matcher.group(1);
					String field = (!match.equals("populate") && !isNumeric(match)) ? match : "";
					if (!fieldsToPopulate.contains(field) && !field.isEmpty())
					{
						fieldsToPopulate.add(field);
					}
				}
				Arrays.stream(value.split("\\.")).forEach(field -> {
					if (!fieldsToPopulate.contains(field))
					{
						fieldsToPopulate.add(field);
					}
				});
			}
		});

		return fieldsToPopulate;
	}

	private static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch(NumberFormatException e){
			return false;
		}
	}

	public void addPopulateToQuery (String key, String value) {
		query.put(key, value);
	}

	public Map<String, String> addPaginationToQuery (int start, int limit) {
		query.put("pagination[start]", ""+start);
		query.put("pagination[limit]", ""+limit);
		return query;
	}

	public Map<String, String> getQuery() {
		return query;
	}
}

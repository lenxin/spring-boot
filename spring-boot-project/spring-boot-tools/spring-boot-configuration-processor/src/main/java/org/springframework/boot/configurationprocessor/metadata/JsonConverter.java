package org.springframework.boot.configurationprocessor.metadata;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.configurationprocessor.metadata.ItemMetadata.ItemType;

/**
 * Converter to change meta-data objects into JSON objects.
 *


 */
class JsonConverter {

	private static final ItemMetadataComparator ITEM_COMPARATOR = new ItemMetadataComparator();

	JSONArray toJsonArray(ConfigurationMetadata metadata, ItemType itemType) throws Exception {
		JSONArray jsonArray = new JSONArray();
		List<ItemMetadata> items = metadata.getItems().stream().filter((item) -> item.isOfItemType(itemType))
				.sorted(ITEM_COMPARATOR).collect(Collectors.toList());
		for (ItemMetadata item : items) {
			if (item.isOfItemType(itemType)) {
				jsonArray.put(toJsonObject(item));
			}
		}
		return jsonArray;
	}

	JSONArray toJsonArray(Collection<ItemHint> hints) throws Exception {
		JSONArray jsonArray = new JSONArray();
		for (ItemHint hint : hints) {
			jsonArray.put(toJsonObject(hint));
		}
		return jsonArray;
	}

	JSONObject toJsonObject(ItemMetadata item) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", item.getName());
		jsonObject.putOpt("type", item.getType());
		jsonObject.putOpt("description", item.getDescription());
		jsonObject.putOpt("sourceType", item.getSourceType());
		jsonObject.putOpt("sourceMethod", item.getSourceMethod());
		Object defaultValue = item.getDefaultValue();
		if (defaultValue != null) {
			putDefaultValue(jsonObject, defaultValue);
		}
		ItemDeprecation deprecation = item.getDeprecation();
		if (deprecation != null) {
			jsonObject.put("deprecated", true); // backward compatibility
			JSONObject deprecationJsonObject = new JSONObject();
			if (deprecation.getLevel() != null) {
				deprecationJsonObject.put("level", deprecation.getLevel());
			}
			if (deprecation.getReason() != null) {
				deprecationJsonObject.put("reason", deprecation.getReason());
			}
			if (deprecation.getReplacement() != null) {
				deprecationJsonObject.put("replacement", deprecation.getReplacement());
			}
			jsonObject.put("deprecation", deprecationJsonObject);
		}
		return jsonObject;
	}

	private JSONObject toJsonObject(ItemHint hint) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", hint.getName());
		if (!hint.getValues().isEmpty()) {
			jsonObject.put("values", getItemHintValues(hint));
		}
		if (!hint.getProviders().isEmpty()) {
			jsonObject.put("providers", getItemHintProviders(hint));
		}
		return jsonObject;
	}

	private JSONArray getItemHintValues(ItemHint hint) throws Exception {
		JSONArray values = new JSONArray();
		for (ItemHint.ValueHint value : hint.getValues()) {
			values.put(getItemHintValue(value));
		}
		return values;
	}

	private JSONObject getItemHintValue(ItemHint.ValueHint value) throws Exception {
		JSONObject result = new JSONObject();
		putHintValue(result, value.getValue());
		result.putOpt("description", value.getDescription());
		return result;
	}

	private JSONArray getItemHintProviders(ItemHint hint) throws Exception {
		JSONArray providers = new JSONArray();
		for (ItemHint.ValueProvider provider : hint.getProviders()) {
			providers.put(getItemHintProvider(provider));
		}
		return providers;
	}

	private JSONObject getItemHintProvider(ItemHint.ValueProvider provider) throws Exception {
		JSONObject result = new JSONObject();
		result.put("name", provider.getName());
		if (provider.getParameters() != null && !provider.getParameters().isEmpty()) {
			JSONObject parameters = new JSONObject();
			for (Map.Entry<String, Object> entry : provider.getParameters().entrySet()) {
				parameters.put(entry.getKey(), extractItemValue(entry.getValue()));
			}
			result.put("parameters", parameters);
		}
		return result;
	}

	private void putHintValue(JSONObject jsonObject, Object value) throws Exception {
		Object hintValue = extractItemValue(value);
		jsonObject.put("value", hintValue);
	}

	private void putDefaultValue(JSONObject jsonObject, Object value) throws Exception {
		Object defaultValue = extractItemValue(value);
		jsonObject.put("defaultValue", defaultValue);
	}

	private Object extractItemValue(Object value) {
		Object defaultValue = value;
		if (value.getClass().isArray()) {
			JSONArray array = new JSONArray();
			int length = Array.getLength(value);
			for (int i = 0; i < length; i++) {
				array.put(Array.get(value, i));
			}
			defaultValue = array;

		}
		return defaultValue;
	}

	private static class ItemMetadataComparator implements Comparator<ItemMetadata> {

		@Override
		public int compare(ItemMetadata o1, ItemMetadata o2) {
			if (o1.isOfItemType(ItemType.GROUP)) {
				return compareGroup(o1, o2);
			}
			return compareProperty(o1, o2);
		}

		private int compareGroup(ItemMetadata o1, ItemMetadata o2) {
			return o1.getName().compareTo(o2.getName());
		}

		private int compareProperty(ItemMetadata o1, ItemMetadata o2) {
			if (isDeprecated(o1) && !isDeprecated(o2)) {
				return 1;
			}
			if (isDeprecated(o2) && !isDeprecated(o1)) {
				return -1;
			}
			return o1.getName().compareTo(o2.getName());
		}

		private boolean isDeprecated(ItemMetadata item) {
			return item.getDeprecation() != null;
		}

	}

}

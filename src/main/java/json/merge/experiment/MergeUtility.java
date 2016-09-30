package json.merge.experiment;

import java.util.Map;

import com.google.gson.*;

/**
 * Deep merge "new" Json object into the "old" Json object:
 * Start from the root and recursively merge as follows:
 * 1. Merge recursively
 * 2. If the key does not exist in "old", then add to the <key, value> to merged object
 * 3. "old" json will be appended to and not overridden by "new" if keys match
 */
public class MergeUtility {

    public static JsonObject merge(JsonObject newJsonObject, JsonObject oldJsonObject) throws Exception {
        for (Map.Entry<String, JsonElement> entry : newJsonObject.entrySet()) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();
            if (!oldJsonObject.has(key)) {
                // key does not exist in old json, add to merged json
                oldJsonObject.add(key, value);
            } else {
                if (value.isJsonObject()) {
                    //recursively merge since its a JsonObject
                    merge(value.getAsJsonObject(), oldJsonObject.get(key).getAsJsonObject());
                } else if (value.isJsonArray()) {
                    // append if json array
                    oldJsonObject.get(key).getAsJsonArray().addAll((JsonArray) value);
                }
            }
        }

    return oldJsonObject;
}
    public static void main(String[] args) throws Exception {
        JsonParser parser = new JsonParser();
        JsonObject newJsonObject;
        JsonObject oldJsonObject;
        newJsonObject = parser.parse("{\"customNamingIncludeRules\":[{\"name\":null,\"matchOnMobileApplicationName\":null,\"enabled\":true,\"priority\":2147483647,\"matchOnURL\":null,\"isDefault\":true,\"pageNamingConfig\":{\"useProtocol\":false,\"useDomainName\":true,\"useURL\":true,\"useRegex\":false,\"type\":\"FIRST_N_SEGMENTS\",\"anchorType\":null,\"urlMatchSegments\":{\"firstNSegments\":2,\"lastNSegments\":1,\"segmentNumbers\":null},\"anchorMatchSegments\":{\"firstNSegments\":1,\"lastNSegments\":1,\"segmentNumbers\":null},\"regexGroupConfig\":null,\"domainNameType\":\"SHOW_FULL_DOMAIN\",\"queryStringMatch\":null}}],\"customNamingExcludeRules\":[{\"enabled\":true,\"name\":\"test\",\"priority\":1,\"matchOnURL\":{\"type\":\"CONTAINS\",\"value\":\"test\"}}]}").getAsJsonObject();
        oldJsonObject = parser.parse("{\"customNamingIncludeRules\":[{\"name\":null,\"matchOnMobileApplicationName\":null,\"enabled\":true,\"priority\":2147483647,\"matchOnURL\":null,\"isDefault\":true,\"pageNamingConfig\":{\"useProtocol\":false,\"useDomainName\":true,\"useURL\":true,\"useRegex\":false,\"type\":\"FIRST_N_SEGMENTS\",\"anchorType\":null,\"urlMatchSegments\":{\"firstNSegments\":2,\"lastNSegments\":1,\"segmentNumbers\":null},\"anchorMatchSegments\":{\"firstNSegments\":1,\"lastNSegments\":1,\"segmentNumbers\":null},\"regexGroupConfig\":null,\"domainNameType\":\"SHOW_FULL_DOMAIN\",\"queryStringMatch\":null}}],\"customNamingExcludeRules\":[]}").getAsJsonObject();
        System.out.println(merge(newJsonObject, oldJsonObject));
    }
}

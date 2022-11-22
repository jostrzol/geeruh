package pl.edu.pw.elka.paprykaisalami.geeruh.support;

import lombok.val;
import net.javacrumbs.jsonunit.core.Option;
import net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers;
import org.json.JSONArray;
import org.json.JSONObject;

import static net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers.json;

public class JsonUtils {

    public static JSONArray array(JSONObject... contents) {
        val result = new JSONArray();
        result.putAll(contents);
        return result;
    }

    public static JsonUnitResultMatchers easyJson() {
        return json().when(Option.IGNORING_EXTRA_FIELDS).when(Option.IGNORING_ARRAY_ORDER);
    }

    public static String jsonUnitRegex(String regex) {
        return "${json-unit.regex}^" + regex + "$";
    }

    public static String DATE_REGEX =
            "[1-9][0-9]+-(0[0-9]|1[0-2])-([0-2][0-9]|3[0-1])" +
                    "T([0-1][0-9]|2[0-3])(:[0-5][0-9]){2}.[0-9]{3}\\+[0-9]{2}:[0-9]{2}";
}

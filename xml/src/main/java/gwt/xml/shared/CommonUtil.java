package gwt.xml.shared;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class CommonUtil {
    public static <T> T split(BiFunction<String, String[], T> function, Collection<String> values) {
        String head = null;
        String[] tail = new String[values.size() - 1];

        int index = 0;
        for (String value : values) {
            if (index++ == 0)
                head = value;
            else
                tail[index - 2] = value;
        }

        return function.apply(head, tail);
    }

    public static Map<String, String> createMap(Collection<String> sourceList) {
        Map<String, String> map = new LinkedHashMap<>(sourceList.size() >> 1);

        String key = null;
        for (String source : sourceList) {
            if (key == null) {
                key = source;
            } else {
                map.put(key, source);
                key = null;
            }
        }

        return map;
    }

    public static Map<String, String> createMap(String... sourceArray) {
        return createMap(Arrays.asList(sourceArray));
    }

    private CommonUtil() {
    }
}

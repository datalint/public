package gwt.xml.shared;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonUtil {
    public static Map<String, String> createMap(List<String> sourceList) {
        Map<String, String> map = new HashMap<>(sourceList.size() >> 1);

        for (int i = 0; i < sourceList.size(); i++) {
            map.put(sourceList.get(i++), sourceList.get(i));
        }

        return map;
    }

    public static Map<String, String> createMap(String... sourceArray) {
        return createMap(Arrays.asList(sourceArray));
    }

    private CommonUtil() {
    }
}

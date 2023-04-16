package gwt.xml.shared;

import java.util.HashMap;
import java.util.Map;

public class CommonUtil {
    public static Map<String, String> createMap(String... sourceArray) {
        Map<String, String> map = new HashMap<>(sourceArray.length >> 1);

        for (int i = 0; i < sourceArray.length; i++) {
            map.put(sourceArray[i++], sourceArray[i]);
        }

        return map;
    }

    private CommonUtil() {
    }
}

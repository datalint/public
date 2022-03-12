package gwt.xml.shared.common;

import java.util.*;

public enum CollectionMode {
    unordered {
        @Override
        public <K, V> Map<K, V> createMap(int capacity) {
            return new HashMap<>(capacity);
        }

        @Override
        public <T> Set<T> createSet(int capacity) {
            return new HashSet<>(capacity);
        }
    },
    insertOrdered {
        @Override
        public <K, V> Map<K, V> createMap(int capacity) {
            return new LinkedHashMap<>(capacity);
        }

        @Override
        public <T> Set<T> createSet(int capacity) {
            return new LinkedHashSet<>(capacity);
        }
    },
    naturalOrdered {
        @Override
        public <K, V> Map<K, V> createMap(int capacity) {
            return new TreeMap<>();
        }

        @Override
        public <T> Set<T> createSet(int capacity) {
            return new TreeSet<>();
        }
    };

    private static final int DEFAULT_CAPACITY = 16;

    protected int getDefaultCapacity() {
        return DEFAULT_CAPACITY;
    }

    public <K, V> Map<K, V> createMap() {
        return createMap(getDefaultCapacity());
    }

    public <T> Set<T> createSet() {
        return createSet(getDefaultCapacity());
    }

    public abstract <K, V> Map<K, V> createMap(int capacity);

    public abstract <T> Set<T> createSet(int capacity);
}

package com.datalint.xml.shared.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public enum CollectionMode {
	unordered {
		@Override
		public <K, V> Map<K, V> createMap(int capacity) {
			return new HashMap<K, V>(capacity);
		}

		@Override
		public <T> Set<T> createSet(int capacity) {
			return new HashSet<T>(capacity);
		}
	},
	insertOrdered {
		@Override
		public <K, V> Map<K, V> createMap(int capacity) {
			return new LinkedHashMap<K, V>(capacity);
		}

		@Override
		public <T> Set<T> createSet(int capacity) {
			return new LinkedHashSet<T>(capacity);
		}
	},
	naturalOrdered {
		@Override
		public <K, V> Map<K, V> createMap(int capacity) {
			return new TreeMap<K, V>();
		}

		@Override
		public <T> Set<T> createSet(int capacity) {
			return new TreeSet<T>();
		}
	};

	private static int DEFAULT_CAPACITY = 16;

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

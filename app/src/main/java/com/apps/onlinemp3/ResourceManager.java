package com.apps.onlinemp3;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {

    private static Map<String, Object> dependencies = new HashMap<>();

    public static void add(String name, Object object) {
        dependencies.put(name, object);
    }

    public static <T> T get(String key) {
        return (T) dependencies.get(key);
    }

}

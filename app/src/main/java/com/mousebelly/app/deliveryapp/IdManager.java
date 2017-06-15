package com.mousebelly.app.deliveryapp;

import java.util.HashMap;


public class IdManager {

    public static HashMap stringToIdMap = new HashMap();
    public static HashMap idToStringMap = new HashMap();

    public static boolean addId(String value) {
        if (stringToIdMap.get(value) == null) {
            int index = IdManager.generateIndex(value);
            stringToIdMap.put(value, index);
            idToStringMap.put(index, value);
            return true;
        } else {
            return false;
        }
    }

    private static int generateIndex(String value) {
        int hash = 7;
        for (int i = 0; i < value.length(); i++) {
            hash = hash * 31 + value.charAt(i);
        }

        return hash;
    }

}

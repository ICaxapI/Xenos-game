package com.exgames.xenos;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Created by Olejon on 15.04.2017.
 */
public class JsonUtils {
    private static JSONObject allJSONobjects;

    public static void parseJson(String resultJson) {
        try {
            allJSONobjects = (JSONObject) JSONValue.parseWithException(resultJson);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }
    public static JSONObject parseObj(String objName) throws NullPointerException{
        JSONObject msg = null;
        if (allJSONobjects.containsKey(objName)){
            msg = (JSONObject) allJSONobjects.get(objName);
            allJSONobjects.remove(objName);
        } else {
            System.out.println("В JSON нет объекта " + objName + ".");
            throw new NullPointerException();
        }
        return msg;
    }
}

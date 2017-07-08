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
//            // конвертируем строку с Json в JSONObject для дальнейшего его парсинга
//            JSONObject infoJsonObject = (JSONObject) JSONValue.parseWithException(resultJson);
//            //System.out.println("Говорит: " + infoJsonObject.get("Name"));
//            //получаем массив элементов Dialogs
//            JSONArray infoArray = (JSONArray) infoJsonObject.get("Dialogs");
//            // достаем из массива Blades все элементы по очереди
//            String author;
//            for (int i = 0; i <= infoArray.size()-1 ; i++) {
//                JSONObject infoData = (JSONObject) infoArray.get(i);
//                // печатаем Caption в консоль
//                //System.out.println("ID этого обьекта: " + infoData.get("ID"));
//                if (infoData.get("Author") == null) {author = infoJsonObject.get("Name").toString();}
//                else {author = infoData.get("Author").toString();}
//                //System.out.println(author + " говорит: " + infoData.get("Text"))
//            }
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }
    public static JSONArray parseObj(String objName) throws NullPointerException{
        JSONArray msg = null;
        if (allJSONobjects.containsKey(objName)){
            msg = (JSONArray) allJSONobjects.get(objName);
            allJSONobjects.remove(objName);
        } else {
            System.out.println("В JSON нет объекта " + objName + ".");
            throw new NullPointerException();
        }
        return msg;
    }
}

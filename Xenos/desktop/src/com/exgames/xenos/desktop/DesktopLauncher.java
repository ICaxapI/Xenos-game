package com.exgames.xenos.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;
import com.badlogic.gdx.files.FileHandle;
import com.exgames.xenos.Main;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DesktopLauncher {
	private static boolean fullscr;
	private static boolean vSyncEn;
	private static int width;
	private static int height;
	public static float volumeMusic;
	public static float volumeSound;
	public static boolean skiplogo;
	private static LwjglFiles files;

	public static void main (String[] arg) {
		files = new LwjglFiles();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		if (files.external("Saved Games/").isDirectory()){
			checkSettings();
		} else {
			createDirectory(files.external("Saved Games/"));
			checkSettings();
		}
		config.fullscreen = fullscr;
		config.vSyncEnabled = vSyncEn;
		config.width = width;
		config.height = height;
		config.title = "Xenos";
		Main main = new Main();
		Main.setSettings(skiplogo, volumeMusic, volumeSound);
		new LwjglApplication(main, config);
	}

	private static void createDirectory(FileHandle fileHandle){
		fileHandle.mkdirs();
		System.out.println("Создана папка " + fileHandle.toString());
	}

	private static void checkSettings(){
		if (files.external("Saved Games/Xenos").isDirectory()){
			if (files.external("Saved Games/Xenos/settings.json").exists()){
				String jsonInput = files.external("Saved Games/Xenos/settings.json").readString();
				try {
					JSONObject settingsJsonObject = (JSONObject) JSONValue.parseWithException(jsonInput);
					vSyncEn = Objects.equals(settingsJsonObject.get("vSyncEnabled").toString(), "true");
					fullscr = Objects.equals(settingsJsonObject.get("fullscreen").toString(), "true");
					skiplogo = Objects.equals(settingsJsonObject.get("skiplogo").toString(), "true");
					width = Integer.parseInt(settingsJsonObject.get("width").toString());
					height = Integer.parseInt(settingsJsonObject.get("height").toString());
					volumeMusic = Float.parseFloat(settingsJsonObject.get("volumeMusic").toString());
					volumeSound = Float.parseFloat(settingsJsonObject.get("volumeSound").toString());
				} catch (ParseException ex){
					Logger.getLogger(DesktopLauncher.class.getName()).log(Level.SEVERE, null, ex);
					ex.printStackTrace();
					String info = "Не удалось прочитать файл с настройками. Настройки сброшенны по-умолчанию.";
					System.out.println(info);
					Logger.getLogger(DesktopLauncher.class.getName()).log(Level.INFO, null, info);
					vSyncEn = true;
					fullscr = false;
					skiplogo = false;
					width = 960;
					height = 540;
					volumeMusic = 0.1f;
					volumeSound = 1;
				}
			} else {
				try {
					System.out.println("Не найден файл с настройками.");
					files.external("Saved Games/Xenos/settings.json").file().createNewFile();
					JSONObject settingsJsonObject = new JSONObject();
					settingsJsonObject.put("vSyncEnabled", true);
					settingsJsonObject.put("fullscreen", false);
					settingsJsonObject.put("skiplogo", false);
					settingsJsonObject.put("width", 960);
					settingsJsonObject.put("height", 540);
					settingsJsonObject.put("volumeMusic", 0.1);
					settingsJsonObject.put("volumeSound", 1);
					FileWriter writer = new FileWriter(files.external("Saved Games/Xenos/settings.json").file());
					writer.write(settingsJsonObject.toJSONString());
					writer.flush();
					writer.close();
					System.out.println("Создали новый.");
					checkSettings();
				} catch (IOException ex){
					Logger.getLogger(DesktopLauncher.class.getName()).log(Level.SEVERE, null, ex);
					ex.printStackTrace();
					String info = "Не удалось создать файл с настройками. Загруженны настройки по умолчанию.";
					System.out.println(info);
					Logger.getLogger(DesktopLauncher.class.getName()).log(Level.INFO, null, info);
					vSyncEn = true;
					fullscr = false;
					skiplogo = false;
					width = 960;
					height = 540;
					volumeMusic = 0.1f;
					volumeSound = 1;
				}
			}
		} else {
			System.out.println("Не найденна папка.");
			createDirectory(files.external("Saved Games/Xenos"));
			System.out.println("Созданна папка игры.");
			checkSettings();
		}
	}
}

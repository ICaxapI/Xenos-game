package com.exgames.xenos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Main extends Game {
    float x = 0;
    public static Menu menu;
    public static Logo logo;

	@Override
	public void create () {
        Gdx.graphics.setTitle("Xenos");
        logo = new Logo(this);
        menu = new Menu(this);
        setScreen(logo);
	}

}

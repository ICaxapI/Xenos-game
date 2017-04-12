package com.exgames.xenos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Main extends Game {
    float x = 0;
    public static boolean skipLogo = false;
    public static Menu menu;
    public static LogoShow logo;
    public static SpriteBatch batch;
    public static OrthographicCamera camera;
    public static Viewport viewport;

	@Override
	public void create () {
        Gdx.graphics.setTitle("Xenos");
        batch = new SpriteBatch();
        camera = new OrthographicCamera(1280,720);
        camera.position.set(new Vector3(1280/2,720/2,0));
        viewport = new FitViewport(1280, 720, camera);
        logo = new LogoShow(this, batch);
        menu = new Menu(this, batch, camera, viewport);
        if (!skipLogo) {
            setScreen(logo);
        } else {
            setScreen(menu);
        }
	}

}

package com.exgames.xenos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.graphics.GL20.GL_ONE;
import static com.badlogic.gdx.graphics.GL20.GL_ONE_MINUS_SRC_ALPHA;

public class Main extends Game {
    public static boolean skipLogo = false;
    public static Menu menu;
    public static LogoShow logo;
    public static SpriteBatch batch;
    public static OrthographicCamera camera;
    public static Viewport viewport;
    public static float volumeMusic;
    public static float volumeSound;

	@Override
	public void create () {
        Gdx.gl.glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        batch = new SpriteBatch();
        camera = new OrthographicCamera(1280,720);
        camera.position.set(new Vector3(camera.viewportWidth/2,camera.viewportHeight/2,0));
        viewport = new FitViewport(1280, 720, camera);
        menu = new Menu(this, batch, camera, viewport);
        if (!skipLogo) {
            logo = new LogoShow(this, batch);
            setScreen(logo);
        } else {
            setScreen(menu);
        }
	}

	public static void setSettings(boolean skipLogo, float volumeMusic, float volumeSound){
	    Main.skipLogo = skipLogo;
	    Main.volumeMusic = volumeMusic;
	    Main.volumeSound = volumeSound;
    }
}

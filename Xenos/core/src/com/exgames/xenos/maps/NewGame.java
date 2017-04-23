package com.exgames.xenos.maps;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.PerformanceCounter;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.exgames.xenos.JsonUtils;
import com.exgames.xenos.WorldBuilder;
import com.exgames.xenos.actors.Cloud;
import com.exgames.xenos.actors.WorldObject;

import static com.exgames.xenos.Main.camera;


/**
 * Created by Alex on 09.04.2017.
 */
public class NewGame extends WorldBuilder {
    private BitmapFont font;
    private Texture cloud;
    private Vector2 wallModelOrigin;
    private WorldObject wall;

    public NewGame(Game game, SpriteBatch batch, Viewport viewport) {
        super(game, batch, viewport);
    }

    public void show(){
        super.show();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("resources/font/4.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.characters = FONT_CHARACTERS;
        parameter.renderCount = 2;
        font = generator.generateFont(parameter);
        font.getData().scale(0.005f);
        generator.dispose();

        PerformanceCounter kek = new PerformanceCounter("kek");
        PerformanceCounter kek2 = new PerformanceCounter("kek2");
        kek.start();
        String jsonInput = Gdx.files.internal("resources/writing/test.json").readString();
        kek.stop();
        kek2.start();
        JsonUtils.parseCurrentInfoJson(jsonInput);
        kek2.stop();
        System.out.println("Время парсинга Json: " + kek2.current);
        System.out.println("Время загрузки Json: " + kek.current);
        cloud = new Texture(Gdx.files.internal("resources/entities/cloud.png"));
        String string = "Ну такое.";
        Cloud cloudActor = new Cloud(cloud,20,40 , string, font, stage, 5,60, "resources/music/peek.wav");
        wall = new WorldObject("NewWorld", "CaptainCabin", BodyDef.BodyType.StaticBody, 100, 0, 0, 1, 1, camera.viewportWidth/2f,camera.viewportHeight/2f);
        createNewObj(wall);
    }
}

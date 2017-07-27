package com.exgames.xenos.maps;

import box2dLight.PointLight;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.utils.PerformanceCounter;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.exgames.xenos.JsonUtils;
import com.exgames.xenos.WorldBuilder;
import com.exgames.xenos.actors.Cloud;
import com.exgames.xenos.actors.Detector;
import com.exgames.xenos.actors.Door;
import com.exgames.xenos.actors.WorldObject;
import com.sun.org.apache.bcel.internal.generic.FADD;

import static com.exgames.xenos.Main.camera;


/**
 * Created by Alex on 09.04.2017.
 */
public class NewGame extends WorldBuilder {
    private BitmapFont font;
    private Texture ship;
    private Texture metalBoxTex;
    private Vector2 wallModelOrigin;
    private WorldObject glad;
    private WorldObject leks;
    private WorldObject testFont;
    private Door door;

    public NewGame(Game game, SpriteBatch batch, Viewport viewport) {
        super(game, batch, viewport);
    }

    public void show(){
        super.show();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("resources/font/7.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.genMipMaps = true;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Nearest;
        parameter.characters = FONT_CHARACTERS;
        parameter.renderCount = 1;
        parameter.incremental = true;
        parameter.mono = true;
        font = generator.generateFont(parameter);
        font.getData().scale(0.0001f);
        generator.dispose();
        metalBoxTex = new Texture(Gdx.files.internal("resources/texture/metalBox.png"));
        ship = new Texture(Gdx.files.internal("resources/texture/ship.png"));
        PerformanceCounter kek = new PerformanceCounter("kek");
        kek.start();
        String jsonInput = Gdx.files.internal("resources/writing/test.json").readString();
        kek.stop();
        JsonUtils.parseJson(jsonInput);
        System.out.println("Время загрузки Json: " + kek.current);

        testFont = new WorldObject(metalBoxTex, "NewWorld", "metalBox", BodyDef.BodyType.DynamicBody, 100,
                0.35f, 0.34f, 15, 0, 0, 1, -1, 0);
        createNewObj(testFont, 0.35f, 0.34f, CATEGORY_WALL, MASK_WALL);
        testFont.addInputListener("Тест шрифта", font, stage);

        leks = new WorldObject(metalBoxTex, "NewWorld", "metalBox", BodyDef.BodyType.DynamicBody, 100,
                0.35f, 0.34f, 15, 0, 0, 1, 2, 0);
        createNewObj(leks, 0.35f, 0.34f, CATEGORY_WALL, MASK_WALL);
        leks.addInputListener("Лекс", font, stage);

        glad = new WorldObject(metalBoxTex, "NewWorld", "metalBox", BodyDef.BodyType.DynamicBody, 100,
                0.35f, 0.34f, 15, 0, 0, 1, 1, 0);
        createNewObj(glad, 0.35f, 0.34f, CATEGORY_WALL, MASK_WALL);
        glad.addInputListener("ГЛЭД", font, stage);

        //wall = new WorldObject(ship, "NewWorld", "Ship", BodyDef.BodyType.StaticBody, 100,
                //15.9f, 21.55f, 0, 0, 0, 1);
        //createNewObj(wall, 15.9f, 21.55f, CATEGORY_WALL, MASK_WALL);

        door = new Door("NewWorld", "Door", BodyDef.BodyType.KinematicBody, 100, 0, 0, 0, 0, camera.viewportWidth/2f-2.125f,camera.viewportHeight/2f-0.5f, true);
        createNewObj(door, CATEGORY_OBJECTS, MASK_OBJECTS);

        Detector detector = new Detector(0.5f, door.getRect().getWorldPoint(door.getRect().getLocalCenter()).x,camera.viewportHeight/2f-0.5f);
        createDetector(door, detector, CATEGORY_SENSOR, MASK_SENSOR);
        door.initVector();

        PointLight herolight = new PointLight(handler, 500, Color.WHITE, 7f, 0,0);
        herolight.attachToBody(getHeroBody(), 0.29f, 0.43F);
        herolight.setSoft(false);
        herolight.setIgnoreAttachedBody(true);

        Filter filterLight = new Filter();
        filterLight.categoryBits = CATEGORY_LIGHT;
        filterLight.maskBits = MASK_LIGHT;
        herolight.setContactFilter(filterLight);

//        stage.setDebugAll(true); //Дебаг стейджа!
    }
}

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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.utils.PerformanceCounter;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.exgames.xenos.JsonUtils;
import com.exgames.xenos.UserData;
import com.exgames.xenos.WorldBuilder;
import com.exgames.xenos.actors.Cloud;
import com.exgames.xenos.actors.Detector;
import com.exgames.xenos.actors.Door;
import com.exgames.xenos.actors.WorldObject;
import com.sun.org.apache.bcel.internal.generic.FADD;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static com.exgames.xenos.Main.camera;


/**
 * Created by Alex on 09.04.2017.
 */
public class NewGame extends WorldBuilder {
    private BitmapFont font;
    private Texture metalBoxTex;
    private Vector2 wallModelOrigin;
    private WorldObject glad;
    private WorldObject leks;
    private WorldObject testFont;
    private WorldObject corfloor;
    private WorldObject k1floor; //каюта капитана
    private WorldObject k1wall;
    private WorldObject k1wall2;
    private WorldObject k2floor; //каюта альберт
    private WorldObject k2wall;
    private WorldObject k2wall2;
    private WorldObject k3floor; //каюта анны
    private WorldObject k3wall;
    private WorldObject k3wall2;
    private WorldObject orfloor; //оружейная
    private WorldObject orwall;
    private WorldObject orwall2;
    private WorldObject izfloor; //изолятор
    private WorldObject izwall;
    private WorldObject izwall2;
    private WorldObject bdfloor; //туалет
    private WorldObject bdbwall;
    private WorldObject bdwall2;
    private WorldObject medfloor; //мед отсек
    private WorldObject medwall;
    private WorldObject medwall2;
    private WorldObject bkfloor; //бортовой компьютер
    private WorldObject bkwall;
    private WorldObject bkwall2;
    private WorldObject kitfloor;
    private WorldObject kitwall;
    private WorldObject kitwall2;
    private Door door;
    private TextureAtlas shipAtlas;

    public NewGame(Game game, SpriteBatch batch, Viewport viewport) {
        super(game, batch, viewport);
    }

    public void show(){
        super.show();
        shipAtlas = new TextureAtlas(Gdx.files.internal("resources/maps/NewWorld.txt"));
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
        PerformanceCounter kek = new PerformanceCounter("kek");
        kek.start();
        String jsonInput = Gdx.files.internal("resources/writing/test.json").readString();
        kek.stop();
        JsonUtils.parseJson(jsonInput);
        System.out.println("Время загрузки Json: " + kek.current);

//        testFont = new WorldObject(metalBoxTex, "NewWorld", "metalBox", BodyDef.BodyType.DynamicBody, 100,
//                1.05f, 1.02f, 15, 0, 0, 1, -1, 0);
//        createNewObj(testFont, 1.05f, 1.02f, CATEGORY_WALL, MASK_WALL);
//        testFont.addInputListener("Тест шрифта", font, stage);
//
//        leks = new WorldObject(metalBoxTex, "NewWorld", "metalBox", BodyDef.BodyType.DynamicBody, 100,
//                1.05f, 1.02f, 15, 0, 0, 1, 2, 0);
//        createNewObj(leks, 1.05f, 1.02f, CATEGORY_WALL, MASK_WALL);
//        leks.addInputListener("Лекс", font, stage);
//
//        glad = new WorldObject(metalBoxTex, "NewWorld", "metalBox", BodyDef.BodyType.DynamicBody, 100,
//                1.05f, 1.02f, 15, 0, 0, 1, 1, 0);
//        createNewObj(glad, 1.05f, 1.02f, CATEGORY_WALL, MASK_WALL);
//        glad.addInputListener("ГЛЭД", font, stage);

        //wall = new WorldObject(ship, "NewWorld", "Ship", BodyDef.BodyType.StaticBody, 100,
                //15.9f, 21.55f, 0, 0, 0, 1);
        //createNewObj(wall, 15.9f, 21.55f, CATEGORY_WALL, MASK_WALL);

//        door = new Door("NewWorld", "Door", BodyDef.BodyType.KinematicBody, 100, 0, 0, 0, 0, camera.viewportWidth/2f-2.125f,camera.viewportHeight/2f-0.5f, true);
//        createNewObj(door, CATEGORY_OBJECTS, MASK_OBJECTS);

//        Detector detector = new Detector(0.5f, door.getRect().getWorldPoint(door.getRect().getLocalCenter()).x,camera.viewportHeight/2f-0.5f);
//        createDetector(door, detector, CATEGORY_SENSOR, MASK_SENSOR);
//        door.initVector();

//        PointLight herolight = new PointLight(handler, 500, Color.WHITE, 4.5f, 0,0);
//        herolight.attachToBody(getHeroBody(), 0.29f, 0.05f);
//        herolight.setSoft(false);
//        herolight.setIgnoreAttachedBody(true);

//        Filter filterLight = new Filter();
//        filterLight.categoryBits = CATEGORY_LIGHT;
//        filterLight.maskBits = MASK_LIGHT;
//        herolight.setContactFilter(filterLight);

//        stage.setDebugAll(true); //Дебаг стейджа!

        createroom(orfloor, orwall, orwall2, 0,0);
        createroom(izfloor, izwall, izwall2, 0,1);
        createroom(bdfloor, bdbwall, bdwall2, 0,2);
        createroom(medfloor, medwall, medwall2, 0,3);
        createroom(bkfloor, bkwall, bkwall2, 0,4);
        createroom(k1floor, k1wall, k1wall2, 1,0);
        createroom(k2floor, k2wall, k2wall2, 1,1);
        createroom(k3floor, k3wall, k3wall2, 1,2);

        corfloor = new WorldObject(shipAtlas.createSprite("corfloor"), "NewWorld", "floor", BodyDef.BodyType.StaticBody, 100,
                0.91f*3, 5.44f*3, 0, 0, 0, 0, 1.73f*3, 0.12f*3);
        createNewObj(corfloor, 0.91f*3, 5.44f*3, CATEGORY_FLOOR, MASK_FLOOR);



        kitfloor = new WorldObject(shipAtlas.createSprite("kitchen"), "NewWorld", "kitFloor", BodyDef.BodyType.StaticBody, 100,
                1.48f*3, 2.11f*3, 0, 0, 0, 0, 2.75f*3, 3.46f*3);
        createNewObj(kitfloor, 1.48f*3, 2.11f*3, CATEGORY_FLOOR, MASK_FLOOR);
        Body last = getLastBody();
        kitwall2 = new WorldObject(shipAtlas.createSprite("wall2"), "NewWorld", "floor", BodyDef.BodyType.StaticBody, 100,
                1.48f*3, 0.96f*3, 0, 0, 0, 0, 2.75f*3, 3.35f*3);
        createNewWall(kitwall2, 1.48f*3, 0.96f*3, CATEGORY_FLOOR, MASK_FLOOR);
        kitwall = new WorldObject(shipAtlas.createSprite("wall1"), "NewWorld", "Wall", BodyDef.BodyType.StaticBody, 100,
                1.48f*3, 0.96f*3, 0, 0, 0, 0, 2.75f*3, 3.35f*3);
        createNewWall(kitwall, 1.48f*3, 0.96f*3, CATEGORY_FLOOR, MASK_FLOOR);
        UserData userData = new UserData("DetectorFloor",  kitwall,  kitwall2);
        last.setUserData(userData);
    }

    private void createroom(WorldObject floor, WorldObject wall, WorldObject wall2, int x, int y){
        float xdraw = 0;
        if (x == 0){
            xdraw = 0.13f*3;
        } else {
            xdraw = 2.75f*3;
        }
        float ydraw = (0.02f + (1.11f*y))*3;
        floor = new WorldObject(shipAtlas.createSprite("roomfloor"), "NewWorld", "roomFloor", BodyDef.BodyType.StaticBody, 100,
                1.48f*3, 1.0f*3, 0, 0, 0, 0, xdraw, ydraw + 0.11f*3);
        createNewObj(floor, 1.48f*3, 1.0f*3, CATEGORY_FLOOR, MASK_FLOOR);
        Body last = getLastBody();
        wall2 = new WorldObject(shipAtlas.createSprite("wall2"), "NewWorld", "floor", BodyDef.BodyType.StaticBody, 100,
                1.48f*3, 0.96f*3, 0, 0, 0, 0, xdraw, ydraw);
        createNewWall(wall2, 1.48f*3, 0.96f*3, CATEGORY_FLOOR, MASK_FLOOR);
        wall = new WorldObject(shipAtlas.createSprite("wall1"), "NewWorld", "Wall", BodyDef.BodyType.StaticBody, 100,
                1.48f*3, 0.96f*3, 0, 0, 0, 0, xdraw, ydraw);
        createNewWall(wall, 1.48f*3, 0.96f*3, CATEGORY_FLOOR, MASK_FLOOR);
        UserData userData = new UserData("DetectorFloor", wall, wall2);
        last.setUserData(userData);
    }

    @Override
    public void dispose(){
        shipAtlas.dispose();
        super.dispose();
    }
}

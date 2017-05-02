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
import com.exgames.xenos.actors.Detector;
import com.exgames.xenos.actors.Door;
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
    private Door door;

    public NewGame(Game game, SpriteBatch batch, Viewport viewport) {
        super(game, batch, viewport);
    }

    public void show(){
        super.show();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("resources/font/6.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.characters = FONT_CHARACTERS;
        parameter.renderCount = 1;
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
        String string = "Дамы и господа! И господамы. И дамы-господа. И господа-дамы.\n" +
                "И господа в теле дам, дамы в теле господ. И дамы, которые раньше были\n" +
                "господами, а теперь дамы. И господа, которые раньше были в теле дам, а\n" +
                "теперь господа. Не дамы и не господа. И дамы, которые иногда господа, но\n" +
                "чаще дамы, и господа, которые иногда дамы, но чаще господа, и дамы, которые\n" +
                "преимущественно господа и иногда дамы, и господа, которые преимущественно\n" +
                "дамы и иногда господа. И то господа, то дамы. Господа, которые пришли в\n" +
                "костюмах дам, дамы, которые пришли в костюме господ. Те, кто пока не\n" +
                "определился. Господствующие дамы и дамствующие господа. Ламы и газвода.     \n" +
                "...\n" +
                "Я забыл, зачем мы собрались.";
       // Cloud cloudActor = new Cloud(cloud,20,40 , string, font, stage, 5,60, "resources/music/peek.wav");
        wall = new WorldObject("NewWorld", "CaptainCabin", BodyDef.BodyType.StaticBody, 100, 0, 0, 0, 5, camera.viewportWidth/2f,camera.viewportHeight/2f);
        createNewObj(wall);
        door = new Door("NewWorld", "Door", BodyDef.BodyType.KinematicBody, 100, 0, 0, 0, 0, camera.viewportWidth/2f-2.125f,camera.viewportHeight/2f-0.5f, true);
        createNewObj(door);
        Detector detector = new Detector(0.5f, door.getRect().getWorldPoint(door.getRect().getLocalCenter()).x,camera.viewportHeight/2f-0.5f);
        createDetector(door, detector);
        door.initVector();
    }
}

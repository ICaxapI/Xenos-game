package com.exgames.xenos.maps;

import aurelienribon.bodyeditor.BodyEditorLoader;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.PerformanceCounter;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.exgames.xenos.JsonUtils;
import com.exgames.xenos.WorldBuilder;
import com.exgames.xenos.actors.Cloud;


/**
 * Created by Alex on 09.04.2017.
 */
public class NewGame extends WorldBuilder {
    private BitmapFont font;
    private Texture cloud;

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
        String string = "Можно грабить корованы. А можно и не.";
        Cloud cloudActor = new Cloud(cloud,20,40 , string, font, stage, 5,60, "resources/music/peek.wav");
        createVall();
    }

    public void createVall(){
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("resources/maps/NewWorld.json"));
        BodyDef body = new BodyDef();
        body.type = BodyDef.BodyType.StaticBody;
        body.position.set(getCamera().viewportWidth/2f,getCamera().viewportHeight/2f);
        body.linearDamping = 1.0f;
        body.angularDamping = 4.0f;
        Body wall = getWorld().createBody(body);
        FixtureDef fdef = new FixtureDef();
        fdef.restitution = 0.0f;
        fdef.friction = 2.0f;
        fdef.density = 100;
        loader.attachFixture(wall,"CaptainCabin",fdef,1f);
    }
}

package com.exgames.xenos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Alex on 09.04.2017.
 */
public class newWorld implements Screen {
    public static double mouseGrad;
    public static boolean updateGrad = true;

    private Game game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Viewport viewport;
    private Stage stage;
    private boolean press = false;
    private World world;
    private Box2DDebugRenderer renderer;
    protected static Body rect;
    private InputController inputController;
    protected static float centerx;
    protected static float centery;
    BitmapFont font;
//    BitmapFont font;
    public static final String FONT_CHARACTERS = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфЧчЦцЧчШшЩщЪъЫыЬьЭэЮюЯяabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";

    private float gradneed;
    private double gradRect;


    public newWorld(Game game, SpriteBatch batch, Viewport viewport){
        this.game = game;
        this.camera = new OrthographicCamera(16,9);;
        camera.position.set(new Vector3(camera.viewportWidth/2f,camera.viewportHeight/2f,0));
        this.batch = batch;
        this.viewport = viewport;
    }

    @Override
    public void show() {
        stage = new Stage(viewport);
        world = new World(new Vector2(0,0),true);
        renderer = new Box2DDebugRenderer();
        createRect();
        renderer.setDrawBodies(true);
        renderer.setDrawContacts(true);
        renderer.setDrawInactiveBodies(true);
        renderer.setDrawJoints(true);
        renderer.setDrawVelocities(true);
        inputController = new InputController();
        Gdx.input.setInputProcessor(inputController);
        centerx = Gdx.graphics.getWidth()/2f;
        centery = Gdx.graphics.getHeight()/2f;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("resources/font/1.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.characters = FONT_CHARACTERS;
        parameter.magFilter = Texture.TextureFilter.Nearest;
        parameter.minFilter = Texture.TextureFilter.Linear;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    private void createRect(){
        BodyDef body = new BodyDef();
        body.type = BodyDef.BodyType.DynamicBody;
        body.position.set(camera.viewportWidth/2f,camera.viewportHeight/2f);
        body.linearDamping = 1.0f;
        body.angularDamping = 4.0f;
        rect = world.createBody(body);
        FixtureDef fdef = new FixtureDef();
        fdef.restitution = 0.0f;
        fdef.friction = 2.0f;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.2f,1.5f);
        fdef.shape = shape;
        fdef.density = 100;
        rect.createFixture(fdef);
    }

    @Override
    public void render(float delta) {
        if (updateGrad){
            updateMouse();
        }
        float fps = Gdx.graphics.getFramesPerSecond();
        if (fps < 1){
            fps = 60;
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.render(world,camera.combined);
        world.step(1/fps,5,5);
        batch.setProjectionMatrix(camera.combined);
        stage.act(delta);
        stage.getBatch().begin();
        font.draw(stage.getBatch(), "Лол кек чебурек :D" , 0, stage.getHeight()/2);
        font.draw(stage.getBatch(), "Lol kek 4eburek" , 0, stage.getHeight()/2 - 20*3 - font.getXHeight()*3);
        stage.getBatch().end();
        stage.draw();
        batch.begin();
        batch.end();

    }

    public void updateMouse(){
        double gradRect = Math.toDegrees(newWorld.rect.getAngle());
        if (gradRect >= 0) {
            while (gradRect >= 360) {
                gradRect -= 360;
            }
        } else {
            while (gradRect <= -360) {
                gradRect += 360;
            }
            gradRect += 360;
        }
        gradneed = (float) mouseGrad - (float) gradRect;
        if        (gradneed >= 0 & Math.abs(gradneed) < 180){
            newWorld.rect.setAngularVelocity(gradneed/5);
        } else if (gradneed <= 0 & Math.abs(gradneed) < 180){
            newWorld.rect.setAngularVelocity(gradneed/5);
        } else if (gradneed >= 0 & Math.abs(gradneed) >= 180){
            if (gradneed < 0) {
                newWorld.rect.setAngularVelocity((-360 - gradneed)/5);
            } else {
                newWorld.rect.setAngularVelocity((-360 + gradneed)/5);
            }
        } else if (gradneed <= 0 & Math.abs(gradneed) >= 180){
            if (gradneed < 0) {
                newWorld.rect.setAngularVelocity(( 360 + gradneed)/5);
            } else {
                newWorld.rect.setAngularVelocity(( 360 - gradneed)/5);
            }
        }
        if (Math.abs(gradneed) <= 0.005f){
            updateGrad = false;
        }
    }

    @Override
    public void resize(int width, int height) {
        centerx = Gdx.graphics.getWidth()/2f;
        centery = Gdx.graphics.getHeight()/2f;
        viewport.update(width, height);
        System.out.println(centerx + " " + centery);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

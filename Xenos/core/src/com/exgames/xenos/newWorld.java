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
import com.badlogic.gdx.utils.PerformanceCounter;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.exgames.xenos.actors.Cloud;


/**
 * Created by Alex on 09.04.2017.
 */
public class NewWorld implements Screen {
    public static double mouseGrad;
    public static boolean updateGrad = true;

    private Texture cloud;

    private Game game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Viewport viewport;
    private Stage stage;
    private boolean press = false;
    private World world;
    private Box2DDebugRenderer renderer;
    static Body rect;
    private InputController inputController;
    static float centerx;
    static float centery;
    private BitmapFont font;
    private BitmapFont font2;
    private BitmapFont font3;
//    BitmapFont font;
    public static final String FONT_CHARACTERS = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфЧчЦцЧчШшЩщЪъЫыЬХхьЭэЮюЯяabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";

    private float gradneed;
    private double gradRect;


    public NewWorld(Game game, SpriteBatch batch, Viewport viewport){
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

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("resources/font/4.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.characters = FONT_CHARACTERS;
        parameter.renderCount = 2;
        font = generator.generateFont(parameter);
        font.getData().scale(0.005f);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("resources/font/5.ttf"));
        parameter.size = 50;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.Linear;
        font2 = generator.generateFont(parameter);
        font2.getData().scale(0.005f);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("resources/font/6.ttf"));
        font3 = generator.generateFont(parameter);
        font3.getData().scale(0.005f);
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
        Cloud cloudActor2 = new Cloud(cloud,20,450 , string, font2, stage, 5,20, "resources/music/peek.wav");
        Cloud cloudActor3 = new Cloud(cloud,20,200 , string, font3, stage, 5,40, "resources/music/peek.wav");


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
        shape.setAsBox(1f,1f);
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
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.render(world,camera.combined);
        world.step(1/fps,5,5);
        batch.setProjectionMatrix(camera.combined);
        stage.act(delta);
        stage.draw();
        batch.begin();
        batch.end();

    }

    public void updateMouse(){
        double gradRect = Math.toDegrees(NewWorld.rect.getAngle());
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
            NewWorld.rect.setAngularVelocity(gradneed/5);
        } else if (gradneed <= 0 & Math.abs(gradneed) < 180){
            NewWorld.rect.setAngularVelocity(gradneed/5);
        } else if (gradneed >= 0 & Math.abs(gradneed) >= 180){
            if (gradneed < 0) {
                NewWorld.rect.setAngularVelocity((-360 - gradneed)/5);
            } else {
                NewWorld.rect.setAngularVelocity((-360 + gradneed)/5);
            }
        } else if (gradneed <= 0 & Math.abs(gradneed) >= 180){
            if (gradneed < 0) {
                NewWorld.rect.setAngularVelocity(( 360 + gradneed)/5);
            } else {
                NewWorld.rect.setAngularVelocity(( 360 - gradneed)/5);
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

package com.exgames.xenos;

import aurelienribon.bodyeditor.BodyEditorLoader;
import box2dLight.BlendFunc;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.exgames.xenos.actors.Detector;
import com.exgames.xenos.actors.Door;
import com.exgames.xenos.actors.Hero;
import com.exgames.xenos.actors.WorldObject;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

import static com.badlogic.gdx.graphics.GL20.GL_ONE;
import static com.badlogic.gdx.graphics.GL20.GL_ONE_MINUS_SRC_ALPHA;
import static com.badlogic.gdx.graphics.GL20.GL_SRC_ALPHA;
import static java.lang.Math.PI;
import static java.lang.Math.atan2;


/**
 * Created by Alex on 09.04.2017.
 */
public class WorldBuilder implements Screen {
    final public static short CATEGORY_SENSOR = 0x0001;
    final public static short CATEGORY_PLAYER = 0x0002;
    final public static short CATEGORY_WALL = 0x0004;
    final public static short CATEGORY_LIGHT = 0x0008;
    final public static short CATEGORY_OBJECTS = 0x0016;
    final public static short MASK_SENSOR = CATEGORY_PLAYER | CATEGORY_OBJECTS;
    final public static short MASK_PLAYER = -1;
    final public static short MASK_WALL = CATEGORY_LIGHT | CATEGORY_PLAYER | CATEGORY_OBJECTS;
    final public static short MASK_LIGHT = CATEGORY_WALL | CATEGORY_OBJECTS | CATEGORY_PLAYER;
    final public static short MASK_OBJECTS = -1;

    public static final float PIXINMET = 1f/23f;
    private static Camera camera;
    private Vector2 heroModelOrigin;
    private static double mouseGrad;
    private static boolean keyboardUpdate = false;
    private BodyEditorLoader loader;
    private Texture texhero;
    private Hero hero;
    private Game game;
    private SpriteBatch batch;
    private Viewport viewport;
    protected Stage stage;
    private boolean press = false;
    private com.badlogic.gdx.physics.box2d.World world;
    private Box2DDebugRenderer renderer;
    private static Body heroBody;
    private InputController inputController;
    private static float centerx;
    private static float centery;
    private boolean mouseUpdate;
    public static final String FONT_CHARACTERS = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфЧчЦцЧчШшЩщЪъЫыЬХхьЭэЮюЯяabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
    private Vector2 heroVector;
    protected static ArrayList<Body> listBody = new ArrayList<>();
    protected static ArrayList<WorldObject> listObjects = new ArrayList<>();

    public RayHandler handler;


    private float gradneed;

    public WorldBuilder(Game game, SpriteBatch batch, Viewport viewport){
        this.game = game;
        this.camera = new OrthographicCamera(16,9);
        camera.position.set(new Vector3(camera.viewportWidth/2f,camera.viewportHeight/2f,0));
        this.batch = batch;
        this.viewport = viewport;
    }

    @Override
    public void show() {
        heroVector = new Vector2(0,0);
        stage = new Stage(viewport);
        world = new com.badlogic.gdx.physics.box2d.World(new Vector2(0,0),true);
        world.setContactListener(new MyContactListener());
        renderer = new Box2DDebugRenderer();
        renderer.setDrawBodies(true);
        renderer.setDrawContacts(true);
        renderer.setDrawInactiveBodies(true);
        renderer.setDrawJoints(true);
        renderer.setDrawVelocities(true);
        inputController = new InputController(this);
        Gdx.input.setInputProcessor(inputController);
        centerx = Gdx.graphics.getWidth()/2f;
        centery = Gdx.graphics.getHeight()/2f;
        texhero = new Texture(Gdx.files.internal("resources/texture/hero2.png"));
        texhero.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        hero = new Hero(texhero, "NewWorld", "Hero", 100);
        heroBody = createNewObj(hero, heroBody);
        heroBody.setUserData(new UserData(hero.getNameModel()));
        Filter filterHero = new Filter();
        filterHero.categoryBits = CATEGORY_PLAYER;
        filterHero.maskBits = MASK_PLAYER;
        for (int i = 0; i < hero.getFixtureLastIndex(); i++){
            hero.getFixture(i).setFilterData(filterHero);
        }

        handler = new RayHandler(world);
        RayHandler.setGammaCorrection(true);
        handler.setBlur(true);
        handler.setBlurNum(10);
        handler.setAmbientLight(0.1f);
        handler.setCulling(false);
    }

    private Body createNewObj(WorldObject object, Body body){
        body = world.createBody(object.getBody());
        object.setRect(body);
        object.attFix();
        return body;
    }

    public void createDetector(Door door, Detector detector){
        Body body = world.createBody(detector.getBody());
        listBody.add(listBody.size(), body);
        listObjects.add(listObjects.size(), detector);
        detector.setRect(body);
        detector.attFix(body);
        UserData userData = new UserData("Detector", door);
        body.setUserData(userData);
    }

    protected void createNewObj(WorldObject object){
        Body body = world.createBody(object.getBody());
        listBody.add(listBody.size(), body);
        listObjects.add(listObjects.size(), object);
        object.setRect(body);
        object.attFix();
        body.setUserData(new UserData(object.getNameModel()));
    }
    protected void createNewObj(WorldObject object, float sizex, float sizey){
        Body body = world.createBody(object.getBody());
        listBody.add(listBody.size(), body);
        listObjects.add(listObjects.size(), object);
        object.setRect(body);
        object.attFix(sizex);
        body.setUserData(new UserData(object.getNameModel()));
    }

    public Camera getCamera(){
        return this.camera;
    }
    public World getWorld(){
        return this.world;
    }
    @Override
    public void render(float delta) {
        float fps = Gdx.graphics.getFramesPerSecond();
        if (fps < 1){
            fps = 60;
        }
        Gdx.gl.glClearColor(0.5f,0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateGrad(this);
        batch.begin();
        for (int i = 0; i < listObjects.size(); i++) {
            listObjects.get(i).draw(batch);
        }
        hero.draw(batch);
        batch.end();
        camera.position.set(heroBody.getWorldPoint(heroBody.getLocalCenter()),0);
        camera.update();
        handler.setCombinedMatrix((OrthographicCamera) camera);
        handler.updateAndRender();
        //renderer.render(world,camera.combined);
        world.step(1/fps,5,5);
        batch.setProjectionMatrix(camera.combined);
        stage.act(delta);
        stage.draw();
        updateHeroInput();
        //System.out.println(world.getFixtureCount());
    }
    public void setHeroVector(Vector2 updateVector){
        heroVector = updateVector;
    }
    public Vector2 getHeroVector(){
        return heroVector;
    }
    public void updateHeroInput(){
        getHeroBody().setLinearVelocity(heroVector);
    }

    public void updateGrad(WorldBuilder world){
        double gradRect = Math.toDegrees(world.heroBody.getAngle());
        if (mouseUpdate) {
            gradneed = (float) mouseGrad - (float) gradRect;
            while (gradneed >= 359.99f){
                gradneed -= 360;
            }
            while (gradneed <= -359.99f){
                gradneed += 360;
            }
        } else if (keyboardUpdate){
            if (heroVector.x == 0 & heroVector.y == 0) {

            } else {
                mouseGrad = atan2(heroVector.x, heroVector.y) * 180 / PI;
                mouseGrad = 360 - mouseGrad + 90;
                gradneed = (float) Math.abs(mouseGrad) - (float) gradRect;
                while (gradneed >= 359.99f) {
                    gradneed -= 360;
                }
                while (gradneed <= -359.99f) {
                    gradneed += 360;
                }
            }
        } else {
            gradneed = 0;
        }
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
        if        (gradneed >= 0 & Math.abs(gradneed) < 180){
            world.heroBody.setAngularVelocity(gradneed/5);
        } else if (gradneed <= 0 & Math.abs(gradneed) < 180){
            world.heroBody.setAngularVelocity(gradneed/5);
        } else if (gradneed >= 0 & Math.abs(gradneed) >= 180){
            if (gradneed < 0) {
                world.heroBody.setAngularVelocity((-360 - gradneed)/5);
            } else {
                world.heroBody.setAngularVelocity((-360 + gradneed)/5);
            }
        } else if (gradneed <= 0 & Math.abs(gradneed) >= 180){
            if (gradneed < 0) {
                world.heroBody.setAngularVelocity(( 360 + gradneed)/5);
            } else {
                world.heroBody.setAngularVelocity(( 360 - gradneed)/5);
            }
        }
        if (Math.abs(gradneed) <= 0.1f){
            setMouseUpdate(false);
        }
    }
    public void setMouseGrad(double temp){
        mouseGrad = temp;
    }
    public void setMouseUpdate(boolean temp){
        mouseUpdate = temp;
    }
    public void setKeyboardUpdate(boolean temp){
        keyboardUpdate = temp;
    }
    public Body getHeroBody(){
        return heroBody;
    }
    public float getCenterx(){
        return centerx;
    }
    public float getCentery(){
        return centery;
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

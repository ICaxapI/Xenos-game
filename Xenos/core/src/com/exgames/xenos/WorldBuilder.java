package com.exgames.xenos;

import aurelienribon.bodyeditor.BodyEditorLoader;
import box2dLight.BlendFunc;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
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
import shaders.DiffuseShader;

import java.util.ArrayList;

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
    final public static short CATEGORY_FLOOR = 0x0032;
    final public static short MASK_SENSOR = CATEGORY_PLAYER | CATEGORY_OBJECTS;
    final public static short MASK_PLAYER = -1;
    final public static short MASK_WALL = CATEGORY_LIGHT | CATEGORY_PLAYER | CATEGORY_OBJECTS;
    final public static short MASK_LIGHT = CATEGORY_WALL | CATEGORY_OBJECTS | CATEGORY_PLAYER;
    final public static short MASK_OBJECTS = -1;
    final public static short MASK_FLOOR = -1;

    public static final float PIXINMET = 1f/23f;
    private static Camera camera;
    private Vector2 heroModelOrigin;
    private static double mouseGrad;
    private static boolean keyboardUpdate = false;
    private BodyEditorLoader loader;
    private Texture texhero;
    private static Hero hero;
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
    protected static ArrayList<WorldObject> listWalls = new ArrayList<>();
    protected static ArrayList<WorldObject> upperAll = new ArrayList<>();
    public static Music music;
    private boolean heroleftside = false;
    private Body lastBody;

    public RayHandler handler;


    private static float gradneed;

    public WorldBuilder(Game game, SpriteBatch batch, Viewport viewport){
        this.game = game;
        this.camera = new OrthographicCamera(16f,9f);
        camera.position.set(new Vector3(camera.viewportWidth/2f,camera.viewportHeight/2f,0));
        this.batch = batch;
        this.viewport = viewport;
    }

    @Override
    public void show() {
        InputMultiplexer multiplexer = new InputMultiplexer();

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
        centerx = Gdx.graphics.getWidth()/2f;
        centery = Gdx.graphics.getHeight()/2f;
        texhero = new Texture(Gdx.files.internal("resources/texture/AnimationHero.png"));
        texhero.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        hero = new Hero(texhero, "NewWorld", "Hero", 100);
        heroBody = createNewObj(hero, heroBody, CATEGORY_PLAYER, MASK_PLAYER);
        heroBody.setUserData(new UserData(hero.getNameModel()));
        hero.setName("Hero");
        hero.getRect().setFixedRotation(true);


        handler = new RayHandler(world);
        handler.setLightShader(Shader.createShader());
        handler.setAmbientLight(1f);
        handler.setBlur(true);
        handler.setBlurNum(1);
        handler.setCulling(true);
        handler.setLightMapRendering(true);
        handler.setShadows(true);
        handler.resizeFBO(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

        //hero.addInputListener("Hero");
        stage.addActor(hero);
        hero.setAnglex(0);// смещение до центра, если в физ модели отчёт начинается не с 0;0
        hero.setAngley(0);


        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(inputController);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private Body createNewObj(WorldObject object, Body body, short category, short mask){
        body = world.createBody(object.getBody());
        object.setRect(body);
        object.attFix();
        Filter filter = new Filter();
        filter.categoryBits = category;
        filter.maskBits = mask;
        for (int i = 0; i < object.getFixtureLastIndex(); i++){
            object.getFixture(i).setFilterData(filter);
        }
        return body;
    }

    public void createDetector(Door door, Detector detector, short category, short mask){
        Body body = world.createBody(detector.getBody());
        listBody.add(listBody.size(), body);
        listObjects.add(listObjects.size(), detector);
        detector.setRect(body);
        detector.attFix(body);
        UserData userData = new UserData("Detector", door);
        body.setUserData(userData);
        Filter filter = new Filter();
        filter.categoryBits = category;
        filter.maskBits = mask;
        detector.getRect().getFixtureList().forEach(fixture -> fixture.setFilterData(filter));
    }

    protected void createNewObj(WorldObject object, short category, short mask){
        Body body = world.createBody(object.getBody());
        listBody.add(listBody.size(), body);
        listObjects.add(listObjects.size(), object);
        object.setRect(body);
        object.attFix();
        body.setUserData(new UserData(object.getNameModel()));
        Filter filter = new Filter();
        filter.categoryBits = category;
        filter.maskBits = mask;
        for (int i = 0; i < object.getFixtureLastIndex(); i++){
            object.getFixture(i).setFilterData(filter);
        }
    }
    protected void createNewObj(WorldObject object, float sizex, float sizey, short category, short mask){
        Body body = world.createBody(object.getBody());
        lastBody = body;
        listBody.add(listBody.size(), body);
        listObjects.add(listObjects.size(), object);
        object.setRect(body);
        object.attFix(sizex);
        body.setUserData(new UserData(object.getNameModel()));
        Filter filter = new Filter();
        filter.categoryBits = category;
        filter.maskBits = mask;
        for (int i = 0; i < object.getFixtureLastIndex(); i++){
            object.getFixture(i).setFilterData(filter);
        }
    }
    protected void createNewWall(WorldObject object, float sizex, float sizey, short category, short mask){
        Body body = world.createBody(object.getBody());
        listBody.add(listBody.size(), body);
        listWalls.add(listWalls.size(), object);
        object.setRect(body);
        object.attFix(sizex);
        body.setUserData(new UserData(object.getNameModel()));
        Filter filter = new Filter();
        filter.categoryBits = category;
        filter.maskBits = mask;
        for (int i = 0; i < object.getFixtureLastIndex(); i++){
            object.getFixture(i).setFilterData(filter);
        }
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
        camera.position.set(heroBody.getWorldPoint(heroBody.getLocalCenter()),0);
        camera.update();
        updateHeroInput();
        updateGrad(this);
        world.step(1/fps,5,5);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
//        for (int i = 0; i < listObjects.size(); i++) {
//            listObjects.get(i).draw(batch);
//        }
//        for (int i = 0; i < listWalls.size(); i++) {
//            listWalls.get(i).draw(batch);
//        }
        listObjects.forEach(sprite -> sprite.draw(batch));
        listWalls.forEach(sprite -> sprite.draw(batch));
        hero.draw(batch);
//        for (int i = 0; i < upperAll.size(); i++) {
//            upperAll.get(i).draw(batch);
//        }
        upperAll.forEach(sprite -> sprite.draw(batch));
        batch.end();
        handler.update();
        handler.render();
        handler.setCombinedMatrix((OrthographicCamera) camera);
        renderer.render(world,camera.combined);
        stage.act(delta);
        stage.draw();
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
        if (keyboardUpdate) {
            if (heroVector.x == 0 & heroVector.y == 0) {

            } else {
                mouseGrad = atan2(heroVector.x, heroVector.y) * 180 / PI;
                mouseGrad = 360 - mouseGrad;
                gradneed = (float) Math.abs(mouseGrad) - (float) gradRect + 90;
                while (gradneed >= 359.99f) {
                    gradneed -= 360;
                }
                while (gradneed <= -359.99f) {
                    gradneed += 360;
                }
                if (gradneed == 0){
                    gradneed = 359;
                }
            }
            heroleftside = gradneed <= 180;
        } else if (mouseUpdate){
            gradneed = (float) mouseGrad - (float) gradRect;
            while (gradneed >= 359.99f){
                gradneed -= 360;
            }
            while (gradneed <= -359.99f){
                gradneed += 360;
            }
            heroleftside = gradneed <= 180;
        } else {
            if (heroleftside) {
                gradneed = 190;
            } else {
                gradneed = 350;
            }
        }
//        if (gradRect >= 0) {
//            while (gradRect >= 360) {
//                gradRect -= 360;
//            }
//        } else {
//            while (gradRect <= -360) {
//                gradRect += 360;
//            }
//            gradRect += 360;
//        }
//        if        (gradneed >= 0 & Math.abs(gradneed) < 180){
//            world.heroBody.setAngularVelocity(gradneed/5);
//        } else if (gradneed <= 0 & Math.abs(gradneed) < 180){
//            world.heroBody.setAngularVelocity(gradneed/5);
//        } else if (gradneed >= 0 & Math.abs(gradneed) >= 180){
//            if (gradneed < 0) {
//                world.heroBody.setAngularVelocity((-360 - gradneed)/5);
//            } else {
//                world.heroBody.setAngularVelocity((-360 + gradneed)/5);
//            }
//        } else if (gradneed <= 0 & Math.abs(gradneed) >= 180){
//            if (gradneed < 0) {
//                world.heroBody.setAngularVelocity(( 360 + gradneed)/5);
//            } else {
//                world.heroBody.setAngularVelocity(( 360 - gradneed)/5);
//            }
//        }
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
    public static float getCenterx(){
        return centerx;
    }
    public static float getCentery(){
        return centery;
    }
    public static Hero getHero(){
        return hero;
    }
    public Stage getStage(){
        return stage;
    }
    public static ArrayList<WorldObject> getListObjects(){
        return listObjects;
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
        handler.dispose();
    }

    public static float getGrad(){
        return gradneed;
    }

    public Body getLastBody() {
        return lastBody;
    }

    public static ArrayList<WorldObject> getListWalls(){
        return listWalls;
    }

    public static ArrayList<WorldObject> getUpperAll(){
        return upperAll;
    }
}

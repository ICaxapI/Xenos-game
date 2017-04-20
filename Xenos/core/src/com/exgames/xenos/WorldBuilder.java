package com.exgames.xenos;

import aurelienribon.bodyeditor.BodyEditorLoader;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;


/**
 * Created by Alex on 09.04.2017.
 */
public class WorldBuilder implements Screen {
    public static final float PIXINMET = 1f/44f;
    private Vector2 heroModelOrigin;
    private static double mouseGrad;
    private static boolean keyboardUpdate = false;
    private BodyEditorLoader loader;
    private Texture texhero;
    private Sprite hero;
    private Game game;
    private static double a;
    private static double b;
    private static double c;
    private static double grad;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Viewport viewport;
    protected Stage stage;
    private boolean press = false;
    private com.badlogic.gdx.physics.box2d.World world;
    private Box2DDebugRenderer renderer;
    private static Body rect;
    private InputController inputController;
    private static float centerx;
    private static float centery;
    private boolean mouseUpdate;
    public static final String FONT_CHARACTERS = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфЧчЦцЧчШшЩщЪъЫыЬХхьЭэЮюЯяabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
    private Vector2 heroVector;


    private float gradneed;

    public WorldBuilder(Game game, SpriteBatch batch, Viewport viewport){
        this.game = game;
        this.camera = new OrthographicCamera(16,9);;
        camera.position.set(new Vector3(camera.viewportWidth/2f,camera.viewportHeight/2f,0));
        this.batch = batch;
        this.viewport = viewport;
    }

    @Override
    public void show() {
        heroVector = new Vector2(0,0);
        stage = new Stage(viewport);
        world = new com.badlogic.gdx.physics.box2d.World(new Vector2(0,0),true);
        renderer = new Box2DDebugRenderer();
        createRect();
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
        hero = new Sprite(texhero);
        hero.setSize(0.63f, 0.86f);
    }
    private void createRect(){
        loader = new BodyEditorLoader(Gdx.files.internal("resources/maps/NewWorld.json"));
        BodyDef body = new BodyDef();
        body.type = BodyDef.BodyType.DynamicBody;
        body.position.set(camera.viewportWidth/2f,camera.viewportHeight/2f);
        body.linearDamping = 1.0f;
        body.angularDamping = 4.0f;
        rect = world.createBody(body);
        FixtureDef fdef = new FixtureDef();
        fdef.restitution = 0.0f;
        fdef.friction = 2.0f;
        fdef.density = 100;
        loader.attachFixture(rect,"Hero",fdef,1f);
        heroModelOrigin = loader.getOrigin("Hero", PIXINMET).cpy();
    }

    public Camera getCamera(){
        return this.camera;
    }
    public World getWorld(){
        return this.world;
    }
    @Override
    public void render(float delta) {
        Vector2 heroPos = rect.getPosition().sub(heroModelOrigin);
        hero.setPosition(heroPos.x, heroPos.y);
        hero.setOrigin(heroModelOrigin.x, heroModelOrigin.y);
        hero.setRotation(rect.getAngle() * MathUtils.radiansToDegrees);
        updateGrad(this);
        float fps = Gdx.graphics.getFramesPerSecond();
        if (fps < 1){
            fps = 60;
        }
        Gdx.gl.glClearColor(0f,0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.position.set(rect.getWorldPoint(rect.getLocalCenter()),0);
        camera.update();
        renderer.render(world,camera.combined);
        world.step(1/fps,5,5);
        batch.setProjectionMatrix(camera.combined);
        stage.act(delta);
        stage.draw();
        batch.begin();
        hero.draw(batch);
        batch.end();
        updateHeroInput();
    }
    public void setHeroVector(Vector2 updateVector){
        heroVector = updateVector;
    }
    public Vector2 getHeroVector(){
        return heroVector;
    }
    public void updateHeroInput(){
        getRect().setLinearVelocity(heroVector);
    }

    public void updateGrad(WorldBuilder world){
        double gradRect = Math.toDegrees(world.rect.getAngle());
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
            world.rect.setAngularVelocity(gradneed/5);
        } else if (gradneed <= 0 & Math.abs(gradneed) < 180){
            world.rect.setAngularVelocity(gradneed/5);
        } else if (gradneed >= 0 & Math.abs(gradneed) >= 180){
            if (gradneed < 0) {
                world.rect.setAngularVelocity((-360 - gradneed)/5);
            } else {
                world.rect.setAngularVelocity((-360 + gradneed)/5);
            }
        } else if (gradneed <= 0 & Math.abs(gradneed) >= 180){
            if (gradneed < 0) {
                world.rect.setAngularVelocity(( 360 + gradneed)/5);
            } else {
                world.rect.setAngularVelocity(( 360 - gradneed)/5);
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
    public Body getRect(){
        return rect;
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

package com.exgames.xenos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Alex on 09.04.2017.
 */
public class newWorld implements Screen {
    private Game game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Viewport viewport;
    private Stage stage;
    private World world;
    private Box2DDebugRenderer renderer;
    private Body rect;


    public newWorld(Game game, OrthographicCamera camera, SpriteBatch batch, Viewport viewport){
        this.game = game;
        this.camera = camera;
        this.batch = batch;
        this.viewport = viewport;
    }


    @Override
    public void show() {
//        stage = new Stage(viewport);
        world = new World(new Vector2(0,-10),true);
        renderer = new Box2DDebugRenderer();
        createRect();
        createWall();
        renderer.setDrawBodies(true);
        renderer.setDrawContacts(true);
        renderer.setDrawInactiveBodies(true);
        renderer.setDrawJoints(true);
        renderer.setDrawVelocities(true);
    }

    private void createRect(){
        BodyDef body = new BodyDef();
        body.type = BodyDef.BodyType.DynamicBody;
        body.position.set(620,360);
        body.linearDamping = 10.0f;
        body.angularDamping = 10.0f;

        rect = world.createBody(body);

        FixtureDef fdef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10,50);

        fdef.shape = shape;
        fdef.density = 1;
        rect.createFixture(fdef);
    }
    private void createWall(){
        BodyDef wall = new BodyDef();
        wall.type = BodyDef.BodyType.StaticBody;
        wall.position.set(10,10);

        Body w = world.createBody(wall);

        FixtureDef fdef = new FixtureDef();

        ChainShape shape = new ChainShape();
        shape.createChain(new Vector2[]{new Vector2(0,500), new Vector2(0,0), new Vector2(500,0), new Vector2(500,500)});

        fdef.shape = shape;
        w.createFixture(fdef);
    }

    @Override
    public void render(float delta) {
        float fps = Gdx.graphics.getFramesPerSecond();
        if (fps < 1 | fps > 144){
            System.out.println(fps);
            fps = 60;
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.render(world,camera.combined);
        world.step(1/fps,5,5);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            rect.applyForceToCenter(new Vector2(-900000000,0),true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            rect.applyForceToCenter(new Vector2(0,900000000),true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            rect.applyForceToCenter(new Vector2(900000000,0),true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            rect.applyForceToCenter(new Vector2(0,-900000000),true);
        }
//        batch.setProjectionMatrix(camera.combined);
//        stage.act(delta);
//        stage.draw();
//        batch.begin();
//        batch.end();
    }

    @Override
    public void resize(int width, int height) {

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

package com.exgames.xenos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
    private boolean press = false;
    private World world;
    private Box2DDebugRenderer renderer;
    private Body rect;


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
        world = new World(new Vector2(0,-10f),true);
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
        body.position.set(camera.viewportWidth/2f,camera.viewportHeight/2f);
        body.linearDamping = 1.0f;
        body.angularDamping = 1.0f;
        body.position.set(1f,7f);
        rect = world.createBody(body);

        FixtureDef fdef = new FixtureDef();
        fdef.restitution = 0.0f;
        fdef.friction = 2.0f;
        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f);
        fdef.shape = shape;
        fdef.density = 10;
        rect.createFixture(fdef);
    }
    private void createWall(){
        BodyDef wall = new BodyDef();
        wall.type = BodyDef.BodyType.StaticBody;
        wall.position.set(0.5f,0.5f);

        Body w = world.createBody(wall);

        FixtureDef fdef = new FixtureDef();
        fdef.friction = 2f;
        ChainShape shape = new ChainShape();
        shape.createChain(new Vector2[]{new Vector2(0f,8.0f),new Vector2(2f,8.0f),new Vector2(2f,7.03f), new Vector2(10f,7.03f),new Vector2(15f,6.999f), new Vector2(15f,6f), new Vector2(0f,6f),new Vector2(0f,7.0f)});

        fdef.shape = shape;
        w.createFixture(fdef);
    }

    @Override
    public void render(float delta) {
        float fps = Gdx.graphics.getFramesPerSecond();
        if (fps < 1){
            fps = 60;
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.render(world,camera.combined);
        world.step(1/fps,5,5);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            rect.applyTorque(200, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            rect.applyForceToCenter(new Vector2(0,500),true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            rect.applyTorque(-200, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            rect.applyForceToCenter(new Vector2(0,-500),true);
        }
        batch.setProjectionMatrix(camera.combined);
        stage.act(delta);
        stage.draw();
        batch.begin();
        batch.end();
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

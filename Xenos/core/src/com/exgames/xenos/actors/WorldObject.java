package com.exgames.xenos.actors;

import aurelienribon.bodyeditor.BodyEditorLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.exgames.xenos.WorldBuilder;

import static com.exgames.xenos.Main.camera;

/**
 * Created by Alex on 18.04.2017.
 */
public class WorldObject extends Actor{
    InputListener listener;
    private BodyEditorLoader loader;
    private Body rect;
    private BodyDef body;
    private Vector2 modelOrigin;
    protected Sprite mySprite;
    private String nameModel;
    private FixtureDef fdef;
    private int anglex;
    private int angley;

    private WorldObject(String world, String nameModel, BodyDef.BodyType bodyType, int density, float linearDamping,
                       float angularDamping, float restitution, float friction){
        this.nameModel = nameModel;
        loader = new BodyEditorLoader(Gdx.files.internal("resources/maps/" + world + ".json"));
        body = new BodyDef();
        body.type = bodyType;
        body.linearDamping = linearDamping;
        body.angularDamping = angularDamping;
        fdef = new FixtureDef();
        fdef.restitution = restitution;
        fdef.friction = friction;
        fdef.density = density;
        modelOrigin = loader.getOrigin(nameModel, WorldBuilder.PIXINMET).cpy();
    }
    public WorldObject(Texture texture, String world, String nameModel, BodyDef.BodyType bodyType, int density,
                       float sizeX, float sizeY, float linearDamping, float angularDamping, float restitution, float friction,float x, float y){
        this(texture, world, nameModel, bodyType, density, sizeX, sizeY, linearDamping, angularDamping, restitution, friction);
        getBody().position.set(x , y);
    }
    public WorldObject(String world, String nameModel, BodyDef.BodyType bodyType, int density, float linearDamping,
                       float angularDamping, float restitution, float friction, float x, float y){
        this(world, nameModel, bodyType, density, linearDamping, angularDamping, restitution, friction);
        getBody().position.set(x , y);
    }
    public WorldObject(Texture texture, String world, String nameModel, BodyDef.BodyType bodyType, int density,
                       float sizeX, float sizeY, float linearDamping, float angularDamping, float restitution, float friction){
        this(world, nameModel, bodyType, density, linearDamping, angularDamping, restitution, friction);
        mySprite = new Sprite(texture);
        mySprite.setSize(sizeX, sizeY);
        getBody().position.set(camera.viewportWidth/2f,camera.viewportHeight/2f);
    }

    public WorldObject(float radius, float x, float y){
        body = new BodyDef();
        body.type = BodyDef.BodyType.StaticBody;
        fdef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);
        fdef.shape = circleShape;
        body.position.set(x, y);
    }


    public void addInputListener(String name){
        listener = new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("touchUp");
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                System.out.println("touchDragged");
                super.touchDragged(event, x, y, pointer);
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                System.out.println("scrolled");
                return false;
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                System.out.println("keyDown");
                return false;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                System.out.println("keyUp");
                return false;
            }

            @Override
            public boolean keyTyped(InputEvent event, char character) {
                System.out.println("KeyTyped");
                return false;
            }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                System.out.println("mouseMoved");
                return false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("touchDown" + mySprite.getRegionWidth() + mySprite.getRegionHeight());
                return false;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                System.out.println("enter");
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                System.out.println("exit");
                super.exit(event, x, y, pointer, toActor);
            }
        };
        addListener(listener);
    }
    public void attFix(){
        loader.attachFixture(rect,nameModel,fdef,1f);
    }
    public void attFix(float scale){
        loader.attachFixture(rect,nameModel,fdef,scale);
    }
    public void attFix(Body body){
        body.createFixture(fdef);
    }

    public FixtureDef getFdef() {
        return fdef;
    }

    public Fixture getFixture() {
        return getRect().getFixtureList().first();
    }

    public Fixture getFixture(int index) {
        return getRect().getFixtureList().get(index);
    }
    public int getFixtureLastIndex() {
        return getRect().getFixtureList().size;
    }
    public void act(float delta){
        mySprite.setBounds(mySprite.getX(), mySprite.getY(),  mySprite.getWidth(), mySprite.getHeight());
        super.act(delta);
    }
    public void draw(Batch batch, float alpha){
        if (mySprite != null) {
            Vector2 pos = rect.getPosition().sub(modelOrigin);
            mySprite.setPosition(pos.x, pos.y);
            mySprite.setOrigin(modelOrigin.x, modelOrigin.y);
            mySprite.setRotation(rect.getAngle() * MathUtils.radiansToDegrees);
            mySprite.setBounds(mySprite.getX(), mySprite.getY(), mySprite.getWidth(), mySprite.getHeight());
            setPosition(mySprite.getX()-anglex, mySprite.getY()-angley);
            setSize(mySprite.getRegionWidth(), mySprite.getRegionHeight());
            mySprite.draw(batch, alpha);
        }
        //System.out.println(rect.getUserData());
    }
    public void draw(Batch batch){
        draw(batch, 1f);
    }

    public BodyDef getBody(){
        return body;
    }
    public String getNameModel(){
        return nameModel;
    }
    public Vector2 getModelOrigin(){
        return modelOrigin;
    }
    public Body getRect(){
        return rect;
    }

    public void setRect(Body rect){
        this.rect = rect;
    }

    public int getAnglex() {
        return anglex;
    }

    public void setAnglex(int anglex) {
        this.anglex = anglex;
    }

    public int getAngley() {
        return angley;
    }

    public void setAngley(int angley) {
        this.angley = angley;
    }
}

package com.exgames.xenos.actors;

import aurelienribon.bodyeditor.BodyEditorLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.exgames.xenos.WorldBuilder;

import static com.exgames.xenos.Main.camera;

/**
 * Created by Alex on 18.04.2017.
 */
public class WorldObject {
    private BodyEditorLoader loader;
    private Body rect;
    private BodyDef body;
    private Vector2 modelOrigin;
    protected Sprite mySprite;
    private String nameModel;
    private FixtureDef fdef;

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


    public void attFix(){
        loader.attachFixture(rect,nameModel,fdef,1f);
    }
    public void attFix(Body body){
        body.createFixture(fdef);
    }


    public void draw(Batch batch, float alpha){
        if (mySprite != null) {
            mySprite.draw(batch, alpha);
            Vector2 pos = rect.getPosition().sub(modelOrigin);
            mySprite.setPosition(pos.x, pos.y);
            mySprite.setOrigin(modelOrigin.x, modelOrigin.y);
            mySprite.setRotation(rect.getAngle() * MathUtils.radiansToDegrees);
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
}

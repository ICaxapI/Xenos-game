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
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.exgames.xenos.WorldBuilder;

import static com.exgames.xenos.Main.camera;

/**
 * Created by Alex on 18.04.2017.
 */
public class Hero {
    private BodyEditorLoader loader;
    private Body rect;
    private BodyDef body;
    private Vector2 modelOrigin;
    private Sprite mySprite;
    private String nameModel;
    private FixtureDef fdef;

    public Hero(Texture texture, String world, String nameModel, BodyDef.BodyType bodyType, int density, float sizeX, float sizeY){
        this.nameModel = nameModel;
        loader = new BodyEditorLoader(Gdx.files.internal("resources/maps/" + world + ".json"));
        body = new BodyDef();
        body.type = bodyType;
        body.position.set(camera.viewportWidth/2f,camera.viewportHeight/2f);
        body.linearDamping = 1.0f;
        body.angularDamping = 4.0f;
        fdef = new FixtureDef();
        fdef.restitution = 0.0f;
        fdef.friction = 2.0f;
        fdef.density = density;
        modelOrigin = loader.getOrigin(nameModel, WorldBuilder.PIXINMET).cpy();
        mySprite = new Sprite(texture);
        mySprite.setSize(sizeX, sizeY);
    }
    public void attFix(){
        loader.attachFixture(rect,nameModel,fdef,1f);
    }


    public void draw(Batch batch, float alpha){
        mySprite.draw(batch, alpha);
        Vector2 pos = rect.getPosition().sub(modelOrigin);
        mySprite.setPosition(pos.x, pos.y);
        mySprite.setOrigin(modelOrigin.x, modelOrigin.y);
        mySprite.setRotation(rect.getAngle() * MathUtils.radiansToDegrees);
    }
    public void draw(Batch batch){
        draw(batch, 1f);
    }

    public BodyDef getBody(){
        return body;
    }
    public Vector2 getModelOrigin(){
        return modelOrigin;
    }
    public void setRect(Body rect){
        this.rect = rect;
    }
}

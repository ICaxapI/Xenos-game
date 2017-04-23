package com.exgames.xenos.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;

/**
 * Created by Alex on 23.04.2017.
 */
public class Door extends  WorldObject {
    public Door(Texture texture, String world, String nameModel, BodyDef.BodyType bodyType, int density, float sizeX, float sizeY, float linearDamping, float angularDamping, float restitution, float friction, float x, float y) {
        super(texture, world, nameModel, bodyType, density, sizeX, sizeY, linearDamping, angularDamping, restitution, friction, x, y);
    }
    public Door(String world, String nameModel, BodyDef.BodyType bodyType, int density, float linearDamping, float angularDamping, float restitution, float friction, float x, float y) {
        super(world, nameModel, bodyType, density, linearDamping, angularDamping, restitution, friction, x, y);
    }

}

package com.exgames.xenos.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;

import static com.exgames.xenos.Main.camera;

public class Hero extends WorldObject {

    public Hero(Texture texture, String world, String nameModel, int density){
        super(texture, world, nameModel, BodyDef.BodyType.DynamicBody, density, 0.63f, 0.86f, 1f, 4f, 0f, 2f, 0 ,0);
    }

}

package com.exgames.xenos.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.exgames.xenos.Animator;

import static com.exgames.xenos.Main.camera;

public class Hero extends WorldObject {
    private Animator animator;

    public Hero(Texture textureSheet, String world, String nameModel, int density){
        super((Texture) null, world, nameModel, BodyDef.BodyType.DynamicBody, density, 0.875f,1.3f, 1f, 4f, 0f, 2f, 0 ,0);
        animator = new Animator(textureSheet);
        animator.updateFrames();
        mySprite = new Sprite(animator.needrender());
        mySprite.setSize(0.875f,1.3f);
    }

    public void draw(Batch batch){
        super.draw(batch);
    }

    public Animator getAnimator(){
        return animator;
    }

}

package com.exgames.xenos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Alex on 05.04.2017.
 */
public class Animator {
    private final int FRAME_COLS = 10;
    private final int FRAME_ROWS = 2;
    private int angle;
    private boolean stay = true;
    private boolean reflect = true;
    private float stayDuration = 2f;
    private float walkDuraton = 0.2f;

    private Animation<TextureRegion> animation;
    private Texture walkSheet;
    private TextureRegion[] walkFrames;
    private TextureRegion[] stayFrames;
    private TextureRegion currentFrame;
    private float stateTime = 0f;
    private TextureRegion[][] tmp;

    public Animator(Texture texture) {
        walkSheet = texture;
        tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);
        walkFrames = new TextureRegion[8];
        stayFrames = new TextureRegion[2];
        updateFrames();
    }


    public TextureRegion needrender() {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = (TextureRegion) animation.getKeyFrame(stateTime, true);
        return currentFrame;
    }

    public void updateFrames(){
        switch (angle){
            case 0:
                if(stay){
                    System.arraycopy(tmp[1], 0, stayFrames, 0, 2);
                    animation = new Animation<>(stayDuration, stayFrames);
                    reflect = true;
                } else {
                    System.arraycopy(tmp[1], 2, walkFrames, 0, 8);
                    animation = new Animation<>(walkDuraton, walkFrames);
                    reflect = true;
                }
                break;
            case 1:
                if(stay){
                    System.arraycopy(tmp[1], 0, stayFrames, 0, 2);
                    animation = new Animation<>(stayDuration, stayFrames);
                    reflect = false;
                } else {
                    System.arraycopy(tmp[1], 2, walkFrames, 0, 8);
                    animation = new Animation<>(walkDuraton, walkFrames);
                    reflect = false;
                    //walkFrames[j].flip(false, true);
                }
                break;
            case 2:
                if(stay){
                    System.arraycopy(tmp[0], 0, stayFrames, 0, 2);
                    animation = new Animation<>(stayDuration, stayFrames);
                    reflect = false;
                } else {
                    System.arraycopy(tmp[0], 2, walkFrames, 0, 8);
                    animation = new Animation<>(walkDuraton, walkFrames);
                    reflect = false;
                }
                break;
            case 3:
                if(stay){
                    System.arraycopy(tmp[0], 0, stayFrames, 0, 2);
                    animation = new Animation<>(stayDuration, stayFrames);
                    reflect = true;
                } else {
                    System.arraycopy(tmp[0], 2, walkFrames, 0, 8);
                    animation = new Animation<>(walkDuraton, walkFrames);
                    reflect = true;
                }
                break;
        }
    }

    public void setAngle(int angle) {
        if (angle != this.angle) {
            this.angle = angle;
            updateFrames();
        }
    }

    public void setStay(boolean stay) {
        this.stay = stay;
    }

    public void setReflect(boolean reflect) {
        this.reflect = reflect;
    }

    public boolean isReflect() {
        return reflect;
    }
}

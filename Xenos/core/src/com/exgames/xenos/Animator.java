package com.exgames.xenos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Alex on 05.04.2017.
 */
public class Animator {
    private static final int FRAME_COLS = 6; // 6
    private static final int FRAME_ROWS = 5; // 5

    private Animation<TextureRegion> walkAnimation; // #3
    private Texture walkSheet; // #4
    private TextureRegion[] walkFrames; // #5
    private TextureRegion currentFrame; // #7
    private float stateTime; // #8

    public Animator(String textureurl) {
        walkSheet = new Texture(Gdx.files.internal(textureurl)); // #9
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS); // #10
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation<>(0.025f, walkFrames); // #11
        stateTime = 0f; // #13
    }

    public TextureRegion needrender() {
        stateTime += Gdx.graphics.getDeltaTime(); // #15
        currentFrame = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true); // #16
        return currentFrame;
    }

}

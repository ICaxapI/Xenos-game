package com.exgames.xenos;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by Alex on 23.04.2017.
 */
public class MyContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {
        UserData fixA = (UserData) contact.getFixtureA().getBody().getUserData();
        UserData fixB = (UserData) contact.getFixtureB().getBody().getUserData();
        if (fixA.getName().equals("Detector")){
            fixA.getDoor().setHeroInside(false);
        }
        if (fixB.getName().equals("Detector")){
            fixB.getDoor().setHeroInside(false);
        }
        if (fixA.getName().equals("DetectorFloor")){
            if (fixA.getWall().isInv()) {
                WorldBuilder.getUpperAll().remove(fixA.getWall());
                WorldBuilder.getUpperAll().remove(fixA.getWall2());
                WorldBuilder.getListWalls().add(fixA.getWall2());
                WorldBuilder.getListWalls().add(fixA.getWall());
            }
            fixA.getWall().fadeIn();
        }
        if (fixB.getName().equals("DetectorFloor")){
            if (fixB.getWall().isInv()) {
                WorldBuilder.getUpperAll().remove(fixB.getWall());
                WorldBuilder.getUpperAll().remove(fixB.getWall2());
                WorldBuilder.getListWalls().add(fixB.getWall2());
                WorldBuilder.getListWalls().add(fixB.getWall());
            }
            fixB.getWall().fadeIn();
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        UserData fixA = (UserData) contact.getFixtureA().getBody().getUserData();
        UserData fixB = (UserData) contact.getFixtureB().getBody().getUserData();
        if (fixA.getName().equals("Detector")){
            contact.setEnabled(false);
            fixA.getDoor().swith();
            fixA.getDoor().setHeroInside(true);
        }
        if (fixB.getName().equals("Detector")){
            contact.setEnabled(false);
            fixB.getDoor().swith();
            fixB.getDoor().setHeroInside(true);
        }
        if (fixA.getName().equals("DetectorFloor")){
            contact.setEnabled(false);
            if (!fixA.getWall().isInv()) {
                WorldBuilder.getListWalls().remove(fixA.getWall());
                WorldBuilder.getListWalls().remove(fixA.getWall2());
                WorldBuilder.getUpperAll().add(fixA.getWall2());
                WorldBuilder.getUpperAll().add(fixA.getWall());
            }
            fixA.getWall().fadeOut();
        }
        if (fixB.getName().equals("DetectorFloor")){
            contact.setEnabled(false);
            if (!fixB.getWall().isInv()) {
                WorldBuilder.getListWalls().remove(fixB.getWall());
                WorldBuilder.getListWalls().remove(fixB.getWall2());
                WorldBuilder.getUpperAll().add(fixB.getWall2());
                WorldBuilder.getUpperAll().add(fixB.getWall());
            }
            fixB.getWall().fadeOut();
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

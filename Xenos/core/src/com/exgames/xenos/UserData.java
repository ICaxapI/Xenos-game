package com.exgames.xenos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.exgames.xenos.actors.Door;
import com.exgames.xenos.actors.WorldObject;

/**
 * Created by Alex on 23.04.2017.
 */
public class UserData {
    private String name;
    private Door door;
    private WorldObject wall;
    private WorldObject wall2;

    public UserData(String name){
        this.name = name;
    }

    public UserData(String name, Door door){
        this(name);
        this.door = door;
    }

    public UserData(String name, WorldObject wall, WorldObject wall2){
        this(name);
        this.wall = wall;
        this.wall2 = wall2;
    }

    public Door getDoor(){
        return door;
    }

    public WorldObject getWall(){
        return wall;
    }
    public WorldObject getWall2(){
        return wall2;
    }

    public String getName(){
        return name;
    }
}

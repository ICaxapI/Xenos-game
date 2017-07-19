package com.exgames.xenos.actors;

import aurelienribon.bodyeditor.BodyEditorLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.exgames.xenos.JsonUtils;
import com.exgames.xenos.Main;
import com.exgames.xenos.WorldBuilder;
import com.exgames.xenos.maps.NewGame;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

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
    private JSONObject myObject;
    private int iter = 0;
    private Texture cloudTex;
    private BitmapFont font;
    private String soundPatch = "resources/music/peek.wav";
    private String stateDialog = "v1";//сохранять и применять сюда из файла сохранения
    private Cloud cloudActor;
    private boolean movedCloud = true;
    private Dialog dialogCloud;

    private short interuptedNumb;
    private String myName;
    private static WorldObject calldObject;
    private static boolean canInterupted;

    private Texture staticatlas;
    private static String[] staticansvers;
    private static String staticansversID[];
    private static String[] staticexit;
    private static String[] staticexitID;
    private static BitmapFont staticfont;
    private static Stage staticstage;
    private static float staticscale;
    private static WorldObject staticmyObject;



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
    public WorldObject(String world, String nameModel, BodyDef.BodyType bodyType, int density, float linearDamping,
                       float angularDamping, float restitution, float friction, float x, float y){
        this(world, nameModel, bodyType, density, linearDamping, angularDamping, restitution, friction);
        getBody().position.set(x , y);
    }
    public WorldObject(Texture texture, String world, String nameModel, BodyDef.BodyType bodyType, int density,
                       float sizeX, float sizeY, float linearDamping, float angularDamping, float restitution, float friction, float x, float y){
        this(world, nameModel, bodyType, density, linearDamping, angularDamping, restitution, friction);
        mySprite = new Sprite(texture);
        mySprite.setSize(sizeX, sizeY);
        body.fixedRotation = true;
        getBody().position.set(x, y);
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

    public static WorldObject getCalldObject() {
        return calldObject;
    }

    public static boolean isCanInterupted() {
        return canInterupted;
    }

    public static void setCanInterupted(boolean canInterupted) {
        WorldObject.canInterupted = canInterupted;
    }

    private Vector2 getCordsScreen(){
        Vector2 heroWorld = WorldBuilder.getHero().getRect().getWorldCenter();
        Vector2 objWorld = getRect().getPosition().sub(getModelOrigin());
        float alignx = (objWorld.x - heroWorld.x)*(getStage().getWidth()/16f);
        float aligny = (objWorld.y - heroWorld.y)*(getStage().getHeight()/9f);
        Vector2 objStage = new Vector2(getStage().getWidth()/2f + alignx,  getStage().getHeight()/2f + aligny);
        return objStage;
    }

    public void removeCloud(){
        if(cloudActor != null) {
            cloudActor.dispose();
            cloudActor = null;
        }
    }

    public void removeDialog(){
        if(dialogCloud != null) {
            dialogCloud.dispose();
            dialogCloud = null;
        }
    }

    public void loadReplic(){
        if (myObject.get("type").equals("monolog")){
            movedCloud = false;
            if (cloudActor != null){
                removeCloud();
            }
            iter++;
            JSONArray replicsArr = (JSONArray) myObject.get("replics");
            JSONObject replics = (JSONObject) replicsArr.get(0);
            if(!replics.containsKey("action" + iter)) {
                //Vector2 pos = WorldBuilder.getHero().getRect().getPosition().sub(WorldBuilder.getHero().getModelOrigin());
                if(replics.containsKey(String.valueOf(iter))) {
                    callCloud(cloudTex, (getStage().getWidth()/2f), 280, replics.get(String.valueOf(iter)).toString(), font, getStage(), 2, 20, soundPatch, this, false);
                } else {
                    iter --;
                    callCloud(cloudTex, (getStage().getWidth()/2f), 280, replics.get(String.valueOf(iter)).toString(), font, getStage(), 2, 20, soundPatch, this, false);
                }
            } else {
                JSONObject action = (JSONObject) replics.get("action" + iter);
                if (action.get("action").equals("playmusic")){
                    playMusic(action);
                } else if (action.get("action").equals("changestate")){
                    stateDialog = action.get("actionInfo").toString();
                }
                if (action.containsKey("replic")){
                    callCloud(cloudTex, (getStage().getWidth()/2f), (getStage().getHeight()/2f), action.get("replic").toString(), font, getStage(), 2, 20, soundPatch, this, false);
                }
            }
        } else if (myObject.get("type").equals("dialog")){
            JSONArray replicsArr = (JSONArray) myObject.get("replics");
            JSONObject replics = (JSONObject) replicsArr.get(0);
            JSONArray dialogArr = (JSONArray) myObject.get("dialog");
            JSONObject dialog = (JSONObject) dialogArr.get(0);
            if (dialog.containsKey(stateDialog)){
                JSONObject nowDialog = (JSONObject) dialog.get(stateDialog);
                if (nowDialog.containsKey("start") & interuptedNumb <= 1){
                    if (cloudActor == null) {
                        if(!replics.containsKey("action" + nowDialog.get("start"))) {
                            System.out.println(interuptedNumb);
                            callCloud(cloudTex, getX(), getY(), replics.get(nowDialog.get("start")).toString(), font, getStage(), 2, 60, soundPatch, this, false);
                        } else {
                            JSONObject action = (JSONObject) replics.get("action" + nowDialog.get("start"));
                            if (action.get("action").equals("playmusic")){
                                playMusic(action);
                            } else if (action.get("action").equals("changestate")){
                                stateDialog = action.get("actionInfo").toString();
                            }
                            if (action.containsKey("replic")){
                                callCloud(cloudTex, getX(), getY(), action.get("replic").toString(), font, getStage(), 2, 60, soundPatch, this, false);
                            }
                        }
                    }
                } else {
                    System.out.println("Исключение: Нет ключа start!");
                }
                if (nowDialog.containsKey("interupted" + interuptedNumb + "A")){
                    if (cloudActor != null){
                        cloudActor.setInterupted(true);
                    }
                    if (calldObject == null) {
                        calldObject = this;
                    }
                    if (canInterupted) {
                        canInterupted = false;
                        String needName = nowDialog.get("interupted" + interuptedNumb + "A").toString();
                        String interuptedString = replics.get(nowDialog.get("interupted" + interuptedNumb + "R").toString()).toString();
                        ArrayList<WorldObject> arrayWorldObj = WorldBuilder.getListObjects(); //Можно оптимизировать. Сделать ещё один аррайлист в котором хранить только обьекты с Dialogs.
                        for (int i = 0; i < arrayWorldObj.size() - 1; i++) {
                            if (arrayWorldObj.get(i).myName.equals(needName)) {
                                arrayWorldObj.get(i).callCloud(arrayWorldObj.get(i).cloudTex, arrayWorldObj.get(i).getX(),
                                        arrayWorldObj.get(i).getY(), interuptedString, arrayWorldObj.get(i).font, arrayWorldObj.get(i).getStage(), 2, 60, arrayWorldObj.get(i).soundPatch, arrayWorldObj.get(i), false);
                                arrayWorldObj.get(i).cloudActor.setInterupted(true);
                                i = arrayWorldObj.size() - 1;
                            }
                            System.out.println("Прочекали " + arrayWorldObj.get(i).myName);
                        }
                        interuptedNumb++;
                    }
                } else if (nowDialog.containsKey("ansvers") || nowDialog.containsKey("exit")){
                    calldObject = null;
                    interuptedNumb = 1;
                    String[] ansvers;
                    String[] ansversID;
                    String[] exit;
                    String[] exitID;
                    if (nowDialog.containsKey("ansvers")) {
                        JSONArray ansverArr = (JSONArray) nowDialog.get("ansvers");
                        ansvers = new String[ansverArr.size()];
                        ansversID = new String[ansverArr.size()];
                        for (int i = 0; i < ansvers.length; i++) {
                            ansvers[i] = replics.get(ansverArr.get(i)).toString();
                            ansversID[i] = ansverArr.get(i).toString();
                        }
                    } else {
                        ansvers = null;
                        ansversID = null;
                    }
                    if (nowDialog.containsKey("exit")){
                        JSONArray exitArr = (JSONArray) nowDialog.get("exit");
                        exit = new String[exitArr.size()];
                        exitID = new String[exitArr.size()];
                        for (int i = 0; i < exit.length; i++) {
                            exit[i] = replics.get(exitArr.get(i)).toString();
                            exitID[i] = exitArr.get(i).toString();
                        }
                    } else {
                        exit = null;
                        exitID = null;
                    }
                    deferCallDialog(cloudTex, ansvers, ansversID, exit, exitID, font, getStage(), 2, this);
                } else {
                    System.out.println("Исключение: Нет ключа ansvers!");
                }
            }
        }
    }

    private void callDialog(Texture atlas, String[] ansvers, String ansversID[], String[] exit, String[] exitID, BitmapFont font, Stage stage, float scale, WorldObject myObject){
        dialogCloud = new Dialog(atlas, ansvers, ansversID, exit, exitID, font, stage, scale,  myObject);
        getStage().addActor(dialogCloud);
    }
    public void callDialog(){
        if (staticatlas != null) {
            dialogCloud = new Dialog(staticatlas, staticansvers, staticansversID, staticexit, staticexitID, staticfont, staticstage, staticscale, staticmyObject);
            getStage().addActor(dialogCloud);
            staticatlas = null;
            staticansvers = null;
            staticansversID = null;
            staticexit = null;
            staticexitID = null;
            staticfont = null;
            staticstage = null;
            staticscale = 0;
            staticmyObject = null;
        }
    }
    public void deferCallDialog(Texture atlas, String[] ansvers, String ansversID[], String[] exit, String[] exitID, BitmapFont font, Stage stage, float scale, WorldObject myObject){
        staticatlas = atlas;
        staticansvers = ansvers;
        staticansversID = ansversID;
        staticexit = exit;
        staticexitID = exitID;
        staticfont = font;
        staticstage = stage;
        staticscale = scale;
        staticmyObject = myObject;
    }


    public void playMusic(JSONObject action){
        WorldBuilder.music.pause();
        Music actionmusic;
        actionmusic = Gdx.audio.newMusic(Gdx.files.internal("resources/music/" + action.get("actionInfo")));
        actionmusic.setVolume(Main.volumeMusic);
        if (!actionmusic.isPlaying()){
            actionmusic.play();
        }
        Music.OnCompletionListener listenerMusic = music -> {
            WorldBuilder.music.play();
            if (actionmusic.isPlaying()){
                actionmusic.stop();
            }
            actionmusic.dispose();
        };
        actionmusic.setOnCompletionListener(listenerMusic);
    }

    public void changeStateDialog (String id){
        JSONArray replicsArr = (JSONArray) myObject.get("replics");
        JSONObject replics = (JSONObject) replicsArr.get(0);
        JSONArray dialogArr = (JSONArray) myObject.get("dialog");
        JSONObject dialog = (JSONObject) dialogArr.get(0);
        if (dialog.containsKey(stateDialog)){
            JSONObject nowDialog = (JSONObject) dialog.get(stateDialog);
            if (nowDialog.containsKey("selectansver"+id)){
                stateDialog = nowDialog.get("selectansver"+id).toString();
                removeDialog();
                loadReplic();
            } else {
                System.out.println("Исключение: Нет ключа selectansver" + id + " !");
                System.out.println(stateDialog);
            }
        }
    }

    public void callCloud(Texture atlas, float x, float y, String string, BitmapFont font, Stage stage, float scale,int period, String soundPatch, WorldObject worldObject, boolean interupted){
        cloudActor = new Cloud(atlas, x, y, string, font, stage, scale, period, soundPatch, worldObject, interupted);
    }

    private void addInputListener(String name, Stage stage){
        cloudTex = new Texture(Gdx.files.internal("resources/entities/cloud.png"));
        try {
            myObject  = JsonUtils.parseObj(name);
            myName = name;
        } catch (NullPointerException ex){
            ex.printStackTrace();
        }
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
                System.out.println("touchDown " + nameModel);
                removeCloud();
                loadReplic();
                return false;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                System.out.println("enter " + nameModel);
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                System.out.println("exit " + nameModel);
                super.exit(event, x, y, pointer, toActor);
            }
        };
        addListener(listener);
        stage.addActor(this);
    }

    public void addInputListener(String name, String patchFont, int size, float scale, Stage stage) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(patchFont));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.characters = WorldBuilder.FONT_CHARACTERS;
        parameter.renderCount = 1;
        font = generator.generateFont(parameter);
        font.getData().scale(scale);
        generator.dispose();
        addInputListener(name, stage);
    }

    public void addInputListener(String name, BitmapFont font, Stage stage){
        this.font = font;
        addInputListener(name, stage);
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
            if (getStage() != null) {
                Vector2 stageCords = getCordsScreen();
                setPosition(stageCords.x, stageCords.y);
                setSize(mySprite.getWidth() * getStage().getWidth()/16f, mySprite.getHeight() * getStage().getHeight()/9f);
            }
            mySprite.setPosition(pos.x, pos.y);
            mySprite.setOrigin(modelOrigin.x, modelOrigin.y);
            mySprite.setRotation(rect.getAngle() * MathUtils.radiansToDegrees);
            mySprite.setBounds(mySprite.getX(), mySprite.getY(), mySprite.getWidth(), mySprite.getHeight());
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

    public void setCloud(Texture cloud) {
        this.cloudTex = cloud;
    }

    public void setSoundPatch(String soundPatch) {
        this.soundPatch = soundPatch;
    }

    public boolean isMovedCloud() {
        return movedCloud;
    }

    public String getMyName() {
        return myName;
    }
}

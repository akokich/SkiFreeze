package com.akx2.skifreeze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameAssets {
    public static Texture countdown;
    public static Texture flake0;
    public static Texture flake1;
    public static Texture flake2;
    public static Texture go;
    public static Texture logo;
    public static Texture player;
    public static Texture scoreButton;
    public static Texture startButton;
    public static Texture tree0;
    public static Texture leaves;
    public static Texture jump;
    public static Texture playerShadow;
    public static Texture powder;
    public static Texture sky;
    public static Texture signSlalom;

    public static Texture flags;
    public static TextureRegion flag_LEFT;
    public static TextureRegion flag_RIGHT;
    public static TextureRegion flag_LEFT_FAIL;
    public static TextureRegion flag_RIGHT_FAIL;
    public static TextureRegion flag_LEFT_OK;
    public static TextureRegion flag_RIGHT_OK;
    public static TextureRegion flag_RIGHT_CONTROL;
    public static TextureRegion flag_LEFT_CONTROL;
    public static Texture startLine;
    public static Texture finishLine;

    public static Texture scoreBackground;

    public static BitmapFont font_scoreboard;

    public static void loadAssets ()
    {
        countdown = new Texture("count.png");
        flake0 = new Texture("flake0.png");
        flake1 = new Texture("flake1.png");
        flake2 = new Texture("flake2.png");
        go = new Texture("go.png");
        logo = new Texture("logo.png");
        player = new Texture("player.png");
        scoreButton = new Texture("score.png");
        startButton = new Texture("start.png");
        tree0 = new Texture("tree0.png");
        leaves = new Texture ("leaves.png");
        jump = new Texture("jump.png");
        playerShadow = new Texture ("player_shadow.png");
        powder = new Texture ("powder.png");
        sky = new Texture("sky.png");
        signSlalom = new Texture ("signSlalom.png");

        flags = new Texture("flags.png");
        flag_LEFT = new TextureRegion(GameAssets.flags, 0, 0, 40, 102);
        flag_RIGHT = new TextureRegion(GameAssets.flags, 40, 0, 40, 102);
        flag_LEFT_FAIL = new TextureRegion(GameAssets.flags, 80, 0, 40, 102);
        flag_RIGHT_FAIL = new TextureRegion(GameAssets.flags, 120, 0, 40, 102);
        flag_LEFT_OK = new TextureRegion(GameAssets.flags, 160, 0, 40, 102);
        flag_RIGHT_OK = new TextureRegion(GameAssets.flags, 200, 0, 40, 102);
        flag_LEFT_CONTROL = new TextureRegion(GameAssets.flags, 240, 0, 40, 102);
        flag_RIGHT_CONTROL = new TextureRegion(GameAssets.flags, 280, 0, 40, 102);

        startLine = new Texture("startline.png");
        finishLine = new Texture("finishline.png");

        scoreBackground = new Texture ("scorebackground.png");

        font_scoreboard = new BitmapFont(Gdx.files.internal("scoretext.fnt"), false);
    }
}

package com.akx2.skifreeze;

import com.akx2.x2.GameMain;
import com.akx2.x2.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Timer;
import java.util.TimerTask;

public class PlayScreen extends GameScreen {
    public final int MAX_TREES = 10;            // MAX TREES TO BE USED
    public final int MAX_JUMPS = 2;            // MAX JUMPS TO BE USED

    public final int SLALOM_RESPAWN = 30000;

    public final float LANDING_GRACE_DURATION = 0.75f;

    Countdown countdown;
    Player player;
    Tricks tricks;

    Scoreboard scoreboard;

    StartArea startArea;

    SlopeTree[] trees;
    SlopeJump[] jumps;

    PowderManager powder;
    LeafManager leaves;

    Slalom slalom;

    int idx = 0;

    Rectangle leftButton;
    Rectangle rightButton;
    Rectangle downButton;

    int buttonWidth = 300;
    int buttonHeight = 300;

    float landingTimer = 0;
    boolean isTimingLanding = false;

    Timer slalomTimer;

    Vector2 translatedPick = new Vector2(0, 0);

    public PlayScreen(GameMain main) {
        super(main);

        leftButton = new Rectangle(0, 0, buttonWidth, buttonHeight);
        rightButton = new Rectangle(1080-buttonWidth, 0, buttonWidth, buttonHeight);
        downButton = new Rectangle(buttonWidth, 0, 1080-(buttonWidth*2), buttonWidth);

        countdown = new Countdown();
        player = new Player();
        tricks = new Tricks();

        scoreboard = new Scoreboard();

        startArea = new StartArea();

        powder = new PowderManager();
        leaves = new LeafManager();

        trees = new SlopeTree[MAX_TREES];
        for(idx=0; idx<MAX_TREES; idx++) {
            if (idx < MAX_TREES/3) {
                trees[idx] = new SlopeTree(true);
            } else {
                trees[idx] = new SlopeTree(false);
            }
        }

        jumps = new SlopeJump[MAX_JUMPS];
        for(idx=0; idx<MAX_JUMPS; idx++) {
            if (idx < MAX_JUMPS/3) {
                jumps[idx] = new SlopeJump(true);
            } else {
                jumps[idx] = new SlopeJump(false);
            }
        }

        slalom = new Slalom();
        slalom.spawn(true);

        slalomTimer = new Timer();
        slalomTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!slalom.isActive) {
                    slalom.spawn(false);
                }
            }
        }, SLALOM_RESPAWN, SLALOM_RESPAWN);

        setMovement(player.pose);
    }

    public void setMovement(PlayerPose pose)
    {
        for (idx=0; idx<trees.length; idx++) {
            trees[idx].setMovement(pose.getShift(), pose.getSpeed());
        }

        for (idx=0; idx<jumps.length; idx++) {
            jumps[idx].setMovement(pose.getShift(), pose.getSpeed());
        }

        powder.setMovement(pose.getShift(), pose.getSpeed());
        leaves.setMovement(pose.getShift(), pose.getSpeed());

        startArea.setMovement(pose.getShift(), pose.getSpeed());

        slalom.setMovement(pose.getShift(), pose.getSpeed());

        scoreboard.update();
    }

    @Override
    public void init() {

    }

    @Override
    public void render(SpriteBatch batch) {

        startArea.render(batch);

        powder.render(batch);
        leaves.render(batch);

        slalom.render(batch);

        for (idx=0; idx<jumps.length; idx++)
        {
            jumps[idx].render(batch);
        }

        for (idx=0; idx<trees.length; idx++)
        {
            trees[idx].render(batch);
        }

        player.render(batch);

        scoreboard.render(batch);

        Gdx.graphics.setTitle("Ski Freeze (" + Gdx.graphics.getFramesPerSecond() + ")");
        countdown.render(batch);

        Toast.render(batch);
    }

    @Override
    public void update() {

        player.update();

        if (tricks.lastTrick != TrickPerformed.None)
        {
            Gdx.app.log("playscreen", tricks.lastTrick.toString());
            tricks.lastTrick = TrickPerformed.None;
        }

        if (countdown.goShowing) {
            slalom.update(player.playerBounds);

            startArea.update();

            /* TREE COLLISION AND UPDATING */
            boolean didCollideTree = false;

            for (idx=0; idx<trees.length; idx++)
            {
                // update the tree
                if (slalom.isPlayerIn) {
                    trees[idx].update(false);
                } else {
                    trees[idx].update(true);
                }

                if (player.pose != PlayerPose.JUMP) {
                    if (trees[idx].collides(player.playerBounds)) {
                        didCollideTree = true;
                    }
                }
            }

            /* JUMP COLLISION AND UPDATING */
            boolean didCollideJump = false;

            for (idx=0; idx<jumps.length; idx++)
            {
                // update the tree
                if (slalom.isPlayerIn) {
                    jumps[idx].update(false);
                } else {
                    jumps[idx].update(true);
                }

                if ((player.pose != PlayerPose.JUMP) && (player.pose != PlayerPose.BAIL)) {
                    if (jumps[idx].collides(player.playerBounds)) {
                        didCollideJump = true;
                    }
                }
            }

            /* HANDLE COLLISION RESULTS */
            if ((didCollideTree) && (player.pose != PlayerPose.BAIL)) {
                player.pose = PlayerPose.BAIL;
                powder.generatePlume(player.position, 50, 10);

                leaves.generatePlume(player.position, 50, 5);

                if (player.hasLanded)
                {
                    Toast.addMessage("Total Fail... \n(-20)");
                    scoreboard.reduceScore(20);
                } else if (isTimingLanding)
                {
                    Toast.addMessage("No stick... \n(-10)");
                    scoreboard.reduceScore(20);
                    isTimingLanding = false;
                } else {
                    Toast.addMessage("Dude... \n(-10)");
                    scoreboard.reduceScore(10);
                }
            } else if ((didCollideJump) && (player.pose != PlayerPose.BAIL)) {
                player.jump();
            } else if ((player.pose == PlayerPose.BAIL) && (!didCollideTree))
            {
                player.pose = PlayerPose.STRAIGHT;
                powder.generatePlume(player.position, 35, 10);
            }

            if ((player.pose == PlayerPose.BAIL) || (player.pose == PlayerPose.JUMP)) {
                // disallow spawn while jumping and bailing
                powder.update(player.position, false);
            } else {
                powder.update(player.position, true);
            }

            leaves.update();

            setMovement(player.pose);

            if (isTimingLanding) {
                if (landingTimer > LANDING_GRACE_DURATION) {
                    isTimingLanding = false;
                    Toast.addMessage("Landed\n(+10)");
                    scoreboard.addScore(10);
                } else {
                    landingTimer += Gdx.graphics.getDeltaTime();
                }
            } else if ((player.hasLanded) && (player.pose != PlayerPose.BAIL))
            {
                landingTimer = 0;
                isTimingLanding = true;

                powder.generatePlume(player.position, 60, 25);
            }

            if (slalom.hasFinished)
            {
                scoreboard.addScore(slalom.getScore());
            }
        }

        countdown.update();

        Toast.update();
    }

    @Override
    public void dispose() {
        for (idx=0; idx<trees.length; idx++)
        {
            trees[idx].dispose();
        }

        for (idx=0; idx<jumps.length; idx++)
        {
            jumps[idx].dispose();
        }

        slalom.dispose();
        startArea.dispose();
        powder.dispose();
        leaves.dispose();
        player.dispose();
        countdown.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            // NO
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if ((countdown.goShowing) && (player.pose != PlayerPose.BAIL) && (player.pose != PlayerPose.JUMP)) {
            switch (keycode) {
                case 20:
                    // down
                    player.turnDownForWhat();
                    setMovement(player.pose);
                    return false;
                case 21:
                    // left
                    player.turnLeft();
                    setMovement(player.pose);
                    return false;
                case 22:
                    // right
                    player.turnRight();
                    setMovement(player.pose);
                    return false;
                case 62:
                    game.loadScreen(new PlayScreen(game));
                    return false;
            }
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        tricks.touchDown(screenX, screenY, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if ((countdown.goShowing) && (player.pose != PlayerPose.BAIL) && (player.pose != PlayerPose.JUMP)) {
            // unproject the viewport for translated touch points
            //Vector2 translatedPick = new Vector2(screenX, screenY);
            //translatedPick = game.viewport.unproject(translatedPick);

            translatedPick = Util.translateTouchPick(screenX, screenY, game.viewport);

            if (leftButton.contains(translatedPick)) {
                player.turnLeft();
                setMovement(player.pose);
                return false;
            }

            if (rightButton.contains(translatedPick)) {
                player.turnRight();
                setMovement(player.pose);
                return false;
            }

            if (downButton.contains(translatedPick)) {
                player.turnDownForWhat();
                setMovement(player.pose);
                return false;
            }
        }


        tricks.touchUp(screenX, screenY, pointer, button);

        if (player.pose == PlayerPose.JUMP)
        {

        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        //Vector2 translatedPick = new Vector2(screenX, screenY);
        //translatedPick = game.viewport.unproject(translatedPick);

        //Gdx.app.log("T", pointer + " " + screenX + "." + screenY);

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

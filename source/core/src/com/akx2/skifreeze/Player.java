package com.akx2.skifreeze;

import com.akx2.x2.IGameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player implements IGameObject {

    public final int JUMP_TARGET = 200;
    public final int JUMP_SPEED = 200;
    public final Vector2 SPAWN = new Vector2(1080/2-64, 1200);

    TextureRegion[] regions;

    Sprite[] shadows;

    public Vector2 position;

    public PlayerPose pose;

    public Rectangle playerBounds;

    public Vector2 origin;

    public float rotation;

    public float jumpHeight;
    public boolean isLanding;
    public boolean hasLanded;

    public Player()
    {
        regions = new TextureRegion[9];
        shadows = new Sprite[9];

        for (int i=0; i<8; i++) {
            regions[i] = new TextureRegion(GameAssets.player, i * 128, 0, 128, 128);
            shadows[i] = new Sprite(GameAssets.playerShadow, i * 128, 0, 128, 128);
        }

        // get jump
        regions[8] = new TextureRegion(GameAssets.player, 0, 128, 128, 128);
        shadows[8] = new Sprite(GameAssets.playerShadow, 0, 128, 128, 128);

        playerBounds = new Rectangle(0, 0, 128, 128);

        pose = PlayerPose.STRAIGHT;

        position = new Vector2();
        origin = new Vector2();
        origin.x = 64;
        origin.y = 54;

        jumpHeight = 0;
        isLanding = false;

        for (int i=0; i<9; i++) {
            shadows[i].setPosition((int)SPAWN.x, (int)SPAWN.y);
            shadows[i].setColor(1, 1, 1, 0.2f);
        }

        setPosition ((int)SPAWN.x, (int)SPAWN.y);
    }

    public void jump()
    {
        pose = PlayerPose.JUMP;
        jumpHeight = 0;
        isLanding = false;
    }

    void setPosition (int x, int y)
    {
        position.x = x;
        position.y = y;

        playerBounds.setPosition(x, y);
    }

    public void turnLeft ()
    {
        pose = pose.previous();
    }

    public void turnRight()
    {
        pose = pose.next();
    }

    public void turnDownForWhat()
    {
        pose = PlayerPose.STRAIGHT;
    }

    @Override
    public void update() {
        hasLanded = false;
        if (pose == PlayerPose.BAIL)
        {
            rotation += -(750 * Gdx.graphics.getDeltaTime());
        } else {
            rotation = 0;
        }

        if (pose == PlayerPose.JUMP)
        {
            if (isLanding)
            {
                jumpHeight -= JUMP_SPEED * Gdx.graphics.getDeltaTime();

                if (jumpHeight <= 0)
                {
                    pose = PlayerPose.STRAIGHT;
                    hasLanded = true;
                }
            } else {
                jumpHeight += JUMP_SPEED * Gdx.graphics.getDeltaTime();

                if (jumpHeight >= JUMP_TARGET)
                {
                    isLanding = true;
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (pose == PlayerPose.BAIL)
        {
            batch.draw(shadows[pose.code], shadows[pose.code].getX() + 15, shadows[pose.code].getY(), origin.x, origin.y, 128, 128, 1, 1, rotation);
            batch.draw(regions[pose.code], position.x, position.y + 15, origin.x, origin.y, 128, 128, 1, 1, rotation);
        } else  if (pose == PlayerPose.JUMP){
            batch.draw(shadows[pose.code], shadows[pose.code].getX() + jumpHeight / 2 + 15, shadows[pose.code].getY() + jumpHeight / 2 - 15, origin.x, origin.y, 128, 128, 1, 1, rotation);
            batch.draw(regions[pose.code], position.x, position.y + jumpHeight, origin.x, origin.y, 128, 128, 1, 1, rotation);
        } else {
            batch.draw(regions[pose.code], position.x, position.y, origin.x, origin.y, 128, 128, 1, 1, rotation);
        }
    }

    @Override
    public void dispose() {

    }
}

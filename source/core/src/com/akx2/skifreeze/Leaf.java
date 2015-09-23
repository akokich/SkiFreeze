package com.akx2.skifreeze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Leaf {
    final int MOTION_RATE = 100;
    final float MOTION_SLOWDOWN = 200;
    final int MOTION_MAX_RANDOM = 50;

    final int ROTATION_SPEED = 200;

    TextureRegion flakeRegion;

    Random rand;

    Vector2 position;
    Vector2 origin;
    Vector2 scale;
    float rotation;

    Vector2 shift;

    Vector2 motion;

    boolean isActive = false;

    public Leaf ()
    {
        rand = new Random ();
        int randFlakeId = rand.nextInt(3);
        int flakeId = 0;
        switch (randFlakeId)
        {
            case 1:
                flakeId = 1;
                break;
            case 2:
                flakeId = 2;
                break;
        }

        flakeRegion = new TextureRegion(GameAssets.leaves, flakeId * 32, 0, 32, 32);

        position = new Vector2();
        scale = new Vector2(1f,1f);
        origin = new Vector2((flakeRegion.getRegionWidth() * scale.x / 2), (flakeRegion.getRegionHeight() * scale.y/2));

        rotation = 0;
        shift = new Vector2(0,0);
        motion = new Vector2(0,0);
    }

    public void setMovement (int x, int y)
    {
        shift.x = x;
        shift.y = y;
    }

    public void activatePlume (float x, float y, boolean isLeftSide, int plumeStrength)
    {
        isActive = true;
        position.x = x + 64 - (flakeRegion.getRegionWidth()/2);
        position.y = y - 10;

        if (isLeftSide)
        {
            motion.x = -(MOTION_RATE + (rand.nextInt(plumeStrength*4) - plumeStrength  * 2));

            rotation = -rand.nextInt(360);

        } else {
            motion.x = (MOTION_RATE + (rand.nextInt(plumeStrength*4) - plumeStrength  * 2));

            rotation = rand.nextInt(360);

        }

        motion.y = -(MOTION_RATE + (rand.nextInt(plumeStrength*6) - plumeStrength  * 3));
    }

    public void activateTrail(float x, float y, boolean isLeftSide)
    {
        isActive = true;
        position.x = x + 64 - (flakeRegion.getRegionWidth()/2);
        position.y = y - 10;

        if (isLeftSide)
        {
            motion.x = -(MOTION_RATE + rand.nextInt(MOTION_MAX_RANDOM));
        } else {
            motion.x = (MOTION_RATE + rand.nextInt(MOTION_MAX_RANDOM));
        }
    }

    public void update() {
        if (isActive) {
            if (shift.x != 0) {
                position.x += shift.x * Gdx.graphics.getDeltaTime();
            }

            if (shift.y != 0) {
                position.y += shift.y * Gdx.graphics.getDeltaTime();
            }

            position.x += motion.x * Gdx.graphics.getDeltaTime();
            position.y += motion.y * Gdx.graphics.getDeltaTime();

            if (motion.x > 0) {
                motion.x -= MOTION_SLOWDOWN * Gdx.graphics.getDeltaTime();
            } else if (motion.x < 0) {
                motion.x += MOTION_SLOWDOWN * Gdx.graphics.getDeltaTime();
            }

            if (motion.y > 0) {
                motion.y -= MOTION_SLOWDOWN * Gdx.graphics.getDeltaTime();
            } else if (motion.y < 0) {
                motion.y += MOTION_SLOWDOWN * Gdx.graphics.getDeltaTime();
            }

            rotation += (ROTATION_SPEED * Gdx.graphics.getDeltaTime());
        }
    }

    public void render(SpriteBatch batch) {
        if (isActive) {
            batch.draw(flakeRegion, position.x, position.y, origin.x, origin.y, flakeRegion.getRegionWidth(), flakeRegion.getRegionHeight(), scale.x, scale.y, rotation);
        }
    }

    public void dispose() {

    }
}

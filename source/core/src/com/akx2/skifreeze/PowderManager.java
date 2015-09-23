package com.akx2.skifreeze;

import com.akx2.x2.IGameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class PowderManager {
    public final int MAX_POWDER = 100;
    public final float SPAWN_RATE = 0.05f;
    public final int PLUME_COUNT = 25;

    Powder[] powders;
    int powdersIndex;

    int idx;
    float spawnTimer;

    public PowderManager ()
    {
        powders = new Powder[MAX_POWDER];

        for (idx=0; idx<MAX_POWDER; idx++)
        {
            powders[idx] = new Powder();
        }

        idx = 0;
        spawnTimer = 0;
        powdersIndex = 0;
    }

    int getNextPowder ()
    {
        if (powdersIndex == MAX_POWDER-1) {
            powdersIndex = 0;
            return powdersIndex;
        } else {
            powdersIndex++;
            return  powdersIndex;
        }
    }

    public void setMovement(int x, int y)
    {
        for (idx=0; idx<MAX_POWDER; idx++)
        {
            if (powders[idx].isActive) {
                powders[idx].setMovement(x, y);
            }
        }
    }

    public void generatePlume (Vector2 playerPosition, int plumeStrength, int plumeAmount)
    {
        for (idx=0; idx<plumeAmount; idx++)
        {
            powders[getNextPowder()].activatePlume(playerPosition.x, playerPosition.y, true, plumeStrength);
            powders[getNextPowder()].activatePlume(playerPosition.x, playerPosition.y, false, plumeStrength);
        }
    }

    public void update(Vector2 playerPosition, boolean allowSpawn) {
        for (idx=0; idx<MAX_POWDER; idx++)
        {
            powders[idx].update();
        }

        if (allowSpawn) {
            if (spawnTimer >= SPAWN_RATE) {
                spawnTimer = 0;
                powders[getNextPowder()].activateTrail(playerPosition.x, playerPosition.y, true);
                powders[getNextPowder()].activateTrail(playerPosition.x, playerPosition.y, false);
            } else {
                spawnTimer += Gdx.graphics.getDeltaTime();
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (idx=0; idx<MAX_POWDER; idx++)
        {
            powders[idx].render(batch);
        }
    }

    public void dispose() {
        for (idx=0; idx<MAX_POWDER; idx++)
        {
            powders[idx].dispose();
        }
    }
}

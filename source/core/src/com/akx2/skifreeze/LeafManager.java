package com.akx2.skifreeze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class LeafManager {
    public final int MAX_LEAVES = 100;
    public final float SPAWN_RATE = 0.05f;
    public final int PLUME_COUNT = 25;

    Leaf[] leaves;
    int powdersIndex;

    int idx;
    float spawnTimer;

    public LeafManager ()
    {
        leaves = new Leaf[MAX_LEAVES];

        for (idx=0; idx<MAX_LEAVES; idx++)
        {
            leaves[idx] = new Leaf();
        }

        idx = 0;
        spawnTimer = 0;
        powdersIndex = 0;
    }

    int getNextPowder ()
    {
        if (powdersIndex == MAX_LEAVES-1) {
            powdersIndex = 0;
            return powdersIndex;
        } else {
            powdersIndex++;
            return  powdersIndex;
        }
    }

    public void setMovement(int x, int y)
    {
        for (idx=0; idx<MAX_LEAVES; idx++)
        {
            if (leaves[idx].isActive) {
                leaves[idx].setMovement(x, y);
            }
        }
    }

    public void generatePlume (Vector2 playerPosition, int plumeStrength, int plumeAmount)
    {
        for (idx=0; idx<plumeAmount; idx++)
        {
            leaves[getNextPowder()].activatePlume(playerPosition.x, playerPosition.y, true, plumeStrength);
            leaves[getNextPowder()].activatePlume(playerPosition.x, playerPosition.y, false, plumeStrength);
        }
    }

    public void update() {
        for (idx=0; idx<MAX_LEAVES; idx++)
        {
            leaves[idx].update();
        }
    }

    public void render(SpriteBatch batch) {
        for (idx=0; idx<MAX_LEAVES; idx++)
        {
            leaves[idx].render(batch);
        }
    }

    public void dispose() {
        for (idx=0; idx<MAX_LEAVES; idx++)
        {
            leaves[idx].dispose();
        }
    }
}

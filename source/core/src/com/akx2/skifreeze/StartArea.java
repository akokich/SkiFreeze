package com.akx2.skifreeze;

import com.akx2.x2.IGameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class StartArea {

    Vector2 shift;

    public Vector2 skyPosition;

    public StartArea ()
    {
        skyPosition = new Vector2(0, 1920 - 670);

        shift = new Vector2(0,0);
    }

    public void setMovement (int x, int y)
    {
        shift.x = x;
        shift.y = y;
    }

    public void update() {
        if (shift.x != 0) {
            skyPosition.x += shift.x * Gdx.graphics.getDeltaTime();
        }

        if (shift.y != 0) {
            skyPosition.y += shift.y * Gdx.graphics.getDeltaTime();
        }

        if (skyPosition.y > 1920)
        {
            // hide
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(GameAssets.sky, skyPosition.x - 1920, skyPosition.y, 1920*3, 670);
    }

    public void dispose() {

    }
}

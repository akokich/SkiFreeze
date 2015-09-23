package com.akx2.skifreeze;

import com.akx2.x2.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class SlopeObject extends GameObject {
    public SlopeObject(Texture textureAsset, Boolean spawnOnScreen) {
        super(textureAsset);

        random = new Random();

        shift = new Vector2(0,0);

        collisionOffset = new Vector2(0, 0);
        collisionBox = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());

        setPlacement(spawnOnScreen);
    }


    Random random;

    public Vector2 shift;

    Rectangle collisionBox;
    public Vector2 collisionOffset;

    private void setPlacement(Boolean spawnOnScreen)
    {
        position.x = random.nextInt(2000) - 1000;

        if (spawnOnScreen) {
            position.y = random.nextInt(300) - texture.getHeight();
        } else {
            position.y = -(texture.getHeight() + random.nextInt(1920));
        }

        collisionBox.setPosition(position.x + collisionOffset.x, position.y);

    }

    public boolean collides(Rectangle playerBounds)
    {
        return Intersector.overlaps(playerBounds, collisionBox);
    }

    public void setMovement (int x, int y)
    {
        shift.x = x;
        shift.y = y;
    }

    public void update(boolean respawn) {
        super.update();

        if (shift.x != 0) {
            position.x += shift.x * Gdx.graphics.getDeltaTime();
        }

        if (shift.y != 0) {
            position.y += shift.y * Gdx.graphics.getDeltaTime();
        }

        collisionBox.setPosition(position.x + collisionOffset.x, position.y);

        if ((position.y > 1920) && (respawn))
        {
            setPlacement(false);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (isOnScreen()) {
            super.render(batch);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

package com.akx2.skifreeze;

import com.akx2.x2.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class SlopeTree extends SlopeObject {

    public SlopeTree(Boolean spawnOnScreen) {
        super(GameAssets.tree0, spawnOnScreen);

        collisionOffset = new Vector2(texture.getWidth() / 3, texture.getHeight() / 2);
        collisionBox = new Rectangle(position.x + collisionOffset.x, position.y, texture.getWidth()-(collisionOffset.x*2), texture.getHeight() - collisionOffset.y);
    }
}

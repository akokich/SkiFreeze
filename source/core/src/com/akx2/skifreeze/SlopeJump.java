package com.akx2.skifreeze;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SlopeJump extends SlopeObject {
    public SlopeJump(Boolean spawnOnScreen) {
        super(GameAssets.jump, spawnOnScreen);

        collisionOffset = new Vector2(0, texture.getHeight() - 10);
        collisionBox = new Rectangle(position.x + collisionOffset.x, position.y + collisionOffset.y, texture.getWidth()-(collisionOffset.x*2), texture.getHeight() - collisionOffset.y);
    }
}

package com.akx2.x2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface IGameObject {
    public abstract void update();
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();
}

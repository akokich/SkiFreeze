package com.akx2.x2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameScreen implements InputProcessor {
    public GameMain game;

    public GameScreen (GameMain main)
    {
        game = main;
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
    }

    public abstract void init();

    public abstract void render(SpriteBatch batch);

    public abstract void update();

    public abstract void dispose();

    @Override
    public abstract boolean keyDown(int keycode);

    @Override
    public abstract boolean keyUp(int keycode);

    @Override
    public abstract boolean keyTyped(char character);

    @Override
    public abstract boolean touchDown(int screenX, int screenY, int pointer, int button);

    @Override
    public abstract boolean touchUp(int screenX, int screenY, int pointer, int button);

    @Override
    public abstract boolean touchDragged(int screenX, int screenY, int pointer);

    @Override
    public abstract boolean mouseMoved(int screenX, int screenY);

    @Override
    public abstract boolean scrolled(int amount);
}

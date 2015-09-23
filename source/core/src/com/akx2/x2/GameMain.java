package com.akx2.x2;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.*;

import java.util.Stack;

public class GameMain implements ApplicationListener {
    SpriteBatch batch;

    public OrthographicCamera camera;

    public Viewport viewport;

    public Stack<GameScreen> screens;

    public void loadScreen (GameScreen screen)
    {
        if (!screens.empty()) {
            screens.peek().dispose();
        }

        screen.init();
        screens.push(screen);

        Gdx.app.log("GameMain", "loadScreen " + screen);
    }

    @Override
    public void create()
    {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();

        viewport = new FitViewport(1080, 1920, camera);
        viewport.apply();

        Gdx.app.log("GameMain", "VP: " + viewport.getWorldWidth() + " " + viewport.getWorldHeight());
        Gdx.app.log("GameMain", "GDX: " + Gdx.graphics.getWidth() + " " + Gdx.graphics.getHeight());

        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        screens = new Stack<GameScreen>();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render ()
    {
        camera.update();

        screens.peek().update();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        screens.peek().render(batch);
        batch.end();
    }

    @Override
    public void pause() {
        Gdx.app.log("GameMain", "pause");
    }

    @Override
    public void resume() {
        Gdx.app.log("GameMain", "resume");
    }

    @Override
    public void dispose() {
        batch.dispose();
        screens.peek().dispose();
    }

}

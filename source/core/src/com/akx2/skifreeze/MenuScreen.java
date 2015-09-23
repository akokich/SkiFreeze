package com.akx2.skifreeze;

import com.akx2.x2.GameMain;
import com.akx2.x2.GameObject;
import com.akx2.x2.GameScreen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MenuScreen extends GameScreen {
    GameObject startButton;
    GameObject scoreButton;
    GameObject logo;

    SnowflakeManager snowflakes_background;

    public MenuScreen(GameMain main) {
        super(main);
    }

    @Override
    public void init() {
        logo = new GameObject(GameAssets.logo);
        logo.position.x = (int)((game.viewport.getWorldWidth() / 2) - (logo.texture.getWidth() / 2));
        logo.position.y = (int)(game.viewport.getWorldHeight() - logo.texture.getHeight() - 200);

        startButton = new GameObject(GameAssets.startButton);
        startButton.position.x =(int)((game.viewport.getWorldWidth() / 2) - (startButton.texture.getWidth() / 2));
        startButton.position.y = (int)(game.viewport.getWorldHeight() - startButton.texture.getHeight() - 1000);

        scoreButton = new GameObject(GameAssets.scoreButton);
        scoreButton.position.x = (int)((game.viewport.getWorldWidth() / 2) - (scoreButton.texture.getWidth() / 2));
        scoreButton.position.y = (int) (game.viewport.getWorldHeight() - scoreButton.texture.getHeight() - 1275);

        snowflakes_background = new SnowflakeManager(25, true);
    }

    @Override
    public void render(SpriteBatch batch) {
        snowflakes_background.render(batch);

        logo.render(batch);

        startButton.render(batch);
        scoreButton.render(batch);

    }

    @Override
    public void update() {
        snowflakes_background.update();
        startButton.update();
        scoreButton.update();
    }

    @Override
    public void dispose() {
        snowflakes_background.dispose();
        startButton.dispose();
        scoreButton.dispose();
        logo.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            // NO
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // unproject the viewport for translated touch points
        Vector2 translatedPick = new Vector2(screenX,screenY);
        translatedPick = game.viewport.unproject(translatedPick);

        if (startButton.isInside((int)translatedPick.x, (int)translatedPick.y)) {
            game.loadScreen(new PlayScreen(game));
            //snowflakes_foreground.trigger();
            return false;
        }

        if (scoreButton.isInside((int)translatedPick.x, (int)translatedPick.y)) {
            return false;
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

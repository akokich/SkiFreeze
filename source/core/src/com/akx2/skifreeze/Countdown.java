package com.akx2.skifreeze;

import com.akx2.x2.IGameObject;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Timer;
import java.util.TimerTask;

public class Countdown implements IGameObject {
    public final int TIMER_DELAY = 1000;

    public Boolean expired;
    public Boolean goShowing;

    public TextureRegion[] countImages;

    int currentImage;

    Timer timer;

    public Countdown() {
        countImages = new TextureRegion[3];

        for (int i=0; i<3; i++) {
            countImages[i] = new TextureRegion(GameAssets.countdown, i * 240, 0, 240, 200);
        }

        reset ();
    }

    private void reset ()
    {
        currentImage = 2;
        expired = false;
        goShowing = false;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currentImage--;

                if (goShowing)
                {
                    expired = true;
                    timer.cancel();
                    return;
                }

                if (currentImage < 0) {
                    currentImage = 0;
                    goShowing = true;
                }
            }
        }, TIMER_DELAY, TIMER_DELAY);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch batch)
    {
        if (!expired) {
            if (!goShowing) {
                batch.draw(countImages[currentImage], (1080 / 2) - 110, 1400);
            } else {
                batch.draw(GameAssets.go, (1080 / 2) - 290, 1400);
            }
        }
    }

    @Override
    public void dispose ()
    {
    }
}

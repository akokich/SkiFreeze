package com.akx2.skifreeze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Scoreboard {

    final int INCREMENT = 1;
    final float INCREMENT_DELAY = 0.02f;

    int score;
    int scoreToAdd;
    int scoreToReduce;

    float incrementTimer;

    public Scoreboard ()
    {
        score = 0;
        scoreToAdd = 0;
        scoreToReduce = 0;
        incrementTimer = 0;
    }

    public void reduceScore (int amount)
    {
        scoreToReduce = amount;
    }

    public void addScore(int amount)
    {
        scoreToAdd += amount;
    }

    public void update()
    {
        if (scoreToAdd > 0) {
            if (incrementTimer >= INCREMENT_DELAY) {
                incrementTimer = 0;

                if (scoreToAdd >= INCREMENT) {
                    score += INCREMENT;
                } else {
                    score += scoreToAdd;
                }
                scoreToAdd -= INCREMENT;
            }

            incrementTimer += Gdx.graphics.getDeltaTime();
        }

        if (scoreToReduce > 0)
        {
            if (incrementTimer >= INCREMENT_DELAY) {
                incrementTimer = 0;

                if (scoreToReduce >= INCREMENT) {
                    score += -INCREMENT;
                } else {
                    score += -scoreToReduce;
                }
                scoreToReduce -= INCREMENT;
            }

            incrementTimer += Gdx.graphics.getDeltaTime();
        }

    }

    public void render (SpriteBatch batch)
    {
        batch.draw(GameAssets.scoreBackground, 0, 1920 - GameAssets.scoreBackground.getHeight());

        GameAssets.font_scoreboard.draw(batch, "" + score, (1080/2) - (GameAssets.font_scoreboard.getBounds("" + score).width / 2), (1920 - (GameAssets.scoreBackground.getHeight() / 2)) + (GameAssets.font_scoreboard.getBounds("" + score).height / 2));
    }
}

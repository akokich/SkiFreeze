package com.akx2.skifreeze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Slalom {
    public final float MISSED_GATE_REDUCTION = 1;
    public final Format formatter = new SimpleDateFormat( "s.SSS" );
    public final int MAX_GATES = 10;

    public SlalomGate[] gates;

    public Vector2 shift;

    public Vector2 position;
    public Vector2 signPosition;

    public Vector2 startFlagsPosition;
    public Vector2 endFlagsPosition;

    public Rectangle startTrigger;
    public Rectangle endTrigger;

    public boolean isActive;
    public boolean isPlayerIn;
    public boolean hasFinished; // one tick flag so we can trigger shit in PlayScrren

    public float stopwatch = 0;
    public Date stopwatchDate;

    public Slalom ()
    {
        stopwatchDate = new Date();
        shift = new Vector2(0,0);

        position = new Vector2(0,0);
        signPosition = new Vector2(0,0);

        startFlagsPosition = new Vector2(0,0);
        endFlagsPosition = new Vector2(0,0);

        gates = new SlalomGate[MAX_GATES];
        for (int i=0; i<MAX_GATES; i++)
        {
            gates[i] = new SlalomGate(SlalomGateMode.GATE, SlalomGateSide.LEFT);
            i++;
            gates[i] = new SlalomGate(SlalomGateMode.GATE, SlalomGateSide.RIGHT);
        }

        startTrigger = new Rectangle();
        endTrigger = new Rectangle();

        isActive = false;
        isPlayerIn = false;
        hasFinished = false;
    }

    public void spawn (boolean onScreen)
    {
        //Gdx.app.log("slalom", "ACTIVATE");
        isActive = true;
        isPlayerIn = false;
        hasFinished = false;

        stopwatch = 0;

        position.x = 0;

        if (onScreen) {
            position.y = 500;
        } else {
            position.y = 0 - (GameAssets.signSlalom.getHeight() + 10);
        }

        for (int i=0; i<MAX_GATES; i++)
        {
            gates[i].reset();
        }

        setRelative();
    }

    void setRelative ()
    {
        signPosition.x = position.x + 850;
        signPosition.y = position.y;

        startFlagsPosition.x = position.x + 2000;
        startFlagsPosition.y = position.y - 1000;

        for (int i=0; i<MAX_GATES; i++)
        {
            gates[i].position.x = startFlagsPosition.x + 333;
            gates[i].position.y = startFlagsPosition.y - ((i*500) + 500);
            i++;
            gates[i].position.x = startFlagsPosition.x + 666;
            gates[i].position.y = startFlagsPosition.y - ((i*500) + 500);
        }

        endFlagsPosition.x = position.x + 2000;
        endFlagsPosition.y = position.y - (MAX_GATES * 500) - 1500;

        startTrigger.x = startFlagsPosition.x;
        startTrigger.y = startFlagsPosition.y;
        startTrigger.width = 1040;
        startTrigger.height = 100;

        endTrigger.x = endFlagsPosition.x;
        endTrigger.y = endFlagsPosition.y;
        endTrigger.width = 1040;
        endTrigger.height = 100;
    }

    public void setMovement (int x, int y)
    {
        shift.x = x;
        shift.y = y;
    }

    public int getScore ()
    {
        int totalOk = 0;
        int outScore = 0;

        for (int i=0;i<MAX_GATES; i++)
        {
            if (gates[i].state == SlalomGateState.OK)
            {
                totalOk++;
            }
        }

        outScore += totalOk * 10;

        if (totalOk == MAX_GATES)
        {
            Toast.addMessage("PERFECT!\n(+" + outScore + ")");
            outScore += outScore;
        }

        Toast.addMessage(totalOk + " of " + MAX_GATES + "\n(+" + outScore + ")");

        return outScore;
    }

    public void update(Rectangle playerBounds) {
        if (isActive) {
            if (hasFinished)
            {
                hasFinished = false;
            }

            if (shift.x != 0) {
                position.x += shift.x * Gdx.graphics.getDeltaTime();
            }

            if (shift.y != 0) {
                position.y += shift.y * Gdx.graphics.getDeltaTime();
            }

            setRelative();

            if (isPlayerIn) {
                for (int i = 0; i < MAX_GATES; i++) {
                    gates[i].update(playerBounds);
                }
            }

            if ((Intersector.overlaps(playerBounds, startTrigger)) && (!isPlayerIn)) {
                isPlayerIn = true;
                //Toast.addMessage("Slalom: START!");
                //Gdx.app.log("slalom", "START");
            }

            if ((Intersector.overlaps(playerBounds, endTrigger)) && (isPlayerIn)) {
                isPlayerIn = false;
                hasFinished = true;

                int totalOk = 0;
                for (int i=0;i<MAX_GATES; i++)
                {
                    if (gates[i].state == SlalomGateState.OK)
                    {
                        totalOk++;
                    }
                }

                //Gdx.app.log("",stopwatch + " " + (MAX_GATES - totalOk) * MISSED_GATE_REDUCTION);

                stopwatch += (MAX_GATES - totalOk) * MISSED_GATE_REDUCTION;

                //Gdx.app.log("","" + stopwatch);

                Toast.addMessage("FINISH\n" + formatter.format(stopwatch * 1000));
            }

            if ((isPlayerIn) && (!hasFinished))
            {
                stopwatch += Gdx.graphics.getDeltaTime();
            }

            if (endFlagsPosition.y > 1920) {
                isActive = false;
                //Gdx.app.log("slalom", "DEACTIVATE");

                if (isPlayerIn)
                {
                    // player did not cross the finish line
                    isPlayerIn = false;
                    Toast.addMessage("Slalom: DNF!");
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        if (isActive) {

            batch.draw(GameAssets.signSlalom, signPosition.x, signPosition.y);
            batch.draw(GameAssets.signSlalom, signPosition.x + 1000, signPosition.y);

            if (isPlayerIn)
            {
                batch.draw(GameAssets.flag_RIGHT_OK, startFlagsPosition.x, startFlagsPosition.y);
                batch.draw(GameAssets.flag_LEFT_OK, startFlagsPosition.x + 1000, startFlagsPosition.y);

                batch.draw(GameAssets.flag_RIGHT_OK, endFlagsPosition.x, endFlagsPosition.y);
                batch.draw(GameAssets.flag_LEFT_OK, endFlagsPosition.x + 1000, endFlagsPosition.y);
            } else {
                batch.draw(GameAssets.flag_LEFT_CONTROL, startFlagsPosition.x, startFlagsPosition.y);
                batch.draw(GameAssets.flag_RIGHT_CONTROL, startFlagsPosition.x + 1000, startFlagsPosition.y);

                batch.draw(GameAssets.flag_LEFT_CONTROL, endFlagsPosition.x, endFlagsPosition.y);
                batch.draw(GameAssets.flag_RIGHT_CONTROL, endFlagsPosition.x + 1000, endFlagsPosition.y);
            }

            batch.draw(GameAssets.startLine, startTrigger.x, startTrigger.y, startTrigger.width, GameAssets.startLine.getHeight());

            for (int i=0; i<endTrigger.width/10; i++) {
                batch.draw(GameAssets.finishLine,  endTrigger.x + (i * 10), endTrigger.y, GameAssets.startLine.getWidth(), GameAssets.startLine.getHeight());
            }

            for (int i = 0; i < MAX_GATES; i++) {
                gates[i].render(batch);
            }

            if (isPlayerIn)
            {
                GameAssets.font_scoreboard.draw(batch, formatter.format(stopwatch * 1000), (1080/2) - (GameAssets.font_scoreboard.getBounds(formatter.format(stopwatch * 1000)).width / 2), 1920 - (1920/11));
            }

        }
    }

    public void dispose() {

    }
}

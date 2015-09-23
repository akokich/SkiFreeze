package com.akx2.skifreeze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Tricks {
    Vector2[] points;
    Boolean[] isDown;

    int xDelta = 0;
    int yDelta = 0;

    TrickPerformed lastTrick = TrickPerformed.None;

    public Tricks ()
    {
        points = new Vector2[2];
        for (int i=0; i<2; i++)
        {
            points[i] = new Vector2(0, 0);
        }

        isDown = new Boolean[2];
        for (int i=0; i<2; i++)
        {
            isDown[i] = false;
        }
    }

    public void touchUp(int screenX, int screenY, int pointer, int button)
    {
        if (pointer < 2) {
            xDelta = screenX - (int) points[pointer].x;
            yDelta = (int) points[pointer].y - screenY;
            isDown[pointer] = false;

            Gdx.app.log("touch up", pointer + " " + xDelta + " " + yDelta);

            if (xDelta > 300)
            {
                lastTrick = TrickPerformed.Right360;
            } else if (xDelta < -300)
            {
                lastTrick = TrickPerformed.Left360;
            } else if (yDelta > 300)
            {
                lastTrick = TrickPerformed.BackFlip;
            } else if (yDelta < -300)
            {
                lastTrick = TrickPerformed.FrontFlip;
            }
        }
    }

    public void touchDown(int screenX, int screenY, int pointer, int button) {
        if (pointer < 2) {
            points[pointer].x = screenX;
            points[pointer].y = screenY;
            isDown[pointer] = true;
        }
    }

    // update returns if a trick was performed
    public TrickPerformed update ()
    {
        return lastTrick;
    }
}

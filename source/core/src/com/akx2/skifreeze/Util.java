package com.akx2.skifreeze;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Util {
    public static Vector2 translatedPick = new Vector2(0, 0);

    public static Vector2 translateTouchPick (int screenX, int screenY, Viewport viewport)
    {
        translatedPick.x = screenX;
        translatedPick.y = screenY;
        return viewport.unproject(translatedPick);
    }
}

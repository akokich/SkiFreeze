package com.akx2.skifreeze;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SlalomGate {
    public Vector2 position;
    public SlalomGateMode mode;
    public SlalomGateState state;
    public SlalomGateSide side;

    public SlalomGate(SlalomGateMode gateMode, SlalomGateSide gateSide)
    {
        mode = gateMode;
        side = gateSide;
        state = SlalomGateState.DEFAULT;

        position = new Vector2(0,0);
    }

    public void reset ()
    {
        state = SlalomGateState.DEFAULT;
    }

    // TODO Unhack this bullshit
    public int update (Rectangle playerBounds)
    {
        if (state == SlalomGateState.DEFAULT)
        {
            if (playerBounds.y < position.y) {
                switch (side) {
                    case LEFT:
                        if ((playerBounds.x + (playerBounds.width/2) - 3) >= (position.x + (GameAssets.flag_LEFT_CONTROL.getRegionWidth()/2))) {
                            state = SlalomGateState.FAIL;
                        } else {
                            state = SlalomGateState.OK;
                        }
                        break;
                    case RIGHT:
                        if ((playerBounds.x + (playerBounds.width/2)) <= (position.x + (GameAssets.flag_LEFT_CONTROL.getRegionWidth()/2))) {
                            state = SlalomGateState.FAIL;
                        } else {
                            state = SlalomGateState.OK;
                        }
                        break;
                }
            }
        }

        return 0;
    }

    public void render (SpriteBatch batch)
    {
        switch (state) {
            case DEFAULT:
                switch (mode) {
                    case CONTROL:
                        switch (side) {
                            case LEFT:
                                batch.draw(GameAssets.flag_LEFT_CONTROL, position.x, position.y);
                                break;
                            case RIGHT:
                                batch.draw(GameAssets.flag_RIGHT_CONTROL, position.x, position.y);
                                break;
                        }
                        break;
                    case GATE:
                        switch (side) {
                            case LEFT:
                                batch.draw(GameAssets.flag_LEFT, position.x, position.y);
                                break;
                            case RIGHT:
                                batch.draw(GameAssets.flag_RIGHT, position.x, position.y);
                                break;
                        }
                        break;
                }
                break;
            case OK:
                switch (side) {
                    case LEFT:
                        batch.draw(GameAssets.flag_LEFT_OK, position.x, position.y);
                        break;
                    case RIGHT:
                        batch.draw(GameAssets.flag_RIGHT_OK, position.x, position.y);
                        break;
                }
                break;
            case FAIL:
                switch (side) {
                    case LEFT:
                        batch.draw(GameAssets.flag_LEFT_FAIL, position.x, position.y);
                        break;
                    case RIGHT:
                        batch.draw(GameAssets.flag_RIGHT_FAIL, position.x, position.y);
                        break;
                }
                break;
        }
    }

}

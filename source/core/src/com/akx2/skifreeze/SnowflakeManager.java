package com.akx2.skifreeze;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SnowflakeManager {

    Snowflake[] flakes;

    int count;

    boolean hasTriggered;

    public SnowflakeManager (int totalFlakes, boolean autoStart)
    {
        count = totalFlakes;

        hasTriggered = autoStart;

        flakes = new Snowflake[count];
        for (int i=0; i<count; i++)
        {
            switch (i%3)
            {
                case 0:
                    flakes[i] = new Snowflake(GameAssets.flake0);
                    break;
                case 1:
                    flakes[i] = new Snowflake(GameAssets.flake1);
                    break;
                case 2:
                    flakes[i] = new Snowflake(GameAssets.flake2);
                    break;
            }
        }
    }

    public void trigger()
    {
        hasTriggered = true;
    }

    public void setBehaviour (int MinRotationSpeed, int MaxRotationSpeed, int MinScale, int MaxScale, int MinXPosition, int MaxXPosition, int MaxYPosition,
                              int MinTargetPosition,int MaxTargetPosition,int MinMovementSpeed,int MaxMovementSpeed,int StartY, int RestartY)
    {
        for (int i=0; i<count; i++) {
            flakes[i].minRotationSpeed = MinRotationSpeed;
            flakes[i].maxRotationSpeed = MaxRotationSpeed;
            flakes[i].minScale = MinScale;
            flakes[i].maxScale = MaxScale;
            flakes[i].minXPosition = MinXPosition;
            flakes[i].maxXPosition = MaxXPosition;
            flakes[i].maxYPosition = MaxYPosition;
            flakes[i].minTargetPosition = MinTargetPosition;
            flakes[i].maxTargetPosition = MaxTargetPosition;
            flakes[i].minMovementSpeed = MinMovementSpeed;
            flakes[i].maxMovementSpeed = MaxMovementSpeed;
            flakes[i].startY = StartY;
            flakes[i].restartY = RestartY;

            flakes[i].resetFlake(StartY);
        }
    }

    public void render (SpriteBatch batch)
    {
        for (int i=0; i<count; i++)
        {
            flakes[i].render(batch);
        }
    }

    public void update()
    {
        if (hasTriggered) {
            for (int i = 0; i < count; i++) {
                flakes[i].update();
            }
        }
    }

    public void dispose()
    {
        for (int i=0; i<count; i++)
        {
            flakes[i].dispose();
        }

        flakes = null;
    }
}

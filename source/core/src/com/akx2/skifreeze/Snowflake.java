package com.akx2.skifreeze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Snowflake {
    public int minRotationSpeed;
    public int maxRotationSpeed;
    public int minScale;
    public int maxScale;
    public int minXPosition;
    public int maxXPosition;
    public int maxYPosition;
    public int minTargetPosition;
    public int maxTargetPosition;
    public int minMovementSpeed;
    public int maxMovementSpeed;

    public int startY;
    public int restartY;

    Texture texture;
    TextureRegion textureRegion;

    Vector2 position;
    Vector2 origin;
    Vector2 targetPosition;

    Random random;

    Vector2 moveToDirectionVector;
    Vector2 moveToVelocityVector;
    Vector2 moveToMovementVector;

    float rotation;
    float rotationSpeed;
    float scale;

    public Snowflake (Texture textureAsset)
    {
        minRotationSpeed = -25;
        maxRotationSpeed = 50;
        minScale = 50;
        maxScale = 50;
        minXPosition = 0;
        maxXPosition = 1080;
        maxYPosition = 2300;
        minTargetPosition = -1080;
        maxTargetPosition = 2160;
        minMovementSpeed = 50;
        maxMovementSpeed = 100;
        startY = 0;
        restartY = 1920;

        random = new Random();

        texture = textureAsset;
        origin = new Vector2();
        origin.x = texture.getWidth() / 2;
        origin.y = texture.getHeight() / 2;

        textureRegion = new TextureRegion(texture);

        position = new Vector2();
        targetPosition = new Vector2();

        moveToDirectionVector = new Vector2();
        moveToVelocityVector = new Vector2();
        moveToMovementVector = new Vector2();

        rotation = 0;
        rotationSpeed = 0;
        scale = 1;

        resetFlake (startY);
    }

    public void update ()
    {
        moveToMovementVector.set(moveToVelocityVector).scl(Gdx.graphics.getDeltaTime());
        position.add(moveToMovementVector);

        rotation = (rotation + (rotationSpeed * Gdx.graphics.getDeltaTime()));

        if (position.y < -texture.getHeight()) {
            resetFlake(restartY);
        }
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(textureRegion, position.x, position.y, origin.x, origin.y, texture.getWidth(), texture.getHeight(), scale, scale, rotation);
    }

    public void dispose()
    {
        //texture.dispose();
    }

    public void resetFlake (float yStart)
    {
        rotation = random.nextInt(300);
        rotationSpeed = random.nextInt(maxRotationSpeed) + minRotationSpeed;

        scale = (((float)random.nextInt(maxScale)) + minScale) * 0.01f;

        position.x = random.nextInt(maxXPosition) + minXPosition;
        position.y = yStart + random.nextInt(maxYPosition);

        targetPosition.x = random.nextInt(maxTargetPosition) + minTargetPosition;
        targetPosition.y = -texture.getHeight();

        moveToDirectionVector.set(targetPosition).sub(position).nor();
        moveToVelocityVector = new Vector2(moveToDirectionVector).scl(random.nextInt(maxMovementSpeed) + minMovementSpeed);
    }
}

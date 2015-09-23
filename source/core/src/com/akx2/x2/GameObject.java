package com.akx2.x2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject implements IGameObject {
    public Texture texture;

    public Vector2 position;

    Rectangle tScreenBounds;
    Rectangle tRectangle;

    public GameObject (Texture textureAsset)
    {
        texture = textureAsset;

        position = new Vector2(0,0);

        tScreenBounds = new Rectangle(-texture.getWidth(), -texture.getHeight(), 1080 + texture.getWidth(), 1920 + texture.getHeight());
    }

    public boolean isInside(int x, int y)
    {
        tRectangle = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
        return tRectangle.contains(x,y);
    }

    public boolean isOnScreen ()
    {
        return tScreenBounds.contains(position);
    }

    public void update()
    {

    }

    public void render(SpriteBatch batch)
    {
        batch.draw(texture, position.x, position.y);
    }

    public void dispose ()
    {

    }
}

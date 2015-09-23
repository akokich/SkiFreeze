package com.akx2.skifreeze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class Toast {

    private final static float MESSAGE_DURATION = 2f;

    private final static float FADE_SPEED = 4f;

    private static Stack<String> buffer = new Stack<String>();
    private static Stack<String> queue = new Stack<String>();

    private static float messageTimer = 0;

    private static String currentMessage = "";

    private static int idx = 0;

    private static boolean isFadingIn = false;
    private static boolean isFadingOut = false;
    private static float fade = 0;

    private static float alpha = 1;

    public static void update()
    {
        if ((!isFadingIn) && (!isFadingOut))
        {
            messageTimer += Gdx.graphics.getDeltaTime();
        }

        if (messageTimer >= MESSAGE_DURATION) {
            isFadingOut = true;
        }

        if (buffer.empty())
        {
            for (idx=0; idx<queue.size(); idx++)
            {
                buffer.push(queue.pop());
            }
        } else {
            if (messageTimer >= MESSAGE_DURATION) {
                isFadingOut = true;
            }
        }

        if (isFadingOut)
        {
            alpha -= (FADE_SPEED * Gdx.graphics.getDeltaTime());

            if (alpha <= 0) {
                alpha = 0;
                isFadingOut = false;
                nextMessage();
            }
        }

        if (isFadingIn)
        {
            alpha += (FADE_SPEED * Gdx.graphics.getDeltaTime());

            if (alpha >= 1) {
                alpha = 1;
                isFadingIn = false;
            }
        }
    }

    static void nextMessage ()
    {
        messageTimer = 0;

        if (buffer.empty())
        {
            currentMessage = "";
        } else {
            currentMessage = buffer.pop();
            isFadingIn = true;
        }
    }

    public static void addMessage(String msg)
    {
        queue.push(msg);
    }

    public static void render(SpriteBatch batch)
    {
        if (currentMessage.length() > 0)
        {
            GameAssets.font_scoreboard.setColor(1, 1, 1, alpha);
            GameAssets.font_scoreboard.drawMultiLine(batch, currentMessage, 0, 1920 - (1920/12), 1080, BitmapFont.HAlignment.CENTER);
            GameAssets.font_scoreboard.setColor(1, 1, 1, 1);
        }
    }
}

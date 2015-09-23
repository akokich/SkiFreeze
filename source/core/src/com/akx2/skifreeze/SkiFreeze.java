package com.akx2.skifreeze;

import com.akx2.x2.GameMain;

public class SkiFreeze extends GameMain {

	@Override
	public void create () {
        super.create();

        // Load the game asset library
        GameAssets.loadAssets();

        //loadScreen(new PlayScreen(this));
        loadScreen(new MenuScreen(this));
	}
}

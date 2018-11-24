package ru.geekbrains;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.geekbrains.screen.MenuScreen;

public class StarGame extends Game {
/*	private SpriteBatch batch;
	private Texture img;
	private Texture playerShip;*/
	
	@Override
	public void create () {
		setScreen(new MenuScreen());
/*		batch = new SpriteBatch();
		img = new Texture("background.png");
		playerShip = new Texture("playerShip.png");*/
	}

/*	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.draw(playerShip, 150, 400, 100, 100);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}*/
}

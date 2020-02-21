package edu.csuci.platformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.csuci.platformer.gamestates.GameStateType;
import edu.csuci.platformer.managers.GameStateManager;

public class Platformer extends ApplicationAdapter {

	private GameStateManager gameStateManager;
	
	@Override
	public void create () {
		gameStateManager = new GameStateManager(GameStateType.PLAY);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gameStateManager.step(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
		gameStateManager.resize(width, height);
	}

	@Override
	public void dispose () {
		gameStateManager.dispose();
	}
}

package edu.csuci.platformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.csuci.platformer.gamestates.GameStateType;
import edu.csuci.platformer.managers.ContentManager;
import edu.csuci.platformer.managers.GameStateManager;
import edu.csuci.platformer.managers.input.MyInput;
import edu.csuci.platformer.managers.input.MyInputProcessor;

public class Platformer extends ApplicationAdapter {

	private GameStateManager gameStateManager;
	public static ContentManager content;
	
	@Override
	public void create () {
		content = new ContentManager();
		Gdx.input.setInputProcessor(new MyInputProcessor());
		gameStateManager = new GameStateManager(GameStateType.PLAY);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gameStateManager.step(Gdx.graphics.getDeltaTime());
		MyInput.update();
	}

	@Override
	public void resize(int width, int height) {
		gameStateManager.resize(width, height);
	}

	@Override
	public void dispose () {
		gameStateManager.dispose();
		content.dispose();
	}
}

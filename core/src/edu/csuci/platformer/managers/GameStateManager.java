package edu.csuci.platformer.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import edu.csuci.platformer.gamestates.AbstractGameState;
import edu.csuci.platformer.gamestates.GameStateType;

public class GameStateManager implements Disposable {

    private AbstractGameState currentState;
    private SpriteBatch sb;
    private ShapeRenderer sr;

    public GameStateManager(GameStateType initialState) {
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        setState(initialState);
    }

    public void setState(GameStateType gameStateType) {
        if (currentState != null) currentState.dispose();
        try {
            currentState = (AbstractGameState) ClassReflection
                    .getConstructor(gameStateType.stateClass, GameStateManager.class)
                    .newInstance(this);
        } catch (ReflectionException re) {
            throw new GdxRuntimeException("Game state " + gameStateType + " cannot be created");
        }
        currentState.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void step(float dt) {
        currentState.handleInput(dt);
        currentState.update(dt);
        currentState.draw(dt, sb, sr);
    }

    public void resize(int width, int height) {
        currentState.resize(width, height);
    }

    @Override
    public void dispose() {
        currentState.dispose();
        sb.dispose();
        sr.dispose();
    }
}

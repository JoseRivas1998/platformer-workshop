package edu.csuci.platformer.managers.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class MyInputProcessor extends InputAdapter {

    @Override
    public boolean keyDown(int keycode) {
        return processKey(keycode, true);
    }

    @Override
    public boolean keyUp(int keycode) {
        return processKey(keycode, false);
    }

    private boolean processKey(int keycode, boolean value) {
        if(keycode == Input.Keys.SPACE) {
            MyInput.setKey(MyInput.JUMP, value);
        } else if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            MyInput.setKey(MyInput.LEFT, value);
        } else if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            MyInput.setKey(MyInput.RIGHT, value);
        } else if (keycode == Input.Keys.ENTER) {
            MyInput.setKey(MyInput.SHOOT, value);
        } else if (keycode == Input.Keys.ESCAPE) {
            MyInput.setKey(MyInput.BACK, value);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        MyInput.mouse.set(screenX, screenY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        MyInput.mouse.set(screenX, screenY);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        MyInput.mouse.set(screenX, screenY);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        MyInput.mouse.set(screenX, screenY);
        return true;
    }
}

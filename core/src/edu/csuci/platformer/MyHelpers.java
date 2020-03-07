package edu.csuci.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public final class MyHelpers {

    public static <T> T choose(T... choices) {
        return choices[MathUtils.random(choices.length - 1)];
    }

    public static TextureRegion[][] splitSpriteSheet(Texture spriteSheet, int rows, int cols) {
        int cellWidth = spriteSheet.getWidth() / cols;
        int cellHeight = spriteSheet.getHeight() / rows;
        return TextureRegion.split(spriteSheet, cellWidth, cellHeight);
    }

    public static Vector2 clampVector(Vector3 v,
                               Vector2 bottomLeft,
                               Vector2 topRight,
                               float marginX,
                               float marginY) {
        Vector2 res = new Vector2(v.x, v.y);

        res.x = Math.max(bottomLeft.x + marginX, res.x);
        res.y = Math.max(bottomLeft.y + marginY, res.y);

        res.x = Math.min(topRight.x - marginX, res.x);
        res.y = Math.min(topRight.y - marginY, res.y);

        return res;
    }

    public static void clampCamera(Viewport viewport, Vector2 bottomLeft, Vector2 topRight) {
        Vector2 pos = clampVector(
                viewport.getCamera().position,
                bottomLeft, topRight,
                viewport.getCamera().viewportWidth * 0.5f,
                viewport.getCamera().viewportHeight * 0.5f
        );
        viewport.getCamera().position.set(pos, 0);
    }

}

package edu.csuci.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public final class MyHelpers {

    public static <T> T choose(T... choices) {
        return choices[MathUtils.random(choices.length - 1)];
    }

    public static TextureRegion[][] splitSpriteSheet(Texture spriteSheet, int rows, int cols) {
        int cellWidth = spriteSheet.getWidth() / cols;
        int cellHeight = spriteSheet.getHeight() / rows;
        return TextureRegion.split(spriteSheet, cellWidth, cellHeight);
    }

}

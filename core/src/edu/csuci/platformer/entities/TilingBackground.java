package edu.csuci.platformer.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.csuci.platformer.Platformer;
import edu.csuci.platformer.managers.ContentManager;

public class TilingBackground {

    private boolean tileVertical;
    private boolean tileHorizontal;
    private Texture image;

    private float width;
    private float height;

    private float imageWidth;
    private float imageHeight;

    private boolean stretch;

    public TilingBackground(ContentManager.Image image, float width, float height) {
        this(image, true, true, width, height);
    }

    public TilingBackground(ContentManager.Image image,
                            boolean tileVertical,
                            boolean tileHorizontal,
                            float width,
                            float height) {
        this.image = Platformer.content.getTexture(image);
        this.tileVertical = tileVertical;
        this.tileHorizontal = tileHorizontal;
        this.width = width;
        this.height = height;
        imageWidth = this.image.getWidth();
        imageHeight = this.image.getHeight();
    }

    public void draw(SpriteBatch sb) {
        if (tileHorizontal && tileVertical) {
            tileBoth(sb);
        } else if (tileHorizontal) {
            tileHorizontal(sb);
        } else if (tileVertical) {
            tileVertical(sb);
        } else if (stretch) {
            sb.draw(this.image, 0, 0, width, height);
        } else {
            sb.draw(this.image, 0, 0);
        }
    }

    private void tileVertical(SpriteBatch sb) {
        int vTiles = (int) (height / imageHeight);
        for (int y = 0; y <= vTiles; y++) {
            if(stretch) {
                sb.draw(this.image, 0, y * imageHeight, width, imageHeight);
            } else {
                sb.draw(this.image, 0, y * imageHeight);
            }
        }
    }

    private void tileHorizontal(SpriteBatch sb) {
        int hTiles = (int) (width / imageWidth);
        for (int x = 0; x <= hTiles; x++) {
            if (stretch) {
                sb.draw(this.image, x * imageWidth, 0, imageWidth, height);
            } else {
                sb.draw(this.image, x * imageWidth, 0);
            }
        }
    }

    private void tileBoth(SpriteBatch sb) {
        int hTiles = (int) (width / imageWidth);
        int vTiles = (int) (height / imageHeight);
        for (int x = 0; x <= hTiles; x++) {
            for (int y = 0; y < vTiles; y++) {
                sb.draw(this.image, x * imageWidth, y * imageHeight);
            }
        }
    }

    public void setTileVertical(boolean tileVertical) {
        this.tileVertical = tileVertical;
    }

    public void setTileHorizontal(boolean tileHorizontal) {
        this.tileHorizontal = tileHorizontal;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setStretch(boolean stretch) {
        this.stretch = stretch;
    }
}

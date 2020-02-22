package edu.csuci.platformer.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class AbstractSpriteEntity extends AbstractEntity {

    protected float originX = 0;
    protected float originY = 0;

    protected float imageWidth = 0;
    protected float imageHeight = 0;

    protected float scaleX = 1;
    protected float scaleY = 1;

    protected float imageAngle = 0;

    private TextureRegion image;

    protected void setImage(TextureRegion image, boolean updateSize) {
        this.image = image;
        if(updateSize) {
            this.imageWidth = image.getRegionWidth();
            this.imageHeight = image.getRegionHeight();
        }
    }

    protected void setImage(TextureRegion image) {
        this.setImage(image, true);
    }

    public void setImage(Texture image, boolean updateSize) {
        this.setImage(new TextureRegion(image), updateSize);
    }

    public void setImage(Texture image) {
        this.setImage(image, true);
    }

    public void centerOrigin(boolean useBoundsSize) {
        if(useBoundsSize) {
            originX = getWidth() * 0.5f;
            originY = getHeight() * 0.5f;
        } else {
            originX = imageWidth * 0.5f;
            originY = imageHeight * 0.5f;
        }
    }

    public void centerOrigin() {
        this.centerOrigin(false);
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        sb.draw(
                image,
                getX(), getY(),
                originX, originY,
                imageWidth, imageHeight,
                scaleX, scaleY,
                imageAngle
        );
    }
}

package edu.csuci.platformer.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import edu.csuci.platformer.GameData;

public abstract class AbstractB2DSpriteEntity extends AbstractSpriteEntity {

    protected Body body;

    public Body getBody() {
        return this.body;
    }

    public void update(float dt) {
        setPosition(
                (body.getPosition().x * GameData.PIXELS_PER_METER) - (imageWidth * 0.5f),
                (body.getPosition().y * GameData.PIXELS_PER_METER) - (imageHeight * 0.5f)
        );
    }

    protected abstract void initB2DBody(World world, Vector2 spawnPoint);

}

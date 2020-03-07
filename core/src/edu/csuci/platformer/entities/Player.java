package edu.csuci.platformer.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import edu.csuci.platformer.GameData;
import edu.csuci.platformer.MyHelpers;
import edu.csuci.platformer.Platformer;
import edu.csuci.platformer.managers.ContentManager;
import edu.csuci.platformer.managers.input.MyInput;

public class Player extends AbstractB2DSpriteEntity {

    private Animation<TextureRegion> walkAnim;
    private TextureRegion idle;
    private TextureRegion jump;

    private float stateTime;

    private boolean onGround;

    public Player(World world, Vector2 spawnPoint) {
        super();
        initAnim();
        initB2DBody(world, spawnPoint);
        onGround = true;
        stateTime = 0;
    }

    private void initAnim() {
        TextureRegion[] frames = MyHelpers.splitSpriteSheet(
                Platformer.content.getTexture(ContentManager.Image.PINK_ALIEN_WALK),
                1, 2
        )[0];
        walkAnim = new Animation<>(GameData.PLAYER_ANIM_DURATION, frames);
        idle = Platformer.content.getTextureRegion(ContentManager.Image.PINK_ALIEN_STAND);
        jump = Platformer.content.getTextureRegion(ContentManager.Image.PINK_ALIEN_JUMP);
        setImage(idle, true);
    }

    @Override
    protected void initB2DBody(World world, Vector2 spawnPoint) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(spawnPoint);
        body = world.createBody(bodyDef);
        body.setUserData(this);

        float bodyHWidth = (imageWidth * GameData.METERS_PER_PIXEL * 0.75f) * 0.5f;
        float bodyHHeight = (imageHeight * GameData.METERS_PER_PIXEL * 0.9f) * 0.5f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(bodyHWidth, bodyHHeight);

        // Main Body Fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = GameData.PhysicsLayers.PLAYER;
        fixtureDef.filter.maskBits = GameData.PhysicsLayers.GROUND;
        Fixture mainBody = body.createFixture(fixtureDef);
        mainBody.setUserData(GameData.B2DUserData.PLAYER_BODY);

        shape.dispose();

        // Foot fixture
        shape = new PolygonShape();
        shape.setAsBox(
                bodyHWidth * 0.9f, GameData.PLAYER_FOOT_HEIGHT * GameData.METERS_PER_PIXEL,
                new Vector2(0, -bodyHHeight - (GameData.PLAYER_FOOT_HEIGHT * GameData.METERS_PER_PIXEL * 0.5f)),
                0
        );
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = GameData.PhysicsLayers.PLAYER;
        fixtureDef.filter.categoryBits = GameData.PhysicsLayers.GROUND;
        fixtureDef.isSensor = true;
        Fixture foot = body.createFixture(fixtureDef);
        foot.setUserData(GameData.B2DUserData.PLAYER_FOOT);

        shape.dispose();
    }

    public void handleInput() {
        if (MyInput.keyCheckPressed(MyInput.JUMP) && isOnGround()) {
            Platformer.content.playSound(ContentManager.SoundEffect.JUMP);
            body.applyForceToCenter(0, GameData.PLAYER_JUMP_FORCE, true);
        }
        final float yVel = body.getLinearVelocity().y;
        if (MyInput.keyCheck(MyInput.LEFT)) {
            body.setLinearVelocity(-GameData.PLAYER_SPEED, yVel);
        } else if (MyInput.keyCheck(MyInput.RIGHT)) {
            body.setLinearVelocity(GameData.PLAYER_SPEED, yVel);
            scaleX = 1;
        } else {
            body.setLinearVelocity(0, yVel);
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        final float xVel = body.getLinearVelocity().x;
        if (Float.compare(xVel, 0) > 0) {
            scaleX = 1;
        } else if (Float.compare(xVel, 0) < 0) {
            scaleX = -1;
        }
    }

    private void animate(float dt) {
        stateTime += dt;
        if (isOnGround()) {
            if (Float.compare(body.getLinearVelocity().x, 0) == 0) {
                setImage(idle);
            } else {
                TextureRegion frame = walkAnim.getKeyFrame(stateTime, true);
                setImage(frame);
            }
        } else {
            setImage(jump);
        }
        setSize(imageWidth, imageHeight);
        centerOrigin(true);
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        animate(dt);
        super.draw(dt, sb, sr);
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    @Override
    public void dispose() {

    }
}

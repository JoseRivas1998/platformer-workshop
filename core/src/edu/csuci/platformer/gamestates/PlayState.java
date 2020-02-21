package edu.csuci.platformer.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.csuci.platformer.GameData;
import edu.csuci.platformer.managers.GameStateManager;

public class PlayState extends AbstractGameState {

    private World world;
    private float accumulator;
    private Viewport b2dView;
    private Box2DDebugRenderer b2dRenderer;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void init() {
        initPhys();

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.fixedRotation = true;
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(10, 2);
        Body body = world.createBody(groundBodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5f, 0.5f);

        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.isSensor = false;
        groundFixtureDef.shape = shape;
        body.createFixture(groundFixtureDef);
        shape.dispose();

        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = BodyDef.BodyType.DynamicBody;
        boxBodyDef.fixedRotation = true;
        boxBodyDef.position.set(10, 10);
        Body box = world.createBody(boxBodyDef);

        shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f);

        FixtureDef boxFixtureDef = new FixtureDef();
        boxFixtureDef.isSensor = false;
        boxFixtureDef.shape = shape;
        box.createFixture(boxFixtureDef);
        shape.dispose();

    }

    private void initPhys() {
        world = new World(new Vector2(0, -9.8f), true);
        accumulator = 0;
        b2dView = new FitViewport(GameData.WORLD_WIDTH * GameData.METERS_PER_PIXEL,
                GameData.WORLD_HEIGHT * GameData.METERS_PER_PIXEL);
        b2dRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void handleInput(float dt) {

    }

    @Override
    public void update(float dt) {
        physicsStep(dt);
        b2dView.apply();
    }

    private void physicsStep(float dt) {
        accumulator += Math.min(0.25f, dt);
        while(accumulator >= GameData.TIME_STEP) {
            world.step(GameData.TIME_STEP, GameData.VELOCITY_ITERATIONS, GameData.POSITION_ITERATIONS);
            accumulator -= GameData.TIME_STEP;
        }
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        b2dRenderer.render(world, b2dView.getCamera().combined);
    }

    @Override
    public void resize(int width, int height) {
        b2dView.update(width, height, true);
    }

    @Override
    public void dispose() {
        world.dispose();
    }
}

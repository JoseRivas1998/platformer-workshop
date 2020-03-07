package edu.csuci.platformer.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.csuci.platformer.GameData;
import edu.csuci.platformer.MyHelpers;
import edu.csuci.platformer.entities.Level;
import edu.csuci.platformer.entities.Player;
import edu.csuci.platformer.entities.TilingBackground;
import edu.csuci.platformer.managers.ContentManager;
import edu.csuci.platformer.managers.GameStateManager;

public class PlayState extends AbstractGameState {

    private World world;
    private float accumulator;
    private Viewport b2dView;
    private Box2DDebugRenderer b2dRenderer;

    private Level map;
    private Viewport gameView;

    private Player player;

    private TilingBackground background;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void init() {
        initPhys();

        map = new Level(0, world);

        gameView = new FitViewport(GameData.WORLD_WIDTH, GameData.WORLD_HEIGHT);
        gameView.getCamera().position.set(
                GameData.WORLD_WIDTH * 0.5f,
                GameData.WORLD_HEIGHT * 0.5f,
                0
        );
        gameView.getCamera().update();

        player = new Player(world, map.getPlayerSpawnPosition());

        background = new TilingBackground(
                ContentManager.Image.LEVEL_BG,
                map.getTopRight().x,
                map.getTopRight().y
        );

    }

    private void initPhys() {
        world = new World(new Vector2(0, GameData.GRAVITY), true);
        world.setContactListener(new GameContactListener());
        accumulator = 0;
        b2dView = new FitViewport(GameData.WORLD_WIDTH * GameData.METERS_PER_PIXEL,
                GameData.WORLD_HEIGHT * GameData.METERS_PER_PIXEL);
        b2dRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void handleInput(float dt) {
        player.handleInput();
    }

    @Override
    public void update(float dt) {
        physicsStep(dt);
        player.update(dt);
        updateView();
    }

    private void updateView() {
        gameView.getCamera().position.set(
                new Vector2(player.getBody().getPosition()).scl(GameData.PIXELS_PER_METER),
                0
        );
        MyHelpers.clampCamera(gameView, Vector2.Zero, map.getTopRight());
        gameView.apply();
        b2dView.getCamera().position.set(
                gameView.getCamera().position.x * GameData.METERS_PER_PIXEL,
                gameView.getCamera().position.y * GameData.METERS_PER_PIXEL,
                0
        );
        b2dView.apply();
    }

    private void physicsStep(float dt) {
        accumulator += Math.min(0.25f, dt);
        while (accumulator >= GameData.TIME_STEP) {
            world.step(GameData.TIME_STEP, GameData.VELOCITY_ITERATIONS, GameData.POSITION_ITERATIONS);
            accumulator -= GameData.TIME_STEP;
        }
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        sb.setProjectionMatrix(gameView.getCamera().combined);
        background.draw(sb);
        sb.end();

        map.render(gameView);

        sb.begin();
        sb.setProjectionMatrix(gameView.getCamera().combined);
        player.draw(dt, sb, sr);
        sb.end();
        if (GameData.DEBUG) {
            b2dRenderer.render(world, b2dView.getCamera().combined);
        }
    }

    @Override
    public void resize(int width, int height) {
        b2dView.update(width, height, true);
    }

    @Override
    public void dispose() {
        world.dispose();
    }

    class GameContactListener implements ContactListener {

        private int playerFootContacts = 0;

        private boolean isUserData(Fixture fixture, GameData.B2DUserData data) {
            return fixture.getUserData() != null && fixture.getUserData().equals(data);
        }

        @Override
        public void beginContact(Contact contact) {
            checkPlayerGround(contact, true);
        }

        @Override
        public void endContact(Contact contact) {
            checkPlayerGround(contact, false);
        }

        private void checkPlayerGround(Contact contact, boolean entering) {
            if (isUserData(contact.getFixtureA(), GameData.B2DUserData.PLAYER_FOOT) ||
                    isUserData(contact.getFixtureB(), GameData.B2DUserData.PLAYER_FOOT)) {
                if (entering) {
                    playerFootContacts++;
                } else {
                    playerFootContacts--;
                }
            }
            player.setOnGround(playerFootContacts > 0);
        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }
    }

}

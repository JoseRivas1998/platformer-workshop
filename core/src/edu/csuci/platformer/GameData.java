package edu.csuci.platformer;

public class GameData {

    // GRAPHICS
    public static final int WORLD_WIDTH = 1280;
    public static final int WORLD_HEIGHT = 720;

    public static final float PIXELS_PER_METER = 70f;
    public static final float METERS_PER_PIXEL = 1f / PIXELS_PER_METER;

    public static final boolean DEBUG = true;

    // Physics Constants
    public static final float WORLD_STEP_PER_SECOND = 60f;
    public static final float TIME_STEP = 1f / WORLD_STEP_PER_SECOND;
    public static final float GRAVITY = -9.8f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 2;

    public static final class PhysicsLayers {
        public static final short GROUND = 1 << 0;
        public static final short PLAYER = 1 << 1;
    }

    public enum B2DUserData {
        PLAYER_BODY,
        PLAYER_FOOT
    }

    public static final float PLAYER_ANIM_DURATION = 0.15f; // s / frame
    public static final float PLAYER_FOOT_HEIGHT = 4f; // px
    public static final float PLAYER_SPEED = 5f; // m / s
    public static final float PLAYER_JUMP_FORCE = 500f; // N
    public static final float PLAYER_INVINCIBILITY = 0.33333f; // seconds
    public static final int PLAYER_MAX_HEALTH = 100;

}

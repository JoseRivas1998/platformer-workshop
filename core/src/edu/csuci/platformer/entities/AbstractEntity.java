package edu.csuci.platformer.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public abstract class AbstractEntity implements Disposable {

    private static int entityCount = 0;

    private Rectangle bounds;
    private Vector2 velocity;
    private int id;

    public AbstractEntity() {
        this.bounds = new Rectangle();
        this.velocity = new Vector2();
        this.id = ++AbstractEntity.entityCount;
    }

    public abstract void update(float dt);
    public abstract void draw(float dt, SpriteBatch sb, ShapeRenderer sr);

    public float getX() {
        return this.bounds.x;
    }

    public float getY() {
        return this.bounds.y;
    }

    public Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }

    public void setX(float x) {
        this.bounds.x = x;
    }

    public void setY(float y) {
        this.bounds.y = y;
    }

    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    public void setPosition(Vector2 point) {
        setPosition(point.x, point.y);
    }

    public float getWidth() {
        return this.bounds.width;
    }

    public float getHeight() {
        return this.bounds.height;
    }

    public Vector2 getSize() {
        return new Vector2(getWidth(), getHeight());
    }

    public void setWidth(float width) {
        this.bounds.width = width;
    }

    public void setHeight(float height) {
        this.bounds.height = height;
    }

    public void setSize(float width, float height) {
        setWidth(width);
        setHeight(height);
    }

    public void setSize(Vector2 size) {
        setSize(size.x, size.y);
    }

    public float getCenterX() {
        return getX() + (getWidth() * 0.5f);
    }

    public float getCenterY() {
        return getY() + (getHeight() * 0.5f);
    }

    public Vector2 getCenter() {
        return new Vector2(getCenterX(), getCenterY());
    }

    public void setCenterX(float x) {
        setX(x - (getWidth() * 0.5f));
    }

    public void setCenterY(float y) {
        setY(y - (getHeight() * 0.5f));
    }

    public void setCenter(float x, float y) {
        setCenterX(x);
        setCenterY(y);
    }

    public void setCenter(Vector2 point) {
        setCenter(point.x, point.y);
    }

    public float getVelocityX() {
        return this.velocity.x;
    }

    public float getVelocityY() {
        return this.velocity.y;
    }

    public void setVelocityX(float x) {
        this.velocity.x = x;
    }

    public void setVelocityY(float y) {
        this.velocity.y = y;
    }

    public Vector2 getVelocity() {
        return new Vector2(this.velocity);
    }

    public void setVelocity(float x, float y) {
        this.velocity.set(x, y);
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }

    public void setVelocityPolar(float speed, float angleRadians) {
        setVelocityX(speed * MathUtils.cos(angleRadians));
        setVelocityY(speed * MathUtils.sin(angleRadians));
    }

    public void setVelocityDeg(float speed, float angleDeg) {
        setVelocityPolar(speed, angleDeg * MathUtils.degRad);
    }

    public float getDirection() {
        return MathUtils.atan2(getVelocityY(), getVelocityX());
    }

    public float getDirectionDegrees() {
        return getDirection() * MathUtils.radDeg;
    }

    public void setDirection(float directionRadians) {
        float speed = this.velocity.len();
        setVelocityPolar(speed, directionRadians);
    }

    public void setDirectionDeg(float directionDegrees) {
        setDirection(directionDegrees * MathUtils.degRad);
    }

    public float getSpeed() {
        return this.velocity.len();
    }

    public void setSpeed(float speed) {
        float angle = getDirection();
        setVelocityPolar(speed, angle);
    }

    public void applyVelocity(float dt) {
        this.bounds.x += this.velocity.x * dt;
        this.bounds.y += this.velocity.y * dt;
    }

    public boolean collidingWith(Rectangle rectangle) {
        return this.bounds.overlaps(rectangle);
    }

    public boolean collidingWith(AbstractEntity e) {
        return collidingWith(e.bounds);
    }

}

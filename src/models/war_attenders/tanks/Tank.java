package models.war_attenders.tanks;

import models.war_attenders.WarAttender;
import models.war_attenders.soldiers.Soldier;
import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import java.nio.file.FileSystemNotFoundException;

public abstract class Tank extends WarAttender {
    Image turret;
    float turret_rotate_speed;
    float backwards_speed;
    private boolean decelerate;
    private int TANK_WIDTH_HALF, TANK_HEIGHT_HALF, TURRET_WIDTH_HALF, TURRET_HEIGHT_HALF;

    private boolean switcher;

    // each tank has an acceleration and a deceleration
    public float acceleration_factor;   // number between [0 and 1] -> the smaller the faster the acceleration
    public float deceleration_factor;   // number between [0 and 1] -> the smaller the faster the deceleration

    public Tank(Vector2f startPos, boolean isHostile) {
        super(startPos, isHostile);
    }

    void init() {
        TANK_WIDTH_HALF = base_image.getWidth() / 2;
        TANK_HEIGHT_HALF = base_image.getHeight() / 2;
        TURRET_WIDTH_HALF = turret.getWidth() / 2;
        TURRET_HEIGHT_HALF = turret.getHeight() / 2;
    }

    @Override
    public void update(GameContainer gc, int deltaTime) {
        super.update(gc, deltaTime);

        currentTime += deltaTime;
        if(currentTime >= invincible_animation_time_switch){
            if(switcher) switcher = false;
                    else switcher = true;
            currentTime = 0;
        }

        if (show_accessible_animation) {
            accessible_animation.update(deltaTime);
        }
        if (decelerate) {
            decelerate(deltaTime);
        }
    }

    @Override
    public void draw(Graphics graphics) {
        super.draw(graphics);
        if (isInvincible()) {
            if(!switcher){
                base_image.drawFlash(position.x - TANK_WIDTH_HALF, position.y - TANK_HEIGHT_HALF);
            } else {
                base_image.draw(position.x - TANK_WIDTH_HALF, position.y - TANK_HEIGHT_HALF);
                turret.draw(position.x - TURRET_WIDTH_HALF, position.y - TURRET_HEIGHT_HALF);
            }
        } else {
            base_image.draw(position.x - TANK_WIDTH_HALF, position.y - TANK_HEIGHT_HALF);
            turret.draw(position.x - TURRET_WIDTH_HALF, position.y - TURRET_HEIGHT_HALF);
        }


        if (show_accessible_animation) {
            accessible_animation.draw(position.x - (TANK_WIDTH_HALF * 2) / 4, position.y - (TANK_HEIGHT_HALF * 2) + 17);
        }
    }

    public void accelerate(int deltaTime) {
        if (current_speed < max_speed) {
            current_speed += acceleration_factor * deltaTime;
        } else {
            current_speed = max_speed;  // cap the max speed
        }
        calculateMovementVector(deltaTime, Direction.FORWARD);
        position.add(dir);
    }

    public void decelerate(int deltaTime) {
        if (current_speed > 0.f) {  // 0.01f and not 0.f because it will take longer to reach 0.f completely!
            current_speed -= deceleration_factor * deltaTime;
        } else {
            current_speed = 0.f;
            decelerate = false;
            isMoving = false;
        }
        calculateMovementVector(deltaTime, Direction.FORWARD);
        position.add(dir);
    }

    public void startDeceleration() {
        decelerate = true;
    }

    public void cancelDeceleration() {
        decelerate = false;
    }

    public void moveBackwards(int deltaTime) {
        if (decelerate) { // if tank is still decelerating, but player wants to move backwards, decelerate harder
            current_speed -= acceleration_factor * deltaTime;
        }
        calculateMovementVector(deltaTime, Direction.BACKWARDS);
        position.add(dir);
    }

    public Vector2f calculateSoldierSpawnPosition() {
        // set player 10 pixels behind the tank
        final float DISTANCE = 10;
        final float SPAWN_X = 0;
        final float SPAWN_Y = base_image.getHeight() / 2 + DISTANCE;

        float xVal = (float) (Math.cos(((base_image.getRotation()) * Math.PI) / 180) * SPAWN_X
                + -Math.sin(((base_image.getRotation()) * Math.PI) / 180) * SPAWN_Y);
        float yVal = (float) (Math.sin(((base_image.getRotation()) * Math.PI) / 180) * SPAWN_X
                + Math.cos(((base_image.getRotation()) * Math.PI) / 180) * SPAWN_Y);
        return new Vector2f(xVal + position.x, yVal + position.y);
    }

    public void rotateTurret(RotateDirection r, int deltaTime) {
        switch (r) {
            case ROTATE_DIRECTION_LEFT:
                turret.rotate(-turret_rotate_speed * deltaTime);
                break;
            case ROTATE_DIRECTION_RIGHT:
                turret.rotate(turret_rotate_speed * deltaTime);
                break;
        }
    }

    @Override
    public float getRotation() {
        return base_image.getRotation();
    }

    public void setCurrentSpeed(Direction direction) {
        if (direction == Direction.FORWARD) {
            this.current_speed = 0.f;
        } else {
            this.current_speed = backwards_speed;
        }
    }

    @Override
    public void rotate(RotateDirection r, int deltaTime) {
        float degree;
        switch (r) {
            case ROTATE_DIRECTION_LEFT:
                degree = -rotate_speed * deltaTime;
                base_image.rotate(degree);
                turret.rotate(degree);
                break;
            case ROTATE_DIRECTION_RIGHT:
                degree = rotate_speed * deltaTime;
                base_image.rotate(degree);
                turret.rotate(degree);
                break;
        }
    }

    @Override
    public void onCollision(WarAttender enemy) {
        if (enemy instanceof Tank) {  // enemy is a tank
            // block movement as long as there's collision
            blockMovement();
        } else if (enemy instanceof Soldier) {   // enemy is a soldier (bad for him)
            // kill soldier
        }
        // plane instanceof is not needed, nothing happens there
    }

    @Override
    public void blockMovement() {
        position.sub(dir);  // set the position on last position before the collision
        collisionModel.update(base_image.getRotation());    // update collision model
        current_speed = 0.f;    // set the speed to zero (stop moving on collision)
    }

    /*
    let the tank bounce a few meters back from its current position
     */
    private void bounceBack() {
        // TODO
    }


    @Override
    public void setRotation(float angle) {
        turret.setRotation(angle);
    }

    /*
    The standard method a tank uses to shoot. Should be overwritten if a tank shoots another way.
     */
    @Override
    public void shoot() {
        if (canShoot()) {
            current_reload_time = 0;    // reset the reload time when a shot is fired
            float rotation_angle = turret.getRotation();
            float spawnX = position.x;
            float spawnY = position.y;
            spawnX += -Math.sin(((rotation_angle) * Math.PI) / 180) * -30.f;
            spawnY += Math.cos(((rotation_angle) * Math.PI) / 180) * -30.f;
            Vector2f bullet_spawn = new Vector2f(spawnX, spawnY);

            float xVal = (float) Math.sin(rotation_angle * Math.PI / 180);
            float yVal = (float) -Math.cos(rotation_angle * Math.PI / 180);
            Vector2f bullet_dir = new Vector2f(xVal, yVal);

            Bullet bullet = new Bullet(bullet_spawn, bullet_dir, rotation_angle);
            bullet_list.add(bullet);
        }
    }
}

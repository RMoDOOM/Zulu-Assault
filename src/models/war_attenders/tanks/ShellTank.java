package models.war_attenders.tanks;

import models.CollisionModel;
import models.weapons.Goliath;
import models.weapons.Shell;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.opengl.Texture;

public class ShellTank extends Tank {

    private static Texture shell_tank_hostile_texture, shell_tank_friendly_texture,
            shell_tank_hostile_turret_texture, shell_tank_friendly_turret_texture;

    // attributes
    private static final float ARMOR = 60.f;
    private static final float ROTATE_SPEED_PLAYER = 0.2f, ROTATE_SPEED_BOT = 0.05f;
    private static final float MAX_SPEED_PLAYER = 0.15f, MAX_SPEED_BOT = 0.05f;
    private static final float ACCELERATION_FACTOR = 0.00005f, DECELERATION_FACTOR = 0.0009f;

    public ShellTank(Vector2f startPos, boolean isHostile, boolean isDrivable) {
        super(startPos, isHostile, isDrivable);

        weapons.add(new Shell(isDrivable));   // WEAPON_1
        weapons.add(new Goliath(isDrivable));   // WEAPON_2

        // LOAD TEXTURES
        try {
            if (isHostile) {
                if (shell_tank_hostile_texture == null) {
                    shell_tank_hostile_texture = new Image("assets/war_attenders/tanks/shell_tank_hostile.png")
                            .getTexture();
                }
                base_image = new Image(shell_tank_hostile_texture);
                if (shell_tank_hostile_turret_texture == null) {
                    shell_tank_hostile_turret_texture =
                            new Image("assets/war_attenders/tanks/shell_tank_hostile_turret.png")
                                    .getTexture();
                }
                turret = new Image(shell_tank_hostile_turret_texture);
            } else {    // friendly
                if (shell_tank_friendly_texture == null) {
                    shell_tank_friendly_texture = new Image("assets/war_attenders/tanks/shell_tank_friendly.png")
                            .getTexture();
                }
                base_image = new Image(shell_tank_friendly_texture);
                if (shell_tank_friendly_turret_texture == null) {
                    shell_tank_friendly_turret_texture =
                            new Image("assets/war_attenders/tanks/shell_tank_friendly_turret.png")
                                    .getTexture();
                }
                turret = new Image(shell_tank_friendly_turret_texture);
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }

        collisionModel = new CollisionModel(position, base_image.getWidth(), base_image.getHeight());
        super.init();
    }

    @Override
    protected float getBaseRotateSpeed() {
        return isDrivable ? ROTATE_SPEED_PLAYER : ROTATE_SPEED_BOT;
    }

    @Override
    public float getMaxSpeed() {
        return isDrivable ? MAX_SPEED_PLAYER : MAX_SPEED_BOT;
    }

    @Override
    protected float getAccelerationFactor() {
        return ACCELERATION_FACTOR;
    }

    @Override
    protected float getDecelerationFactor() {
        return DECELERATION_FACTOR;
    }

    @Override
    public void changeHealth(float amount) {
        super.changeHealth(amount, ARMOR);
    }
}

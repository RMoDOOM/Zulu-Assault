package models.war_attenders.tanks;

import models.CollisionModel;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

public class AgileTank extends Tank {


    public AgileTank(Vector2f startPos, boolean isHostile) {
        super(startPos, isHostile);

        // individual AgileTank attributes
        max_speed = 0.3f;
        acceleration_factor = 0.0005f;
        deceleration_factor = 0.995f;
        rotate_speed = 0.15f;
        turret_rotate_speed = 0.2f;

        try {
            base_image = new Image("assets/tanks/agile_tank.png");
            turret = new Image("assets/tanks/agile_tank_turret.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
        collisionModel = new CollisionModel(position, base_image.getWidth(), base_image.getHeight());
    }
}

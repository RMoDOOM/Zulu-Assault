package models.war_attenders.robots;

import models.weapons.DoublePlasma;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class PlasmaRobot extends Robot {

    public PlasmaRobot(Vector2f startPos, boolean isHostile) {
        super(startPos, isHostile);

        // individual PlasmaRobot attributes for bots
        max_health = 100;
        current_health = max_health;
        armor = 75;
        max_speed = 0.15f;
        current_speed = max_speed;
        rotate_speed = 0.25f;
        turret_rotate_speed = 0.5f;

        init();
    }

    public PlasmaRobot(Vector2f startPos, boolean isHostile, boolean isDrivable) {
        super(startPos, isHostile, isDrivable);

        // individual PlasmaRobot attributes for human player
        max_health = 100;
        current_health = max_health;
        armor = 75;
        max_speed = 0.15f;
        current_speed = max_speed;
        rotate_speed = 0.25f;
        turret_rotate_speed = 0.5f;

        init();
    }

    public void init(){
        weapons.add(new DoublePlasma(isDrivable));

        try {
            base_image = new Image("assets/war_attenders/robots/plasma_robot.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
        super.init();
    }
}

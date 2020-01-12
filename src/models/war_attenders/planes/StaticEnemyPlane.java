package models.war_attenders.planes;

import models.StaticWarAttender;
import org.newdawn.slick.geom.Vector2f;

public class StaticEnemyPlane extends StaticWarAttender {

    public StaticEnemyPlane(Vector2f startPos, boolean isHostile, Vector2f[] collision_tiles, Vector2f[] replacement_tiles) {
        super(startPos, isHostile, collision_tiles);
        this.replacement_tiles = replacement_tiles;

        max_health = 100.f;
        armor = 10.f;
        current_health = max_health;
        scoreValue = 200;

        health_bar_position.x = position.x - 27.5f;
        health_bar_position.y = position.y - 35.f;
    }

    @Override
    public void fireWeapon(WeaponType weapon) {

    }

    @Override
    public void setRotation(float degree) {

    }
}

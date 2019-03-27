package models.war_attenders.windmills;

import models.animations.SmokeAnimation;
import models.war_attenders.MovableWarAttender;
import models.war_attenders.WarAttender;
import models.weapons.Weapon;
import org.lwjgl.Sys;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import java.util.Random;

public abstract class Windmill extends WarAttender {
    Image turret;
    float turret_rotate_speed;
    private int key;
    public final static int ARMOR = 10;
    public final static int HEALTH = 100;
    private SmokeAnimation smokeAnimation;
    private final int SMOKE_ANIMATION_FREQUENCY = 300;  // two per second
    private int smoke_animation_timer;
    private Random random;

    public Windmill(Vector2f startPos, boolean isHostile, int key) {
        super(startPos, isHostile);
        random = new Random();
        smokeAnimation = new SmokeAnimation(3);
        this.key = key;
        max_health = 100;
        armor = 10;
        current_health = max_health;
        turret_rotate_speed = 0.2f;
    }

    public void init() {
        super.init();
    }

    @Override
    public void update(GameContainer gc, int deltaTime) {
        super.update(gc, deltaTime);
        smokeAnimation.update(deltaTime);
        if(current_health < max_health / 2){
            smoke_animation_timer += deltaTime;
            if(smoke_animation_timer > SMOKE_ANIMATION_FREQUENCY){
                smoke_animation_timer = 0;
                int rotation = random.nextInt(360);
                float xVal = (float) (position.x + 20 + -Math.sin(((rotation) * Math.PI) / 180) * -30.f);
                float yVal = (float) (position.y + 20 + Math.cos(((rotation) * Math.PI) / 180) * -30.f);
                smokeAnimation.play(xVal, yVal, rotation);
            }
        }
    }

    @Override
    public void draw(Graphics graphics) {
        super.draw(graphics);
        smokeAnimation.draw();
    }

    @Override
    public void fireWeapon(MovableWarAttender.WeaponType weaponType) {
        Weapon weapon = null;
        switch (weaponType) {
            case WEAPON_1:
                weapon = getWeapon(MovableWarAttender.WeaponType.WEAPON_1);
                break;
            case WEAPON_2:
                weapon = getWeapon(MovableWarAttender.WeaponType.WEAPON_2);
                break;
            case MEGA_PULSE:
                weapon = getWeapon(MovableWarAttender.WeaponType.MEGA_PULSE);
                break;
        }
        if (weapon == null) return;  // does not have a WEAPON_2, so return
        weapon.fire(position.x + 20, position.y + 20, turret.getRotation());
    }

    @Override
    public void setRotation(float angle) {
        turret.setRotation(angle);
    }

    public int getKey(){
        return key;
    }
}
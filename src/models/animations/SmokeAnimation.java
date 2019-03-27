package models.animations;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class SmokeAnimation extends AbstractAnimation {
    public SmokeAnimation(final int BUFFER_SIZE) {
        super(BUFFER_SIZE);
    }

    @Override
    public void addNewInstance() throws SlickException {
        Image smoke_animation_image = new Image("assets/animations/smoke.png");
        Animation smoke_animation = new Animation(false);
        int IMAGE_COUNT = 8;
        int x = 0;
        for (int idx = 0; idx < IMAGE_COUNT; ++idx) {
            smoke_animation.addFrame(smoke_animation_image.getSubImage(x, 0, 40, 34), 80);
            x += 40;
        }
        smoke_animation.setLooping(false);
        buffered_instances.add(new AnimationInstance(smoke_animation));
    }
}
package models.animations;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class UziDamageAnimation extends AbstractAnimation {

    final int ANIMATION_WIDTH_HALF, ANIMATION_HEIGHT_HALF;

    public UziDamageAnimation(final int BUFFER_SIZE) {
        super(BUFFER_SIZE);
        ANIMATION_WIDTH_HALF = buffered_instances.get(0).animation.getCurrentFrame().getWidth() / 2;
        ANIMATION_HEIGHT_HALF = buffered_instances.get(0).animation.getCurrentFrame().getHeight() / 2;
    }
    @Override
    public void addNewInstance() throws SlickException {
        Image smoke_animation_image = new Image("assets/animations/bullet_damage.png");
        Animation damage_animation = new Animation(false);
        int IMAGE_COUNT = 17;
        int x = 0;
        for (int idx = 0; idx < IMAGE_COUNT; ++idx) {
            damage_animation.addFrame(smoke_animation_image.getSubImage(x, 0, 13, 89), 35);
            x += 13;
        }
        damage_animation.setLooping(false);
        buffered_instances.add(new AnimationInstance(damage_animation));
    }

    @Override
    public void play(float xPos, float yPos, float rotation) {
        final float Y_OFFSET = -50; // so it looks like it continues the bullet hit
        float xVal = (float) Math.cos(((rotation) * Math.PI) / 180) * Y_OFFSET;
        float yVal = (float) Math.sin(((rotation) * Math.PI) / 180) * Y_OFFSET;

        super.play(xPos - xVal, yPos - yVal, rotation);
    }
}

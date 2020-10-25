package level_editor.toolbars.elements;

import level_editor.toolbars.Toolbar;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Checkbox extends ToolbarElement {

    private int innerCheckboxSize, outerCheckboxSize;
    private int innerCheckboxX, innerCheckboxY;
    private int outerCheckboxY;

    private boolean isChecked;

    private static Color lightGreyWithOpacity = new Color(0.7f, 0.7f, 0.7f, 0.5f);

    public Checkbox(String name, int startX, int startY, int width, int height) {
        super(name, startX, startY, width, height);
        this.innerCheckboxSize = (int) (height / 2.5f);
        this.outerCheckboxSize = (int) (height / 1.5f);
        this.outerCheckboxY = (int) (yPos + height / 2.f - outerCheckboxSize / 2.f);
        this.innerCheckboxX = (int) (xPos + outerCheckboxSize / 2.f - innerCheckboxSize / 2.f) + 1; // +1 = line width
        this.innerCheckboxY = (int) (outerCheckboxY + outerCheckboxSize / 2.f - innerCheckboxSize / 2.f) + 1;

        this.xNameString = (xPos + outerCheckboxSize + Toolbar.Props.DEFAULT_MARGIN);
        this.yNameString = (int) (yPos + height / 2.f - string_drawer.getHeight(name) / 2.f);

    }

    @Override
    public void draw(Graphics graphics) {
        // draw outline
        //graphics.drawRect(xPos, yPos, width, height);

        // draw the check rect
        graphics.setColor(Color.lightGray);
        if (isChecked) {
            graphics.fillRect(innerCheckboxX,
                    innerCheckboxY,
                    innerCheckboxSize,
                    innerCheckboxSize);
        }
        graphics.drawRect(xPos, outerCheckboxY, outerCheckboxSize, outerCheckboxSize);

        string_drawer.drawString(xNameString, yNameString, name, Color.lightGray);
    }

    public void drawWithOpacity(Graphics graphics) {
        graphics.setColor(lightGreyWithOpacity);
        graphics.drawRect(xPos, outerCheckboxY, outerCheckboxSize, outerCheckboxSize);
        string_drawer.drawString(xNameString, yNameString, name, lightGreyWithOpacity);
    }

    @Override
    public void update(GameContainer gc) {

    }

    public void toggle() {
        isChecked = !isChecked;
    }

    public boolean isChecked() {
        return isChecked;
    }

}
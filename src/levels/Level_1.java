package levels;

import logic.Camera;
import logic.CollisionHandler;
import logic.KeyInputHandler;
import models.war_attenders.WarAttender;
import models.war_attenders.tanks.AgileTank;
import models.war_attenders.tanks.AgileTank_v2;
import models.war_attenders.tanks.MediumTank;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;
import player.Player;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

public class Level_1 extends BasicGame {
    private Player player;
    private TiledMap map;
    private ArrayList<WarAttender> level_war_attenders;
    private KeyInputHandler keyInputHandler;
    private CollisionHandler collisionHandler;
    private Camera camera;

    public Level_1(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        // SETUP ENEMY WAR ATTENDERS
        level_war_attenders = new ArrayList<>();
        WarAttender enemy_tank_1 = new MediumTank(new Vector2f(100.f, 100.f), true);
        level_war_attenders.add(enemy_tank_1);

        // SETUP PLAYER'S DRIVABLE WAR ATTENDERS
        WarAttender player_drivable_tank_1 = new MediumTank(new Vector2f(700.f, 300.f), false);
        level_war_attenders.add(player_drivable_tank_1);

        // SETUP THE PLAYER START POSITION
        Vector2f playerStartPos = new Vector2f(gameContainer.getWidth() / 2, gameContainer.getHeight() / 2);

        // SETUP THE PLAYER'S VEHICLE OR SOLDIER
        WarAttender tank = new AgileTank(playerStartPos, false);
        //WarAttender soldier = new PlayerSoldier(gameContainer.getWidth() / 2, gameContainer.getHeight() / 2, false);
        player = new Player(tank);

        // DEFINE THE MAP
        map = new TiledMap("assets/maps/level_1.tmx");

        camera = new Camera(gameContainer, map);
        keyInputHandler = new KeyInputHandler(player, level_war_attenders);     // handle key inputs
        collisionHandler = new CollisionHandler(player, level_war_attenders);    // handle collisions
    }

    @Override
    public void update(GameContainer gameContainer, int deltaTime) {
        player.update(gameContainer, deltaTime);
        for(WarAttender warAttender : level_war_attenders){
            warAttender.update(gameContainer, deltaTime);
        }
        keyInputHandler.update(gameContainer, deltaTime);
        collisionHandler.update(gameContainer, deltaTime);
        camera.centerOn(player.getWarAttender().position.x, player.getWarAttender().position.y);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        camera.drawMap();
        camera.translateGraphics();
        player.draw(graphics);
        for(WarAttender warAttender : level_war_attenders){
            warAttender.draw(graphics);
        }
    }
}

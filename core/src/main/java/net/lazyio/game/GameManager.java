package net.lazyio.game;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import net.lazyio.engine.EntityManager;
import net.lazyio.engine.util.PointMapObject;
import net.lazyio.game.entity.Enemy;
import net.lazyio.game.entity.NonTicking;
import net.lazyio.game.entity.Player;
import net.lazyio.game.util.Tags;

public class GameManager {

    private final EntityManager manager;

    private Player player;
    private Enemy enemy;

    public GameManager() {
        this.manager = new EntityManager(16);
    }

    public void createEntitiesFromMapObjects(MapObjects mapObjects) {
        PointMapObject spawn = (PointMapObject) mapObjects.get("player_spawn");
        this.player = EntityFactory.createPlayer(this.manager.getWorld(), new Vector2(spawn.x, spawn.y));

        mapObjects.forEach(obj -> {
            if (obj instanceof RectangleMapObject) {
                EntityFactory.createTiledRectObj(this.manager.getWorld(), (RectangleMapObject) obj);
            } else if (obj instanceof PointMapObject && obj.getName().equals(Tags.ENEMY)) {
                this.enemy = EntityFactory.spawnEnemy(this.manager.getWorld(), (PointMapObject) obj);
            }
        });
    }

    public Player getPlayer() {
        return this.player;
    }

    public Enemy getEnemy() {
        return this.enemy;
    }

    public EntityManager getManager() {
        return this.manager;
    }

    public void createNonMovableFromMapObjects(MapObjects trees) {
        trees.getByType(PointMapObject.class).forEach(pointMapObject -> new NonTicking(new Vector2(pointMapObject.x, pointMapObject.y), new Vector2(16, 16), Tags.NONE).build(this.manager.getWorld()));
    }
}

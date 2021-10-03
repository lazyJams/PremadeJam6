package net.lazyio.game;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.World;
import net.lazyio.engine.entity.Entity;
import net.lazyio.engine.util.PointMapObject;
import net.lazyio.game.entity.Enemy;
import net.lazyio.game.entity.NonTicking;
import net.lazyio.game.entity.Player;
import net.lazyio.game.util.Tags;

public class EntityFactory {

    public static Player createPlayer(World<Entity> world, Vector2 pos) {
        Player player = (Player) new Player(pos).build(world);
        player.setRespawnPoint(pos.x, pos.y);
        return player;
    }

    public static void createTiledRectObj(World<Entity> world, RectangleMapObject rect) {
        String tag = rect.getName() != null ? rect.getName() : Tags.NONE;
        /*if (EntityTags.AFFECT.equals(tag)) {
            new AffectableEntity(
                    new Vector2(rect.getRectangle().x, rect.getRectangle().y),
                    new Vector2(rect.getRectangle().width, rect.getRectangle().height)
            ).build(world);
        } else {*/
        new Entity(
                new Vector2(rect.getRectangle().x, rect.getRectangle().y),
                new Vector2(rect.getRectangle().width, rect.getRectangle().height),
                tag
        ).build(world);
        /*}*/
    }

    public static Enemy spawnEnemy(World<Entity> world, PointMapObject point) {
        return (Enemy) new Enemy(new Vector2(point.x, point.y)).build(world);
    }
}

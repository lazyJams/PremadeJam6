package net.lazyio.game.util;

import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import net.lazyio.engine.entity.Entity;

public class GameCollisions {

    public static final CollisionFilter playerFilter = GameCollisions::playerResponse;

    public static Response playerResponse(Item<Entity> item, Item<Entity> other) {
        if(other.userData.tag.equals(Tags.ENEMY)
            || other.userData.tag.equals(Tags.TREE)) return Response.cross;
        return Response.slide;
    }

    public static final CollisionFilter enemyFilter = GameCollisions::enemyResponse;

    public static Response enemyResponse(Item<Entity> item, Item<Entity> other) {
        if(other.userData.tag.equals(Tags.PLAYER)) return Response.cross;
        return Response.slide;
    }
}

package net.lazyio.engine.util;

import com.badlogic.gdx.math.Rectangle;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.World;
import net.lazyio.engine.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class JBumpUtils {

    public static List<Entity> getTagInArea(World<Entity> world, String tag, Rectangle area) {
        List<Entity> inArea = new ArrayList<>();
        world.getItems()
                .stream().map(item -> ((Entity) item.userData))
                .filter(e -> e.tag.equals(tag))
                .forEach(i -> {
                    Rectangle itemRect = new Rectangle(i.pos.x, i.pos.y, i.size.x, i.size.y);
                    if(itemRect.overlaps(area)){
                        inArea.add(i);
                    }
                });
        return inArea;
    }

    private static Rectangle gdxRect(Rect rectangle) {
        return new Rectangle(rectangle.x, rectangle.y, rectangle.w, rectangle.h);
    }
}

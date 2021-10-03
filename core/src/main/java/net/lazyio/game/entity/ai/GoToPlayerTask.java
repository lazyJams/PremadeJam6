package net.lazyio.game.entity.ai;

import com.badlogic.gdx.math.Rectangle;
import net.lazyio.engine.ai.ITask;
import net.lazyio.engine.ai.TaskResult;
import net.lazyio.engine.entity.Entity;
import net.lazyio.engine.util.JBumpUtils;
import net.lazyio.game.util.Tags;

import java.util.List;

public class GoToPlayerTask implements ITask {

    private float taskTime;
    private Entity holder;

    public GoToPlayerTask(Entity holder, float taskTime) {
        this.holder = holder;
        this.taskTime = taskTime;
    }

    @Override
    public TaskResult doTask(float dt) {
        List<Entity> tagInArea = JBumpUtils.getTagInArea(this.holder.world, Tags.PLAYER, new Rectangle(
                this.holder.pos.x + (this.holder.size.x / 2) - 50f,
                this.holder.pos.y + (this.holder.size.y / 2) - 50f,
                100f,
                100f
        ));
        System.out.println(tagInArea);
        if (tagInArea.isEmpty()) {
            return TaskResult.PASS;
        } else {
            this.holder.pos.set(tagInArea.get(0).pos.x, this.holder.pos.y);
            return TaskResult.LOOP;
        }
    }
}

package net.lazyio.engine.ai.tasks;

import com.badlogic.gdx.math.MathUtils;
import net.lazyio.engine.ai.ITask;
import net.lazyio.engine.ai.TaskResult;
import net.lazyio.engine.entity.Entity;

public class MoveRandomlyTask implements ITask {

    private final float taskTime;
    private final Entity holder;

    private float currentTime;

    private float dir;

    public MoveRandomlyTask(Entity holder, float taskTime) {
        this.holder = holder;
        this.taskTime = taskTime;

        this.dir = MathUtils.random(-1f, 1f) > 0f ? 1f : -1f;
    }

    @Override
    public TaskResult doTask(float dt) {
        if (this.currentTime < this.taskTime) {
            this.currentTime += dt;
            this.holder.vel.x = this.dir;
            return TaskResult.PASS;
        } else {
            this.dir = this.dir > 0f ? -1f : 1f;
            this.currentTime = 0f;
        }
        return TaskResult.LOOP;
    }
}

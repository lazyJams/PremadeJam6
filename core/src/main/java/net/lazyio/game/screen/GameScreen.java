package net.lazyio.game.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gempukku.libgdx.lib.camera2d.FocusCameraController;
import com.gempukku.libgdx.lib.camera2d.constraint.LockedToCameraConstraint;
import com.gempukku.libgdx.lib.camera2d.constraint.SceneCameraConstraint;
import com.gempukku.libgdx.lib.camera2d.focus.EntityFocus;
import net.lazyio.engine.util.fixes.FixedTmxLoader;
import net.lazyio.game.Assets;
import net.lazyio.game.Game;
import net.lazyio.game.GameManager;
import space.earlygrey.shapedrawer.ShapeDrawer;

import static com.badlogic.gdx.Input.Keys.L;
import static net.lazyio.engine.util.gdx.*;

public class GameScreen extends ScreenAdapter {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRenderer;

    private FocusCameraController cameraController;
    private ShapeDrawer shapeDrawer;

    private GameManager manager = new GameManager();

    @Override
    public void show() {
        this.batch = new SpriteBatch();
        this.camera = createOrthoCam(300f);

        TiledMap map = new FixedTmxLoader().load("maps/tutorial.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(map, this.batch);

        this.manager.createEntitiesFromMapObjects(map.getLayers().get("Objects").getObjects());
        //this.manager.createNonMovableFromMapObjects(map.getLayers().get("Trees").getObjects());

        this.cameraController = new FocusCameraController(
                this.camera,
                new EntityFocus((p) -> this.manager.getPlayer().pos),
                new LockedToCameraConstraint(new Vector2(0.5f, .5f)),
                new SceneCameraConstraint(new Rectangle(0f, 0f, 50f * 16, 50f * 16))
        );

        this.shapeDrawer = new ShapeDrawer(this.batch, new TextureRegion(Assets.shapeDrawer.get()));
    }

    private void update(float delta) {
        this.manager.getManager().tick(delta);
        Game.INST.tweenMgr.update(delta);
        this.cameraController.update(delta);
    }

    @Override
    public void render(float delta) {
        this.update(delta);
        clear(Color.SKY);

        this.mapRenderer.setView(this.camera);
        this.mapRenderer.render();

        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();
        this.manager.getManager().render(this.batch, delta);
        if (isKeyPressed(L)) this.drawDebugLines();
        this.batch.end();
    }

    private void drawDebugLines() {
        this.shapeDrawer.setColor(Color.GREEN);
        this.manager.getManager().getWorld().getRects().forEach(rect -> this.shapeDrawer.rectangle(rect.x, rect.y, rect.w, rect.h));
        this.shapeDrawer.setColor(Color.RED);
        this.shapeDrawer.rectangle(new Rectangle(
                this.manager.getEnemy().pos.x + (this.manager.getEnemy().size.x / 2) - 50f,
                this.manager.getEnemy().pos.y + (this.manager.getEnemy().size.y / 2) - 50f,
                100f,
                100f));
    }

    @Override
    public void resize(int width, int height) {
        updateCamera(this.camera, width, height, 300f);
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.mapRenderer.dispose();
    }
}

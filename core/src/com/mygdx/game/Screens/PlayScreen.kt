package com.mygdx.game.Screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.Controller.Controller
import com.mygdx.game.Main
import com.mygdx.game.Scenes.Hud
import com.mygdx.game.Sprites.Player
import com.mygdx.game.Tools.B2WorldCreator

class PlayScreen(var game: Main) : Screen {
    private var camera: OrthographicCamera = OrthographicCamera()
    private var viewport: Viewport
    private var hud: Hud
    val player: Player
    private val mapLoader: TmxMapLoader
    private val map: TiledMap
    private val renderer: OrthogonalTiledMapRenderer
    private val world: World
    private val b2dr: Box2DDebugRenderer
    private val controller: Controller

    fun handleInput(delta: Float) {
//        if (Gdx.input.isTouched()) {
//            camera.position.x += 100 * delta;
//        }
//        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
//            player.b2Body.applyLinearImpulse(new Vector2(0, 4f), player.b2Body.getWorldCenter(), true);
//
//        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && player.b2Body.getLinearVelocity().x <= 2)
//        if (Gdx.input.isTouched)
//            player.b2Body.applyLinearImpulse(Vector2(0.5f, 0f), player.b2Body.worldCenter, true)
        //
//        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && player.b2Body.getLinearVelocity().x >= -2)
//            player.b2Body.applyLinearImpulse(new Vector2(0, 0.1f), player.b2Body.getWorldCenter(), true);

        if (controller.isLeftPressed)
            player.b2Body.applyLinearImpulse(Vector2(-0.5f, 0f), player.b2Body.worldCenter, true)
        if (controller.isRightPressed)
            player.b2Body.applyLinearImpulse(Vector2(0.5f, 0f), player.b2Body.worldCenter, true)
        if (controller.isUpPressed)
            player.b2Body.applyLinearImpulse(Vector2(0f, 4f), player.b2Body.worldCenter, true)

    }

    fun update(delta: Float) {
        handleInput(delta)
        world.step(1 / 60f, 6, 2)
        camera.position.x = player.b2Body.position.x
        camera.update()
        renderer.setView(camera)
    }

    override fun show() {}
    override fun render(delta: Float) {
        update(delta)
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        game.batch.projectionMatrix = hud.stage.camera.combined
        hud.stage.draw()
        renderer.render()
        controller.draw()
        b2dr.render(world, camera.combined)
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        controller.resize(width, height)
    }

    override fun pause() {}
    override fun resume() {}
    override fun hide() {}
    override fun dispose() {
        map.dispose()
        renderer.dispose()
        world.dispose()
        b2dr.dispose()
        hud.dispose()
    }

    init {
        viewport = FitViewport(Main.V_WIDTH.toFloat(), Main.V_HEIGHT.toFloat(), camera)
        hud = Hud(game.batch)
        mapLoader = TmxMapLoader()
        map = mapLoader.load("lv1.tmx")
        renderer = OrthogonalTiledMapRenderer(map)
        camera.position[viewport.worldWidth / 2, viewport.worldHeight / 2] = 0f
        world = World(Vector2(0f, -10f), true)
        player = Player(world)
        b2dr = Box2DDebugRenderer()
        controller = Controller(game)
        B2WorldCreator(world, map)
    }
}
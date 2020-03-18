package com.mygdx.game.Screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.Controller.OnScreenController
import com.mygdx.game.MainGame
import com.mygdx.game.Scenes.Hud
import com.mygdx.game.Sprites.Player
import com.mygdx.game.Tools.B2WorldCreator
import com.mygdx.game.Tools.drawSprite

class PlayScreen(var game: MainGame) : Screen {
    private val mapLoader: TmxMapLoader = TmxMapLoader()
    private val tiledMap: TiledMap
    private val mapRenderer: OrthogonalTiledMapRenderer
    val textureAtlas: TextureAtlas = TextureAtlas("Dino.pack")

    private var camera: OrthographicCamera = OrthographicCamera()
    private var viewport: Viewport

    private val b2dr: Box2DDebugRenderer

    private var hud: Hud
    private val world: World

    private val player: Player
    private val onScreenController: OnScreenController

    init {
        tiledMap = mapLoader.load("lv1.tmx")
        mapRenderer = OrthogonalTiledMapRenderer(tiledMap)

        viewport = FitViewport(MainGame.V_WIDTH.toFloat(), MainGame.V_HEIGHT.toFloat(), camera)
        camera.position[viewport.worldWidth / 2, viewport.worldHeight / 2] = 0f
        b2dr = Box2DDebugRenderer()
        hud = Hud(game.batch)

        world = World(Vector2(0f, -80f), true)
        player = Player(world, this)
        onScreenController = OnScreenController(game)

        B2WorldCreator(world, tiledMap)
    }

    private fun handleInput(delta: Float) {
        if (onScreenController.isLeftPressed || onScreenController.isRightPressed) {
            player.moveHorizontal(onScreenController.isLeftPressed)
        } else if (onScreenController.isUpPressed) {
            player.jump()
        }
    }

    private fun updateWorldStatus(delta: Float) {
        handleInput(delta)
        world.step(1 / 60f, 6, 2)
        // Attach camera to player
        camera.position.x = player.b2Body.position.x

        player.update(delta)
        camera.update()
        // Draw only visible part of the world
        mapRenderer.setView(camera)
    }

    override fun render(delta: Float) {
        updateWorldStatus(delta)
        clearScreen()

//        game.batch.projectionMatrix = hud.stage.camera.combined
        game.batch.projectionMatrix = camera.combined
        game.batch.begin()
        game.batch.drawSprite(player)
        game.batch.end()

        hud.stage.draw()
        mapRenderer.render()
        onScreenController.draw()
        // Debug purposes
        b2dr.render(world, camera.combined)
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        onScreenController.resize(width, height)
    }

    override fun dispose() {
        tiledMap.dispose()
        mapRenderer.dispose()
        world.dispose()
        b2dr.dispose()
        hud.dispose()
    }

    private fun clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    override fun pause() {}
    override fun resume() {}
    override fun hide() {}
    override fun show() {}
}
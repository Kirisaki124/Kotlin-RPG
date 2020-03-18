package com.mygdx.game.Controller

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.MainGame
import com.mygdx.game.MainGame.Companion.V_HEIGHT
import com.mygdx.game.MainGame.Companion.V_WIDTH

class OnScreenController(game: MainGame) {
    private var viewport: Viewport
    private var stage: Stage
    private var camera: OrthographicCamera = OrthographicCamera()

    var isUpPressed = false
    var isLeftPressed = false
    var isRightPressed = false

    fun draw() {
        stage.draw()
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    init {
        viewport = FitViewport(V_WIDTH.toFloat(), V_HEIGHT.toFloat(), camera)
        stage = Stage(viewport, game.batch)
        Gdx.input.inputProcessor = stage
        val table = Table()
        table.left().bottom()
        val upImage = Image(Texture("upBtn.png"))
        upImage.setSize(50f, 50f)
        upImage.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                isUpPressed = true
                return true
            }

            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                isUpPressed = false
            }
        })
        val leftImage = Image(Texture("leftBtn.png"))
        leftImage.setSize(50f, 50f)
        leftImage.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                isLeftPressed = true
                return true
            }

            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                isLeftPressed = false
            }
        })
        val rightImage = Image(Texture("rightBtn.png"))
        rightImage.setSize(50f, 50f)
        rightImage.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                isRightPressed = true
                return true
            }

            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                isRightPressed = false
            }
        })
        table.add()
        table.add(upImage).size(upImage.width, upImage.height)
        table.add()
        table.row().pad(5f, 5f, 5f, 5f)
        table.add(leftImage).size(leftImage.width, leftImage.height)
        table.add()
        table.add(rightImage).size(rightImage.width, rightImage.height)
        stage.addActor(table)
    }
}
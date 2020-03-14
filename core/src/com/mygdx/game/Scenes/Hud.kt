package com.mygdx.game.Scenes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.Main

class Hud(sb: SpriteBatch) : Disposable {
    @JvmField
    var stage: Stage
    var viewport: Viewport
    var name: Label

    init {
        viewport = FitViewport(Main.V_WIDTH.toFloat(), Main.V_HEIGHT.toFloat(), OrthographicCamera())
        stage = Stage(viewport, sb)
        val table = Table()
        table.top()
        table.setFillParent(true)
        name = Label("TEST", LabelStyle(BitmapFont(), Color.WHITE))
        table.add(name).expandX()
        stage.addActor(table)
    }

    override fun dispose() {
        stage.dispose()
    }
}
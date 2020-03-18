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
import com.mygdx.game.MainGame

class Hud(sb: SpriteBatch) : Disposable {
    var stage: Stage
    private var viewport: Viewport = FitViewport(MainGame.V_WIDTH.toFloat(), MainGame.V_HEIGHT.toFloat(), OrthographicCamera())
    private var name: Label

    init {
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
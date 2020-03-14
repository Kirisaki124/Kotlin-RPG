package com.mygdx.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Screens.PlayScreen

class Main : Game() {
    lateinit var batch: SpriteBatch
    override fun create() {
        batch = SpriteBatch()
        setScreen(PlayScreen(this))
    }

    override fun dispose() {
        batch.dispose()
    }

    companion object {
        const val PPM = 100
        var V_WIDTH = 400
        var V_HEIGHT = 208
    }
}
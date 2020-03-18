package com.mygdx.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.Screens.PlayScreen

class MainGame : Game() {
    lateinit var batch: SpriteBatch
    override fun create() {
        batch = SpriteBatch()
        setScreen(PlayScreen(this))
    }

    override fun dispose() {
        batch.dispose()
    }

    companion object {
        const val PIXEL_PER_METER = 100
        const val V_WIDTH = 400
        const val V_HEIGHT = 208
    }
}
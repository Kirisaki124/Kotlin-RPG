package com.mygdx.game.Tools

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch

fun SpriteBatch.drawSprite(sprite: Sprite) {
    sprite.draw(this)
}
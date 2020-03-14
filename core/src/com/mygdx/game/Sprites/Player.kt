package com.mygdx.game.Sprites

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.physics.box2d.*

class Player(var world: World) : Sprite() {
    lateinit var b2Body: Body

    fun definePlayer() {
        val bodyDef = BodyDef()

        bodyDef.position[40f] = 40f
        bodyDef.type = BodyDef.BodyType.DynamicBody
        b2Body = world.createBody(bodyDef)

        val fixtureDef = FixtureDef()
        val shape = CircleShape()

        shape.radius = 8f
        fixtureDef.shape = shape
        b2Body.createFixture(fixtureDef)
    }

    init {
        definePlayer()
    }
}
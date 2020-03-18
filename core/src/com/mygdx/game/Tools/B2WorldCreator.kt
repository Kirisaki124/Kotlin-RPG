package com.mygdx.game.Tools

import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.physics.box2d.*

class B2WorldCreator(private val world: World, private val map: TiledMap) {
    companion object {
        // TODO: Need to check and rename
        const val BACKGROUND_LAYER = 2
        const val GROUND_LAYER = 3
    }

    init {
        // 2d Box ground
        //  Ground
        createBodyAndFixture(BACKGROUND_LAYER)
        createBodyAndFixture(GROUND_LAYER)
    }

    private fun createBodyAndFixture(layer: Int) {
        val bodyDef = BodyDef()
        val shape = PolygonShape()
        val fixtureDef = FixtureDef()
        var body: Body

        for (obj in map.layers[layer].objects.getByType(RectangleMapObject::class.java)) {
            val rect = (obj as RectangleMapObject).rectangle

            body = world.createBody(bodyDef.apply {
                type = BodyDef.BodyType.StaticBody
                position[rect.getX() + rect.getWidth() / 2] = rect.getY() + rect.getHeight() / 2
            })

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2)
            fixtureDef.shape = shape
            body.createFixture(fixtureDef)
        }
    }
}
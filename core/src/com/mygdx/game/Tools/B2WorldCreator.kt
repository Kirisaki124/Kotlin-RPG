package com.mygdx.game.Tools

import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.physics.box2d.*

class B2WorldCreator(val world: World, val map: TiledMap) {

    init {
        init()
    }

    private fun init() {
        // 2d Box ground
        //  Ground
        createBodyAndFixture(1)
        createBodyAndFixture(2)
    }

    private fun createBodyAndFixture(layer: Int) {
        val bodyDef = BodyDef()
        val shape = PolygonShape()
        val fixtureDef = FixtureDef()
        var body: Body

        for (obj in map.layers[layer].objects.getByType(RectangleMapObject::class.java)) {
            val rect = (obj as RectangleMapObject).rectangle
            bodyDef.type = BodyDef.BodyType.StaticBody
            bodyDef.position[rect.getX() + rect.getWidth() / 2] = rect.getY() + rect.getHeight() / 2
            body = world.createBody(bodyDef)
            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2)
            fixtureDef.shape = shape
            body.createFixture(fixtureDef)
        }
    }
}
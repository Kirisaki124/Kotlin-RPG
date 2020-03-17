package com.mygdx.game.Sprites

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.Array
import com.mygdx.game.Screens.PlayScreen


class Player(var world: World, var screen: PlayScreen): Sprite(screen.atlas.findRegion("DinoSprites - doux")) {
    lateinit var b2Body: Body
    enum class State { FALLING, JUMPING, STANDING, RUNNING }
    var currentState: State
    var previousState: State
    var playerRun: Animation<TextureRegion>
    var playerJump: Animation<TextureRegion>
    var runningRight: Boolean
    var stateTimer: Float

    lateinit var playerStand: TextureRegion
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

    fun update(delta: Float) {
        setPosition(b2Body.position.x - width/2 , b2Body.position.y - height/2)
        setRegion(getFrame(delta))
    }

    fun getFrame(delta: Float): TextureRegion {
        currentState = getState()
        var region :TextureRegion
        region = when (currentState) {
            State.JUMPING -> playerJump.getKeyFrame(stateTimer)
            State.RUNNING -> playerRun.getKeyFrame(stateTimer, true)
            else -> {
                playerStand
            }
        }

        if ((b2Body.linearVelocity.x < 0 || !runningRight) && !region.isFlipX) {
            region.flip(true, false)
            runningRight = false
        }else if ((b2Body.linearVelocity.x > 0 || runningRight) && region.isFlipX) {
            region.flip(true, false)
            runningRight = true
        }

        if (currentState == previousState) {
            stateTimer += delta
        } else stateTimer = 0f

        previousState = currentState

        return region
    }

    fun jump() {
        if (currentState != State.JUMPING) currentState = State.JUMPING
    }

    fun getState(): State {
        return if (b2Body.linearVelocity.y > 0 || (b2Body.linearVelocity.y < 0 && previousState == State.JUMPING)) State.JUMPING
        else if (b2Body.linearVelocity.y < 0) State.FALLING
        else if (b2Body.linearVelocity.x != 0f) State.RUNNING
        else State.STANDING
    }

    init {
//        Sprite

        currentState = State.STANDING
        previousState = State.STANDING
        stateTimer = 0f
        runningRight = true

        var frames: Array<TextureRegion> = Array()
        // running
        for (i in 1..4) {
            frames.add(TextureRegion(screen.atlas.findRegion("DinoSprites - vita"), i * 16, 0, 16, 16))
        }
        playerRun = Animation(0.1f, frames)
        frames.clear()

        // jump
        for (i in 5..6) {
            frames.add(TextureRegion(screen.atlas.findRegion("DinoSprites - tard"), i * 16, 0, 16, 16))
        }
//        frames.add(TextureRegion(texture, 6 * 16, 0))
        playerJump = Animation(0.1f, frames)

        playerStand = TextureRegion(texture, 0 ,0 , 16, 16)
        setBounds(0f, 0f, 16f, 16f)
        setRegion(playerStand)
        definePlayer()
    }
}
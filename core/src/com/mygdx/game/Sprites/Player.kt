package com.mygdx.game.Sprites

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.Array
import com.mygdx.game.Screens.PlayScreen


class Player(var world: World, var screen: PlayScreen) : Sprite(screen.textureAtlas.findRegion("DinoSprites - doux")) {
    companion object {
        const val MOVE_SPEED = 10f
        const val JUMP_HEIGHT = 20f
    }

    enum class State { FALLING, JUMPING, STANDING, RUNNING }

    lateinit var b2Body: Body

    var currentState: State
    var previousState: State
    var playerRunAnim: Animation<TextureRegion>
    var playerJump: Animation<TextureRegion>
    var runningRight: Boolean
    var stateTimer: Float

    var playerStand: TextureRegion

    fun moveHorizontal(isLeft: Boolean) {
        b2Body.applyLinearImpulse(Vector2(MOVE_SPEED * (if (isLeft) -1 else 1), 0f), b2Body.worldCenter, true)
    }

    private fun setupPlayerCollider() {
        val bodyDef = BodyDef().apply {
            position[40f] = 40f
            type = BodyDef.BodyType.DynamicBody
        }
        b2Body = world.createBody(bodyDef)

        val collidedBoxCircle = CircleShape().apply {
            radius = 8f
        }
        val fixtureDef = FixtureDef().apply {
            shape = collidedBoxCircle
        }

        b2Body.createFixture(fixtureDef)
    }

    fun update(delta: Float) {
        setPosition(b2Body.position.x - width / 2, b2Body.position.y - height / 2)
        setRegion(getFrame(delta))
    }

    private fun getFrame(delta: Float): TextureRegion {
        currentState = getState()
        var region: TextureRegion = when (currentState) {
            State.JUMPING -> playerJump.getKeyFrame(stateTimer)
            State.RUNNING -> playerRunAnim.getKeyFrame(stateTimer, true)
            else -> playerStand
        }

        if ((b2Body.linearVelocity.x < 0 || !runningRight) && !region.isFlipX) {
            region.flip(true, false)
            runningRight = false
        } else if ((b2Body.linearVelocity.x > 0 || runningRight) && region.isFlipX) {
            region.flip(true, false)
            runningRight = true
        }

        if (currentState == previousState) stateTimer += delta else stateTimer = 0f

        previousState = currentState

        return region
    }

    fun jump() {
        if (currentState != State.JUMPING) currentState = State.JUMPING
        b2Body.applyLinearImpulse(Vector2(0f, JUMP_HEIGHT), b2Body.worldCenter, true)
    }

    private fun getState() = when {
        b2Body.linearVelocity.y > 0 || b2Body.linearVelocity.y < 0 && previousState == State.JUMPING -> State.JUMPING
        b2Body.linearVelocity.y < 0 -> State.FALLING
        b2Body.linearVelocity.x != 0f -> State.RUNNING
        else -> State.STANDING
    }

    init {
//        Sprite
        currentState = State.STANDING
        previousState = State.STANDING
        stateTimer = 0f
        runningRight = true

        var frames = Array<TextureRegion>()
        // running
        for (i in 1..4) {
            frames.add(TextureRegion(screen.textureAtlas.findRegion("DinoSprites - vita"), i * 16, 0, 16, 16))
        }
        playerRunAnim = Animation(0.1f, frames)
        frames.clear()
        // jump
        for (i in 5..6) {
            frames.add(TextureRegion(screen.textureAtlas.findRegion("DinoSprites - tard"), i * 16, 0, 16, 16))
        }
//        frames.add(TextureRegion(texture, 6 * 16, 0))
        playerJump = Animation(0.1f, frames)

        playerStand = TextureRegion(texture, 0, 0, 16, 16)
        setBounds(0f, 0f, 16f, 16f)
        setRegion(playerStand)
        setupPlayerCollider()
    }
}
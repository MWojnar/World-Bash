package com.mwojnar.game;

import java.util.List;
import java.util.Random;

import com.mwojnar.framework.Input.TouchEvent;

public class AsteroidParticles extends Entity {
	
	public int timer;
	
	public AsteroidParticles(double x, double y, double speed, double direction) {
		
		super();
		setSprite(GameScreen.spriteAsteroidParticle);
		setPos(x, y);
		setDirSpeed(speed, direction);
		Random theRandom = new Random();
		timer = theRandom.nextInt(10) + 60;
		
	}
	
	public void update(List<TouchEvent> touchEvents) {
		
		super.update(touchEvents);
		timer--;
		movePos(vSpeed, hSpeed);
		if (timer <= 0 || x < -6 || x > 400 || y < -6 || y > 240)
			destroy();
		
	}
	
}

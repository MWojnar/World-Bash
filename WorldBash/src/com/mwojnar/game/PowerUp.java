package com.mwojnar.game;

import java.util.List;

import com.mwojnar.framework.Input.TouchEvent;

public class PowerUp extends Entity {
	
	double initialX, initialY, oscillation;
	
	PowerUp() {
		
		super();
		sprite = GameScreen.spritePowerUp;
		initialX = x;
		initialY = y;
		oscillation = 0;
		
	}
	
	public void update(List<TouchEvent> touchEvents) {
		
		super.update(touchEvents);
		oscillation += .1;
		if (oscillation >= Math.PI * 2)
			oscillation -= Math.PI * 2;
		setPos(initialX, initialY - Math.sin(oscillation) * 5);
		
	}
	
}

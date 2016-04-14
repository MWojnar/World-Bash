package com.mwojnar.game;

import java.util.List;

import com.mwojnar.framework.Input.TouchEvent;

public class Health extends Entity {
	
	public Health() {
		
		super();
		sprite = GameScreen.spriteHealth;
		animated = false;
		frame = 3;
		
	}
	
	public void update(List<TouchEvent> touchEvents) {
		
		super.update(touchEvents);
		frame = 4 - Planet.health;
		if (frame < 0)
			frame = 0;
		if (frame >= sprite.frames)
			frame = sprite.frames - 1;
		
	}
	
}

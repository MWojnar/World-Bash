package com.mwojnar.game;

import java.util.List;

import com.mwojnar.framework.Input.TouchEvent;

public class Shield extends Entity {
	
	Planet earth;
	
	Shield(Planet earth) {
		
		super();
		this.earth = earth;
		sprite = GameScreen.spriteShield;
		x = earth.x + earth.sprite.frameWidth / 2 - sprite.frameWidth / 2;
		y = earth.y + earth.sprite.image.getHeight() / 2 - sprite.image.getHeight() / 2;
		
	}
	
	public void update(List<TouchEvent> touchEvents) {
		
		super.update(touchEvents);
		x = earth.x + earth.sprite.frameWidth / 2 - sprite.frameWidth / 2;
		y = earth.y + earth.sprite.image.getHeight() / 2 - sprite.image.getHeight() / 2;
		if (!GameScreen.entityList.contains(earth) || (earth.shieldTimer <= 0)) {
			
			destroy();
			
		}
		
	}
	
}

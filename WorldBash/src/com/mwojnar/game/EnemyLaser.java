package com.mwojnar.game;

import java.util.List;

import com.mwojnar.framework.Input.TouchEvent;

public class EnemyLaser extends Entity {
	
	public EnemyLaser() {
		
		super();
		sprite = GameScreen.spriteEnemyLaser;
		
	}
	
	public void update(List<TouchEvent> touchEvents) {
		
		super.update(touchEvents);
		if (active) {
			
			movePos(hSpeed, vSpeed);
			if (x < -sprite.frameWidth || x > 400 || y < -sprite.image.getHeight() || y > 240)
				destroy();
			
		}
		
	}

}

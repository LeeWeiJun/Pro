package ui;

import java.util.Random;

public class Enemy extends FlyingObject {

	private int yspeed = 3;
	public Enemy() {
		this.image = Panel.enemy;
		width = 50;
		height = 50;
		y = -height;
		x =170;
		Random rand = new Random();
		x = rand.nextInt(Panel.WIDTH - width);
	}
	
	public int getScore() {
		return 5;
	}

	@Override
	public boolean outOfBounds() {
		return y > Panel.HEIGHT;
	}

	@Override
	public void step() {
		y += yspeed;
	}
	
}

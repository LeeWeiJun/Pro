package ui;

public class Bullet extends FlyingObject {
	private int speed = 3; // 移動的速度
			
	public Bullet(int x, int y) {
		this.x = x;
		this.y = y;
		this.image = Panel.bullet;
	}

	/** 移動 */
	@Override
	public void step() {
		y -= speed;
	}

	@Override
	public boolean outOfBounds() {
		return y < -height;
	}

}

package ui;

public class Airplane extends FlyingObject {
	private int life;

	public Airplane() {
		life = 3;
		image = Panel.airplane;
		width = 50;
		height = 50;
		x = (Panel.WIDTH / 2) - 30; // 170
		y = Panel.HEIGHT - 90; // 610
	}

	/** 減命 */
	public void subtractLife() { // 減命
		life--;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	@Override
	public boolean outOfBounds() { // 越界
		return false;
	}

	public Bullet[] shoot() { // 子彈位置
		int xStep = width / 2;
		int yStep = 20;
		Bullet[] bullets = new Bullet[1];
		bullets[0] = new Bullet(x + xStep - 5, y - yStep);
		return bullets;

	}

	public void step() {
	}

	public boolean hit(FlyingObject other) { // 敵機判定
		int x1 = other.x;
		int x2 = other.x + 50;
		int y = other.y + 50; // enemy Y
		int airplanex = this.x; // plane X
		int airplaney = this.y; // plane Y
		return (x1 <= airplanex && x2 >= airplanex && airplaney <= y)
				|| (x1 <= airplanex + 50 && x2 >= airplanex + 50 && airplaney <= y);

	}
}

package ui;

import java.awt.Image;

public abstract class FlyingObject {

	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Image image;
	protected int score;
	
	protected FlyingObject[] fl= {};
	protected Bullet[] bs= {};
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public abstract boolean outOfBounds();

	public abstract void step();

	public boolean shootBy(Bullet bullet) {
		int x = bullet.x;
		int y = bullet.y;
		return this.x < x && x < this.x + width && this.y < y && y < this.y + height;
	}

	public interface Enemy{
		int getScore();
	}
	
	public FlyingObject[] getfl() {
		return fl;
	}
	
	public void setfl(FlyingObject[] fl) {
		this.fl = fl;
	}
	
	public Bullet[] getbs() {
		return bs;
	}
	
	public void setbs(Bullet[] bs) {
		this.bs = bs;
	}
}

package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.util.Arrays;
import java.util.Random;

public class Panel extends JPanel {

	public static final int WIDTH = 400;
	public static final int HEIGHT = 700;

	public static int state;
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int GAME_OVER = 2;
	public static final int STOP = 3;
	public int score = 0; // 得分

	static Image bg = new ImageIcon("bg/bg.jpg").getImage();
	static Image airplane = new ImageIcon("material/plane.PNG").getImage();
	static Image bullet = new ImageIcon("material/bullet.PNG").getImage();
	static Image enemy = new ImageIcon("material/enemy.PNG").getImage();

	Airplane Airplane = new Airplane();
	Enemy Enemy = new Enemy();
	public FlyingObject[] flyings = {}; // 敵機陣列
	private Bullet[] bullets = {}; // 子彈陣列

	@Override
	public void paint(Graphics g) {
		g.drawImage(bg, 0, 0, 400, 700, null);
		airplane(g);
		bullet(g);
		enemy(g);
		if (state == START)
			start(g);
		if (state == RUNNING)
			paintScore(g);
		if (state == STOP)
			stop(g);
		if (state == GAME_OVER) {
			over(g);
		}
	}

	int iii = 0;

	public void airplane(Graphics g) {
		g.drawImage(airplane, Airplane.getX(), Airplane.getY(), Airplane.width, Airplane.height, null);
	}

	public void bullet(Graphics g) {
		bullets = Airplane.getbs();
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			g.drawImage(bullet, (b.getX() - b.getWidth() / 2), b.getY(), 10, 20, null);
		}
	}

	int x = 0;

	public void enemy(Graphics g) {
		flyings = Enemy.getfl();
		if (flyings.length > 0)

			for (int i = 0; i < flyings.length; i++) {
				FlyingObject f = flyings[i];
				g.drawImage(enemy, f.getX(), f.getY(), Enemy.width, Enemy.height, null);
			}
	}

	public void paintScore(Graphics g) {
		int x = 10;
		int y = 25;
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 22);
		g.setColor(new Color(0xFF0000));
		g.setFont(font);
		g.drawString("SCORE:" + score, x, y);
		y = y + 20;
		g.drawString("LIFE:" + Airplane.getLife(), x, y);
	}

	public void start(Graphics g) {
		int x = 80;
		int y = 200;
		Font start = new Font(Font.DIALOG_INPUT, Font.BOLD, 30);
		g.setColor(new Color(0x000000));
		g.setFont(start);
		g.drawString("Press any Key ", x, y);
		x = x + 20;
		y = y + 65;
		g.drawString(" to  START", x, y);
	}

	public void stop(Graphics g) {
		int x = 150;
		int y = 200;
		Font stop = new Font(Font.DIALOG_INPUT, Font.BOLD, 30);
		g.setColor(new Color(0x000000));
		g.setFont(stop);
		g.drawString("STOP ", x, y);
		x = x - 130;
		y = y + 65;
		g.drawString("Press P to START", x, y);
	}

	public void over(Graphics g) {
		int x = 100;
		int y = 200;
		Font over = new Font(Font.DIALOG_INPUT, Font.BOLD, 25);
		g.setColor(new Color(0xFFFFFF));
		g.setFont(over);
		g.drawString("GAME OVER!", x, y);
		y = y + 30;
		g.drawString("SCORE :" + score, x, y);

		Airplane.setLife(3);
		Airplane.getLife();
		Bullet[] bullets = {};
		Airplane.setbs(bullets);
	}

	int flyEnteredIndex = 0; // 飛行物入場計數

	public void enterEnemy() {
		flyEnteredIndex++;
		if (flyEnteredIndex % 40 == 0) {
			FlyingObject obj = nextOne();
			flyings = Arrays.copyOf(flyings, flyings.length + 1);
			flyings[flyings.length - 1] = obj;
		}
		Enemy.setfl(flyings);
	}

	public void stepAction() {
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			f.step();
		}

		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			b.step();
		}
	}

	public void flyingStepAction() {
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			f.step();
		}
	}

	int shootIndex = 0;
	public void shootAction() {
		shootIndex++;
		if (shootIndex % 30 == 0) {
			Bullet[] bs = Airplane.shoot();
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);
			Airplane.setbs(bullets);
		}
	}

	public void bangAction() {
		for (int i = 0; i < bullets.length; i++) { // 遍歷所有子彈
			Bullet b = bullets[i];
			bang(b); // 子彈和飛行物之間的碰撞檢查
		}
	}

	public void outOfBoundsAction() {
		int index = 0;

		FlyingObject[] flyingLives = new FlyingObject[flyings.length];
		for (int i = 0; i < Enemy.getfl().length; i++) {
			FlyingObject f = flyings[i];

			if (!f.outOfBounds())
				flyingLives[index++] = f;
		}
		flyings = Arrays.copyOf(flyingLives, index);
		Enemy.setfl(flyings);
		index = 0;

		Bullet[] bulletLives = new Bullet[bullets.length];
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			if (!b.outOfBounds())
				bulletLives[index++] = b;
		}
		bullets = Arrays.copyOf(bulletLives, index);
		Airplane.setbs(bullets);
	}

	public void checkGameOverAction() {
		if (GameOver() == true) {
			state = GAME_OVER; // 改變狀態
		}
	}

	public boolean GameOver() {
		for (int i = 0; i < flyings.length; i++) {
			int index = -1;
			FlyingObject obj = flyings[i];
			if (Airplane.hit(obj)) {
				Airplane.subtractLife();
				index = i;
			}
			if (index != -1) {
				FlyingObject t = flyings[index];
				flyings[index] = flyings[flyings.length - 1];
				flyings[flyings.length - 1] = t;

				flyings = Arrays.copyOf(flyings, flyings.length - 1);
				Enemy.setfl(flyings);
			}
		}
		return Airplane.getLife() <= 0;
	}

	public void bang(Bullet bullet) {
		int index = -1;
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject obj = flyings[i];
			if (obj.shootBy(bullet)) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			FlyingObject one = flyings[index];
			flyings[index] = flyings[flyings.length - 1];
			flyings = Arrays.copyOf(flyings, flyings.length - 1);
			Enemy.setfl(flyings);
			if (one instanceof Enemy) {
				Enemy e = (Enemy) one;
				score += e.getScore();
			}
		}
	}

	public static FlyingObject nextOne() {
		return new Enemy();
	}

}

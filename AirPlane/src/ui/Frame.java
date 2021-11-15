package ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class Frame extends JFrame {

	private Timer timer; // 定時器
	private int intervel = 1000 / 100; // 時間間隔(毫秒)

	Panel panel = new Panel();
	public Frame() {
		// Title
		this.setTitle("Airplane");
		// width & height
		this.setSize(400, 700);
		// drag
		this.setResizable(false);
		// default close
		this.setDefaultCloseOperation(3);
		// View in MID
		this.setLocationRelativeTo(null);
		// setPanel
		this.setContentPane(panel);
		// vision
		this.setVisible(true);
		action();
	}

	public void action() {
		KeyAdapter k = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				char charA = e.getKeyChar();
				if (Panel.state == Panel.START)
				{
					System.out.println("START");
					Panel.state = Panel.RUNNING;
				}

				else if (Panel.state == Panel.GAME_OVER) {
					System.out.println("Game Over!");
					Panel.state = Panel.START;
					panel.score = 0;
					System.out.println("Reset OK");
					return;
				}
				switch (charA) {
				case 'p','P':
					if(Panel.state == Panel.RUNNING)
					Panel.state = Panel.STOP;
					else
						Panel.state = Panel.RUNNING;
				break;
				}
				if (Panel.state == Panel.RUNNING) {
					switch (charA) {
					case 'a', 'A':
						if (panel.Airplane.x >= -25) {
							panel.Airplane.x -= 10;
						} else {
							panel.Airplane.x = 360;
						}

						break;
					case 'd', 'D':
						if (panel.Airplane.x <= 355) {
							panel.Airplane.x += 10;
						} else {
							panel.Airplane.x = -30;
						}
						break;
					}
				}
			}
		};
		this.addKeyListener(k);

		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (Panel.state == Panel.RUNNING) {
					panel.enterEnemy();
					panel.stepAction();
					panel.shootAction();
					panel.bangAction();
					panel.outOfBoundsAction();
					panel.checkGameOverAction();
				}
				repaint();
			}
		}, intervel, intervel);
	}
}

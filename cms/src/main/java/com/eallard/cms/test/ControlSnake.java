package com.eallard.cms.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ControlSnake extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Random rand;

	ArrayList<Point> list, listBody;

	String str, str1;

	static boolean key;

	int x, y, dx, dy, fx, fy, flag;

	int snakeBody;

	int speed;

	public ControlSnake() {
		snakeBody = 1;

		str = "上下左右方向键控制 P键暂停...";
		str1 = "现在的长度为:" + snakeBody;
		key = true;
		flag = 1;

		speed = 1700;
		rand = new Random();
		list = new ArrayList<Point>();
		listBody = new ArrayList<Point>();

		x = 5;
		y = 5;
		list.add(new Point(x, y));
		listBody.add(list.get(0));

		dx = 10;
		dy = 0;

		fx = rand.nextInt(30) * 10 + 5;// 2
		fy = rand.nextInt(30) * 10 + 5;// 2

		setBackground(Color.WHITE);
		setSize(new Dimension(318, 380));

		final Timer time = new Timer(speed, this);
		time.start();

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 37) {
					dx = -10;
					dy = 0;
				} else if (e.getKeyCode() == 38) {
					dx = 0;
					dy = -10;
				} else if (e.getKeyCode() == 39) {
					dx = 10;
					dy = 0;
				} else if (e.getKeyCode() == 40) {
					dx = 0;
					dy = 10;
				} else if (e.getKeyCode() == 80) {
					if (flag % 2 == 1) {
						time.stop();
					}
					if (flag % 2 == 0) {
						time.start();
					}
					flag++;
				}
			}
		});

	}

	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 400, 400);
		g.setColor(Color.DARK_GRAY);
		g.drawLine(3, 3, 305, 3);
		g.drawLine(3, 3, 3, 305);
		g.drawLine(305, 3, 305, 305);
		g.drawLine(3, 305, 305, 305);
		g.setColor(Color.PINK);

		for (int i = 0; i < listBody.size(); i++) {
			g.fillRect(listBody.get(i).x, listBody.get(i).y, 9, 9);
		}
		g.fillRect(x, y, 9, 9);
		g.setColor(Color.ORANGE);
		g.fillRect(fx, fy, 9, 9);

		g.setColor(Color.DARK_GRAY);
		str1 = "现在的长度为:" + snakeBody;
		g.drawString(str, 10, 320);
		g.drawString(str1, 10, 335);
	}

	public void actionPerformed(ActionEvent e) {
		x += dx;
		y += dy;
		if (makeOut() == false) {
			JOptionPane.showMessageDialog(null, "重新开始......");

			speed = 700;

			snakeBody = 1;

			x = 5;
			y = 5;

			list.clear();
			list.add(new Point(x, y));
			listBody.clear();
			listBody.add(list.get(0));

			dx = 10;
			dy = 0;

		}
		addPoint(x, y);
		if (x == fx && y == fy) {
			speed = (int) (speed * 0.8);// 速度增加参数
			if (speed < 200) {
				speed = 100;
			}
			fx = rand.nextInt(30) * 10 + 5;// 2
			fy = rand.nextInt(30) * 10 + 5;// 2
			snakeBody++;// 2
		} // 2
		repaint();
	}

	public void addPoint(int xx, int yy) {
		// 动态的记录最新发生的50步以内的移动过的坐标
		// 并画出最新的snakeBody
		if (list.size() < 100) {// 蛇身长度最长为100
			list.add(new Point(xx, yy));
		} else {
			list.remove(0);
			list.add(new Point(xx, yy));
		}
		if (snakeBody == 1) {
			listBody.remove(0);
			listBody.add(0, list.get(list.size() - 1));
		} else {
			listBody.clear();
			if (list.size() < snakeBody) {
				for (int i = list.size() - 1; i > 0; i--) {
					listBody.add(list.get(i));
				}
			} else {
				for (int i = list.size() - 1; listBody.size() < snakeBody; i--) {
					listBody.add(list.get(i));
				}
			}
		}
	}

	public boolean makeOut() {
		if ((x < 3 || y < 3) || (x > 305 || y > 305)) {
			return false;
		}
		for (int i = 0; i < listBody.size() - 1; i++) {
			for (int j = i + 1; j < listBody.size(); j++) {
				if (listBody.get(i).equals(listBody.get(j))) {
					return false;
				}
			}
		}
		return true;
	}
}
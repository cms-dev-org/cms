package com.eallard.cms.test;

import java.awt.event.*;
import javax.swing.*;
public class Snake extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static void main(String args[]) {
	      JFrame app=new Snake( );
	      app.setTitle("贪吃蛇Game"); 
	      app.setBounds(300,200,400,400); 
	      app.setVisible(true);
	      app.setResizable(false);
	      app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}



	public Snake() {
		JMenuBar mBar = new JMenuBar(); // 创建常规菜单栏对象mBar
		setJMenuBar(mBar); // 把菜单栏放入相应的容器
		JMenu[] m = { new JMenu("游戏菜单(F)"), new JMenu("困难程度(E)") };
		//char[][] mC = { { 'F', 'E' }, { 'O', 'A', 'S' }, { 'C', 'V', 'B' } };
		JMenuItem[][] mI = {
				{ new JMenuItem("开始游戏(O)"), new JMenuItem("暂停游戏(A)"),
						new JMenuItem("退出游戏(S)") },
				{ new JMenuItem("简单(C)"), new JMenuItem("普通(V)"),
						new JMenuItem("困难(B)") } };
		int a, b;
		for (a = 0; a < m.length; a++) {
			mBar.add(m[a]); // 添加下拉式菜单

			for (b = 0; b < mI[a].length; b++) {
				m[a].add(mI[a][b]); // 添加命令式菜单项

				KeyStroke keyOpen = KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK);
				mI[0][0].setAccelerator(keyOpen);
				KeyStroke keyStop = KeyStroke.getKeyStroke('A', InputEvent.CTRL_DOWN_MASK);
				mI[0][1].setAccelerator(keyStop);
				KeyStroke keyExit = KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK);
				mI[0][2].setAccelerator(keyExit);
			}
		}
	}
}

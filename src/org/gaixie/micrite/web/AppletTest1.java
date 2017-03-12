package org.gaixie.micrite.web;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class AppletTest1 extends Applet {
	
	TextField t1, t2;
	int x, y;
	Panel p;
	Thread t;
	boolean running;
	
	public boolean check(int a) {
		int b = 0;
		for(int i = 1; i < a; i++) {
			if((a % i) == 0) {
				b += i;
			}
		}
		return a == b;
	}
	
	public void init() {
		Label l1 = new Label("x:");
		Label l2 = new Label("y:");
		t1 = new TextField();
		t2 = new TextField();
		t = new Thread() {
			
			@Override
			public void run() {
				while(running) {
					try {
						if(x < p.getWidth() - 5) {
							x += 5;
						} else {
							x = 0;
						}
						p.repaint();
						sleep(200);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			}
		};
		Button b = new Button();
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				x = Integer.parseInt(t1.getText());
				y = Integer.parseInt(t2.getText());
				if(!t.isAlive()) {
					running = true;
					t.start();
				}
			}
		});
		p = new Panel() {
			
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				g.drawString("你好", x, y);
			}
		};
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridbag);
		
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.0;
		c.gridwidth = 1;
		gridbag.setConstraints(l1, c);
		add(l1);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(t1, c);
		add(t1);
		
		c.gridy = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.0;
		c.gridwidth = 1;
		gridbag.setConstraints(l2, c);
		add(l2);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(t2, c);
		add(t2);
		
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(b, c);
		add(b);
		
		c.gridy = 3;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1.0;
		c.gridheight = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(p, c);
		add(p);
		setSize(300, 300);
	}
	
	public static void main(String args[]) {
		Frame f = new Frame("test");
		AppletTest1 t = new AppletTest1();
		t.init();
		f.add("Center", t);
		f.pack();
		f.setSize(f.getPreferredSize());
		f.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.setVisible(true);
	}
}


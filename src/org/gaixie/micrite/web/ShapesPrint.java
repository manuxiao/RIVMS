package org.gaixie.micrite.web;
//这个例子不完整
/* 
 还是我还是java打印问题 文本图片打印如何分页
 此程序是实现文本和图片打印到一张纸的代码 
 如果我要分页该怎么做呢？ 
 我试图把文本内容放到数组然后在drawShapes(Graphics2D g2)函数中 
 画出每一行文字。但drawShapes函数总是被调用使我的数组游标总是变化 
 而不准确！ 
 请大家帮我想个办法！ 
 最好能给出可运行的代码 
 谢谢！ 

 */

import java.awt.geom.*; //绘制2D图形 
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import java.awt.print.*;
import java.io.*;
import javax.imageio.ImageIO;

public class ShapesPrint extends JPanel // 继承jpanel shapesparint本身是个panel
		implements Printable, ActionListener {

	final static Color bg = Color.white; // 设置静态变量
	final static Color fg = Color.black;
	final static Color red = Color.red;
	final static Color white = Color.white;

	final static BasicStroke stroke = new BasicStroke(2.0f); // 设置线宽
	final static BasicStroke wideStroke = new BasicStroke(8.0f);

	final static float dash1[] = { 10.0f };
	final static BasicStroke dashed = new BasicStroke(1.0f,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);// 设置成虚线
	final static JButton button = new JButton("Print");

	public ShapesPrint() { // 构造函数中添加按扭事件
		setBackground(bg);
		button.addActionListener(this);
		System.out.println("this+++++++++++++++++++++++++++" + this.getSize());
	}

	public void actionPerformed(ActionEvent e) { // 在ActionListener中重载actionPerformed方法
		if (e.getSource() instanceof JButton) {
			PrinterJob printJob = PrinterJob.getPrinterJob();
			printJob.setPrintable(this); // 继承Printable 传this
			if (printJob.printDialog()) { // 显示打印对话框
				try {
					printJob.print(); // 执行打印
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public void paintComponent(Graphics g) // panel当中的方法，画图应该在他当中实现
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g; // 把Graphics强制转化为Graphics2D丛而实现2D方法
		drawShapes(g2);
	}

	public void drawShapes(Graphics2D g2) { // 实现具体画法
		Dimension d = getSize();
		System.out.println("getSize++++++++++++++" + d);
		int gridWidth = 400 / 6;
		int gridHeight = 300 / 2;

		int rowspacing = 5;
		int columnspacing = 7;
		int rectWidth = gridWidth - columnspacing;
		int rectHeight = gridHeight - rowspacing;

		Color fg3D = Color.lightGray; // 轻绿色

		g2.setPaint(fg3D);
		g2.drawRect(80, 80, 400 - 1, 155);
		g2.setPaint(fg);

		int x = 85;
		int y = 87;

		// draw Line2D.Double 是类，画线段
		g2.draw(new Line2D.Double(x, y + rectHeight - 1, x + rectWidth, y));
		x += gridWidth;

		// draw Rectangle2D.Double
		g2.setStroke(stroke);
		g2.draw(new Rectangle2D.Double(x, y, rectWidth, rectHeight));
		x += gridWidth;

		// draw RoundRectangle2D.Double
		g2.setStroke(dashed);
		g2
				.draw(new RoundRectangle2D.Double(x, y, rectWidth, rectHeight,
						10, 10));
		x += gridWidth;

		// draw Arc2D.Double
		g2.setStroke(wideStroke);
		g2.draw(new Arc2D.Double(x, y, rectWidth, rectHeight, 90, 135,
				Arc2D.OPEN));
		x += gridWidth;

		// draw Ellipse2D.Double
		g2.setStroke(stroke);

		g2.draw(new Ellipse2D.Double(x, y, rectWidth, rectHeight));
		x += gridWidth;

		// draw GeneralPath (polygon) 用复线画图
		int x1Points[] = { x, x + rectWidth, x, x + rectWidth };
		int y1Points[] = { y, y + rectHeight, y + rectHeight, y };
		GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
				x1Points.length);
		polygon.moveTo(x1Points[0], y1Points[0]);
		for (int index = 1; index < x1Points.length; index++) {
			polygon.lineTo(x1Points[index], y1Points[index]);
		}
		;
		polygon.closePath();

		g2.draw(polygon);

		// NEW ROW
		x = 85;
		y += gridHeight;
		y += 30;

		String str = "c:\\wj.txt";
		try // 读取文件
		{
			BufferedReader breader = new BufferedReader(new FileReader(str));
			while (true) {
				str = breader.readLine();
				if (str == null)
					break;
				g2.drawString(str, x, y);
				y += 15;

			}

			g2
					.drawImage(
							ImageIO
									.read(new FileInputStream(
											new File(
													"C:\\Documents and Settings\\Administrator\\My Documents\\My Pictures\\样品.jpg"))),
							x, y, this);

			/*
			 * g2.drawImage( ImageIO.read(new File("C:\\Documents and
			 */
		} catch (Exception e) {//这是是后面增加的
			// TODO: handle exception
		}
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
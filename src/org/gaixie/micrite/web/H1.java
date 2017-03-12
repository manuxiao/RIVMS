package org.gaixie.micrite.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.Scanner;
//学生管理系统
public class H1 {
	public static void main(String args[]) throws Exception {
		int xh = 1;
		int cj = 1;
		int k;
		String km = "";
		Scanner input = new Scanner(System.in);

		System.out.print("1.写入\n2.查询\n3.退出\n请输入你想要的操作号：");
		k = input.nextInt();

		if (k == 1) {

			String outfilename = "c:\\1.txt";

			File fout = new File(outfilename);

			PrintWriter out = new PrintWriter(new FileWriter(fout));

			for (int i = 0; i <= 10; i++) {

				System.out.print("请输入学号(1~200)(输入0结束输入)：\n");
				xh = input.nextInt();

				System.out.print("请输入科目：\n");
				km = input.next();

				System.out.print("请输入成绩：\n");
				cj = input.nextInt();

				Student std = new Student(xh, km, cj);

				out.println(std.xuehao + std.kemu + std.chengji + "\n");

				if (xh == 0) {
					out.close();
					break;

				}

			}

		}

		if (k == 2) {

			System.out.print("请输入学号(0~200)：");

			xh = input.nextInt();

			try {

				FileInputStream fis = new FileInputStream("c:\\1.txt");
				InputStreamReader isr = new InputStreamReader(fis);
				LineNumberReader lnr = new LineNumberReader(isr);
				String s = null;
				int i;
				String j;
				while ((s = lnr.readLine()) != null) {

					j = s.substring(0, 1);

					if (xh / 10 == 0) {
						j = s.substring(0, 1);
					} else if ((xh >= 10) && (xh <= 99)) {
						j = s.substring(0, 2);
					} else if ((xh >= 100) && (xh <= 200)) {
						j = s.substring(0, 3);
					}

					i = Integer.parseInt(j);

					if (i == xh) {
						System.out.println(s);
					} else {
						continue;
					}
				}
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (k == 3) {
			System.exit(0); // 退出
		}

		if (k != 1 && k != 2 && k != 3) {
			System.out.print("ERROR");
		}

	}
}

class Student {
	int xuehao;
	String kemu;
	int chengji;
	final static int LEN = 8;

	public Student(int xuehao, String kemu, int chengji) { // 定义构造函数
		if (kemu.length() > LEN) {
			kemu = kemu.substring(0, 8);
		} else {
			while (kemu.length() < LEN)
				kemu = kemu + "\u0000"; // 后加空格
		}
		this.kemu = kemu;
		this.xuehao = xuehao;
		this.chengji = chengji;
	}
}

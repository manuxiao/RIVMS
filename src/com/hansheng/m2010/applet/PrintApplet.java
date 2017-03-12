package com.hansheng.m2010.applet;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.*; 
import java.awt.*; 

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import netscape.javascript.JSObject;
/*实现套打，把套打的格式当成一张图片，将要填入的数据按其在图片在坐标来定位*/ 
public class PrintApplet extends JApplet implements Printable { 
    private final static int MAX = 10;
	private String[][] valueXy = new String[MAX][2];
	private String str;
	JSObject javascriptWin;
	private String callbackJsFn;
	private String checkIds;
	private int x,y;
	private int fontSize=20;
	private int paperWidth=80;
    private double pageWidth = 480; 
    private double pageHeight = 440; 
    private int previewWidth = 260,previewHeight=400;
    /** 
     * implements Printable 
     * PageFormat类描述了页面的定位信息、它的尺寸以及每英尺可绘制的区域（单位1/72nd）。 
     */ 
    public int print(Graphics g, PageFormat pf, int pageIndex) { 
//        System.out.println("pageIndexx="+pageIndex); 
        if(pageIndex>0){
        	return Printable.NO_SUCH_PAGE;
        }
        String[] strs=null;
        int suitSize = 0;
        Graphics2D g2d = (Graphics2D) g; 
//		g2d.fillRect(0, 0, (int)pageWidth, (int)pageHeight);
//      g2d.setColor(new   Color(255,   179,   64));   //   #FFB340   ->   R:255   G:179   B:64  
        g2d.setColor(Color.black);
        g2d.setFont(new Font(Font.DIALOG,Font.PLAIN,fontSize));
//        System.out.println("valueXy.length="+valueXy.length);
        for (int i = 0; i < valueXy.length; i++) { 
            str = valueXy[i][0];
            if(str==null)continue;
            try {
            	strs=valueXy[i][1].split(",");
            	x = Integer.parseInt(strs[0]);
            	y = Integer.parseInt(strs[1]);
			} catch (Exception e) {
				System.out.println(e);
				x=0;y=0;
			}
//			System.out.println("x/y="+x+"/"+y+","+str);
			suitSize=fontSize;
			if(paperWidth!=0){
				int strWidth = g2d.getFontMetrics().stringWidth(str);
				while(strWidth>(paperWidth-x)&&suitSize>1){
					suitSize = g2d.getFont().getSize();
					suitSize--;
					g2d.setFont(new Font(Font.DIALOG,Font.PLAIN,suitSize));
					strWidth = g2d.getFontMetrics().stringWidth(str);
				}
			}
			g2d.setFont(new Font(Font.DIALOG,Font.PLAIN,suitSize));
            g2d.drawString(str, x, y); 
        } 
        return Printable.PAGE_EXISTS; //会不停打印，NO_SUCH_PAGE打印工作才停止。
    }
    public void init() {
    	initParams();
        Container cont = getContentPane();
    	cont.setBackground(Color.yellow);
		cont.setLayout(new BorderLayout());

		JPanel abovePanel = new JPanel();

		Canvas c = new Canvas(){
			@Override
			public void paint(Graphics g) {
				PrintApplet.this.print(g, null, 0);
			}
		}; 
//		abovePanel.setBackground(Color.yellow);
		c.setSize(previewWidth, previewHeight);
		System.out.println("canvas:width="+c.getSize().getWidth()+",height="+c.getSize().getHeight());
		c.setBackground(Color.white);
		abovePanel.add(c, 0);
		cont.add(abovePanel, "Center");
		JPanel buttons = new JPanel();
		buttons.setBackground(Color.white);
		JButton printBtn = new JButton("确认打印");
		buttons.add(printBtn);
		cont.add(buttons, "South");

		printBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				printReport();
				if (javascriptWin != null)
				{
					if(checkIds==null){
						System.out.println("last checkIds=null");
						checkIds="[]";
					}else{
						System.out.println("last checkIds="+checkIds);
					}
					if(callbackJsFn==null){
						System.out.println("last callbackJsFn=null");
						callbackJsFn="callbackJsFn";	
					}else{
						System.out.println("last callbackJsFn="+callbackJsFn);
					}
					String arguments[] = {checkIds};
					System.out.println("checkIds="+checkIds);
					try {
						System.out.println("javascriptWin="+javascriptWin);
						javascriptWin.call(callbackJsFn, arguments);	
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
					
				}	
				}catch (Exception ex) {
					// TODO: handle exception
					ex.printStackTrace();
					
				}
			}
		});
	}

    public void  printReport() { 

        PrinterJob pj = PrinterJob.getPrinterJob();//创建一个打印任务 
        PageFormat pf = PrinterJob.getPrinterJob().defaultPage(); 
        Paper paper = pf.getPaper(); 
        
        paper.setSize(pageWidth, pageHeight); 
        paper.setImageableArea(0, 0, pageWidth, pageHeight); 
        pf.setPaper(paper); 
        
        pj.setPrintable(this, pf); 

//        if (pj.printDialog()) { //弹出打印对话框，打印对话框，用户可以通过它改变各种选项，例如：设置打印副本数目，页面方向，或者目标打印机。 
            try { 
                pj.print(); 
            } catch (PrinterException e) { 
                e.printStackTrace(); 
            } 
//        } 
    }
private void initParams(){
   	callbackJsFn = this.getParameter("callbackJsFn");
   	javascriptWin = JSObject.getWindow(this);
   	checkIds =this.getParameter("checkIds");
   	if(checkIds==null)checkIds="[]";
   	System.out.println("checkIds="+checkIds);
	try {
    	pageWidth = Double.parseDouble(this.getParameter("pageWidth"));
	} catch (Exception e) {System.out.println("exception:pageWidth="+this.getParameter("pageWidth"));e.printStackTrace();}
    try {
    	pageHeight = Double.parseDouble(this.getParameter("pageHeight"));
	} catch (Exception e) {System.out.println("exception:pageHeight="+this.getParameter("pageHeight"));e.printStackTrace();}
	try {
		previewWidth=Integer.parseInt(this.getParameter("previewWidth"));
	} catch (Exception e) {	}
	try {
		previewHeight=Integer.parseInt(this.getParameter("previewHeight")); 
	} catch (Exception e) {	}
	try {
    	fontSize = Integer.parseInt(this.getParameter("fontSize"));
	} catch (Exception e) {}
	try {
		paperWidth = Integer.parseInt(this.getParameter("paperWidth"));
	} catch (Exception e) {paperWidth=0;}
	
	int i=0;
    str=this.getParameter("str0");
    while(str!=null) {
    	valueXy[i][0]=str;
    	str=this.getParameter("xy"+i);
    	valueXy[i][1]=str;
		i++;
		if(i>=MAX)break;
		str=this.getParameter("str"+i);
	}
}
}

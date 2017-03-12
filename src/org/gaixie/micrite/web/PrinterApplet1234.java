package org.gaixie.micrite.web;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;

import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import javax.swing.*;

public class PrinterApplet1234 extends JApplet {
	// ~ Instance fields

	private PrintRequestAttributeSet aset;
	DocFlavor DocFlavor;
	public Doc doc;
	DocPrintJob printerJob;
	JTextPane pane;

	// ~ Methods

	/**
	 * DOCUMENT ME!
	 */
	public void init() {
		
		getContentPane().setLayout(new BorderLayout());
		
		System.out.println("=======================");

		pane = new JTextPane();
		pane.setSize(150, 100);
		pane.setContentType("text/html");
//		pane.setText("<html><center><b><big>Applet Test</big></b></center></html>");
//		pane.setText("Applet Test");
		
		getContentPane().add(pane, "Center");

		JPanel buttons = new JPanel();
		buttons.setBackground(Color.white);

		JButton print = new JButton("打印");

		buttons.add(print);

		getContentPane().add(buttons, "South");

		print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				print();
			}
		});
	}

	/**
	 * DOCUMENT ME!
	 */
	void prep() {

		URL url = null;
		aset = new HashPrintRequestAttributeSet();
		aset.add(MediaSizeName.ISO_A4);
		aset.add(new Copies(1));

		try {
//			url = new URL("http://sohowww.nascom.nasa.gov//data//realtime//eit_171//512//latest.gif");
			url = new URL("http://localhost/micrite/images/bg.jpg");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintService pservices = PrintServiceLookup.lookupDefaultPrintService();
		System.out.println(pservices.getName());
		// DocFlavor flavor = javax.print.DocFlavor.INPUT_STREAM.GIF;
		DocFlavor flavor = javax.print.DocFlavor.URL.GIF;
		// doc = new InputStreamDoc("a.gif", flavor);
		// doc = new InputStreamDoc(pane.getText(), flavor);
		doc = new SimpleDoc(url, javax.print.DocFlavor.URL.GIF, null);
		try {
			System.out.println("DOC : \n " + doc.getPrintData());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/* Create a Print Job */
		printerJob = pservices.createPrintJob();

	}

	/**
	 * DOCUMENT ME!
	 */
	void print() {
		prep();
		System.out.println("Printer Name : " + printerJob.getPrintService());
		try {
			printerJob.print(doc, aset);

		} catch (PrintException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done Printing.");
	}

}
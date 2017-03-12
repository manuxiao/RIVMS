package org.gaixie.micrite.web;

import java.applet.Applet;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.util.JRLoader;

public class JRPrinterApplet extends javax.swing.JApplet {

	/**     
     *     
     */
	private URL url = null;
	private JasperPrint jasperPrint = null;

	/** Creates new form AppletViewer */
	public JRPrinterApplet() {

	}

	final static String strUrl = "print1";

	/**     
    *     
    */
	public void init() {
		// String strUrl = getParameter("REPORT_URL");

		try {
			// url = new URL(getCodeBase(), strUrl);
			URL urll = new URL(strUrl);
			url = urll;
			System.out.println("url=" + urll);
		} catch (Exception e) {
			StringWriter swriter = new StringWriter();
			PrintWriter pwriter = new PrintWriter(swriter);
			e.printStackTrace(pwriter);
			JOptionPane.showMessageDialog(this, swriter.toString());
		}
	}

	public void start() {
		if (url != null) {
			if (jasperPrint == null) {
				try {
					System.out.println("进入start方法，即将下载pdf文件");
					jasperPrint = (JasperPrint) JRLoader.loadObject(url);
					System.out.println("进入start方法，下载pdf文件完毕");
				} catch (Exception e) {
					StringWriter swriter = new StringWriter();
					PrintWriter pwriter = new PrintWriter(swriter);
					e.printStackTrace(pwriter);
					JOptionPane.showMessageDialog(this, swriter.toString());
				}
			}

			if (jasperPrint != null) {
				final JasperPrint print = jasperPrint;

				Thread thread = new Thread(new Runnable() {
					public void run() {
						try {
							System.out.println("进入start方法，即将打印pdf文件");
							JasperPrintManager.printReport(print, true);
						} catch (Exception e) {
							StringWriter swriter = new StringWriter();
							PrintWriter pwriter = new PrintWriter(swriter);
							e.printStackTrace(pwriter);
							JOptionPane.showMessageDialog(null, swriter
									.toString());
						}
					}
				});

				thread.start();
			} else {
				JOptionPane.showMessageDialog(this, "Empty report.");
			}
		} else {
			JOptionPane.showMessageDialog(this,
					"start():Source URL not specified");
		}

	}

}
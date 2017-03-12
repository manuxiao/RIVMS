package org.gaixie.micrite.web;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 * 建立数据库链接 由 sql 语法取出资料，准备好传入的 parameters 将 parameters 及取出的资料传入 Jasper 中。会由
 * .jasper 文档生出 .jrprint 文档 再由 .jrprint 文档生出 .pdf 目的文档给调用页面下载
 * 
 * @author Defonds
 * 
 */
public class JRPrinterServler3 extends HttpServlet {

	private static final String CONTENTTYPE = "application/octet-stream";

	/**
	 * Constructor of the object.
	 */
	public JRPrinterServler3() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Log.debug("dddddddd");
		String fileName = "";
		String sql = "";
		Map parameters = new HashMap();
		// parameters.put("rptToday", strDate);//传入的 prameters 引数
		sql = "select * from tab_channel_car_basic t";

		Connection conn = this.createConnection();// 建立连接
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			response.setContentType(CONTENTTYPE);
			ServletOutputStream ouputStream = response.getOutputStream();
			fileName = "D:\\iReport\\iReport-0.4.0\\Work\\carBasic3.jrxml";
			JasperReport jrt = JasperCompileManager.compileReport(fileName);// 编译报表格式
			JasperPrint jpt = JasperFillManager.fillReport(jrt, parameters,
					new JRResultSetDataSource(rs));// 匹配数据源，生成JasperPrint
			ObjectOutputStream oos = new ObjectOutputStream(ouputStream);
			oos.writeObject(jpt);
			oos.flush();
			oos.close();

		} catch (Exception e) {
			System.out.println("Error:" + e.toString());
			e.printStackTrace();
		} finally {
			CloseConnect(conn);
			conn = null;
		}

	}

	/**
	 * 建立连接方法
	 * 
	 * @return Connection
	 */
	public Connection createConnection() {
		Connection conn;
		try {
			String driver = "oracle.jdbc.driver.OracleDriver";
			String url = "jdbc:oracle:thin:@localhost:1521:CUC";
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "sa");
			conn.setAutoCommit(false);
			return conn;
		} catch (SQLException e1) {
			System.out.println("建立连接错误 = " + e1.toString());
			e1.printStackTrace();
		} catch (ClassNotFoundException e2) {
			System.out.println("建立连接错误 = " + e2.toString());
			e2.printStackTrace();
		}
		return null;
	}

	/**
	 * 关闭连接方法
	 * 
	 * @param conn
	 */
	public void CloseConnect(Connection conn) {
		try {
			conn.commit();
			conn.setAutoCommit(true);
			conn.close();
		} catch (Exception e) {
			System.out.println("关闭连接错误 = " + e.toString());
		}
	}

}

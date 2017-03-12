package org.gaixie.micrite.web;

//import com.borland.dbswing.JdbTable;
//import com.borland.dbswing.DBExceptionHandler;
//import com.borland.dbswing.DBTableModel;
import javax.swing.*;
//import com.borland.dx.dataset.*;
import javax.swing.table.*;
//import com.borland.dx.text.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.print.*;
import java.util.Vector;
//import com.tbr.app.print.*;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company:</p>
 * 
 * @author unascribed
 * @version 1.0
 */
public class MydbTable extends JTable/*JdbTable */implements Printable {
	final static Color bg = Color.white;
	final static Color fg = Color.black;
	final static Color red = Color.red;
	final static Color white = Color.white;
	final static BasicStroke stroke = new BasicStroke(2.0f);
	final static BasicStroke wideStroke = new BasicStroke(8.0f);
	final static float dash1[] = { 10.0f };
	final static BasicStroke dashed = new BasicStroke(1.0f,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
	final static JButton button = new JButton("Print");

	/**
	 * <p>
	 * Creates <code>JdbTable</code>'s default right-click popup menu.
	 * </p>
	 * 
	 * @return The popup menu.
	 */
	protected JPopupMenu createPopupMenu() {
		JPopupMenu menu = new JPopupMenu();
		menu.setDefaultLightWeightPopupEnabled(false);
		JMenuItem menuItem;
		// JMenu sortMenu = new JMenu(Res._SortBy);
		JMenu sortMenu = new JMenu("排序");
		/*if (this.getDataSet().isEditing()) {
			sortMenu.setEnabled(false);
		}*/
		// JCheckBoxMenuItem checkItem = new JCheckBoxMenuItem(Res._Unsorted);
		JCheckBoxMenuItem checkItem = new JCheckBoxMenuItem("取消排序");
//		checkItem.addActionListener(this);
		checkItem.setActionCommand("nosort");
//		SortDescriptor sortDescriptor = this.getDataSet().getSort();
		String columnSortKey = "";
		/*if (sortDescriptor == null) {
			checkItem.setState(true);
		} else {
			String[] sortKeys;
			if ((sortKeys = sortDescriptor.getKeys()) != null
					&& sortKeys.length > 0) {
				columnSortKey = sortKeys[0];
			}
		}*/
		sortMenu.add(checkItem);
		String[] context = new String[]{"aaaa","bbbb"};
		String columnName;
		for (int colNo = 0, maxCols = context.length/*getColumnModel().getColumnCount()*/; colNo < maxCols; colNo++) {
			columnName=context[colNo];
//			Column dataSetColumn = ((DBTableModel) getModel()).getColumn(convertColumnIndexToModel(colNo));
			// System.out.println(dataSetColumn.getColumnName());
//			if (dataSetColumn == null || !this.getDataSet().isSortable(dataSetColumn)) {
//				continue;
//			}
//			TableColumn column = getColumnModel().getColumn(colNo);
//			if (column.getHeaderValue() instanceof String[]) {
//				checkItem = new JCheckBoxMenuItem(getConcatenatedString((String[]) column.getHeaderValue()));
//			} else {
//				checkItem = new JCheckBoxMenuItem(column.getHeaderValue().toString().replace('\n', ' '));
//			}
//			checkItem.addActionListener(this);
//			columnName = getColumnModel().getColumn(colNo).getIdentifier().toString();
			checkItem.setActionCommand(columnName);
			if (columnSortKey.equals(columnName)) {
				checkItem.setState(true);
			} else {
				checkItem.setState(false);
			}
			sortMenu.add(checkItem);
		}
		menu.add(sortMenu);
		menu.addSeparator();
		boolean readOnly = false;
		/*if (this.getDataSet() instanceof StorageDataSet) {
			readOnly = ((StorageDataSet) this.getDataSet()).isReadOnly();
		}*/
		// menuItem = new JMenuItem(Res._Post);
		menuItem = new JMenuItem("发送改变到当前行");
		menuItem.setEnabled(false);//this.getDataSet().isEditing());
//		menuItem.addActionListener(this);
		menuItem.setActionCommand("post");
		menu.add(menuItem);
		// menuItem = new JMenuItem(Res._Cancel);
		menuItem = new JMenuItem("取消改变当前行");
		menuItem.setEnabled(true);//this.getDataSet().isEditing());
//		menuItem.addActionListener(this);
		menuItem.setActionCommand("cancel");
		menu.add(menuItem);
		// menuItem = new JMenuItem(Res._Insert);
		menuItem = new JMenuItem("添加新数据行");
		menuItem.setEnabled(false);//this.getDataSet().isEnableInsert() && isEditable()				&& !this.getDataSet().isEditingNewRow()				&& this.getDataSet().isEditable() && !readOnly);
//		menuItem.addActionListener(this);
		menuItem.setActionCommand("insert");
		menu.add(menuItem);
		// menuItem = new JMenuItem(Res._Delete);
		menuItem = new JMenuItem("删除当前数据行");
//		try {
			menuItem.setEnabled(false);//this.getDataSet().isEnableDelete()					&& isEditable() && !this.getDataSet().isEmpty()					&& this.getDataSet().isEditable() && !readOnly);
//			menuItem.addActionListener(this);
			menuItem.setActionCommand("delete");
			menu.add(menuItem);
//		} catch (DataSetException e) {
//			DBExceptionHandler.handleException(this.getDataSet(), this, e);
//		}
		return menu;
	}

	private String getConcatenatedString(String[] strings) {
		StringBuffer caption = new StringBuffer();
		for (int i = 0; i < strings.length; i++) {
			caption.append(strings[i]).append(" ");
		}
		caption.deleteCharAt(caption.length() - 1);
		return caption.toString();
	}

	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
		// System.out.println("print("+pageIndex+")");
		Graphics2D g2 = (Graphics2D) graphics;
		JTable tableView = this;
		g2.setColor(Color.black);
		int fontHeight = g2.getFontMetrics().getHeight();
		int fontDesent = g2.getFontMetrics().getDescent();
		int pageHeight = (int) pageFormat.getImageableHeight() - fontHeight;
		int pageWidth = (int) pageFormat.getImageableWidth();
		int tableWidth = 100;//tableView.getColumnModel().getTotalColumnWidth();
		int[][] clnpages = this.getPageColumnWidthPos((int) pageWidth);
		int pagecolumnForTable = clnpages.length;
		int rowMargin = 10;//tableView.getRowMargin();
		int headerHeightOnPage = 10;//(tableView.getTableHeader().getHeight());
		int oneRowHeight = 20;//tableView.getRowHeight();
		int numRowsOnAPage = (pageHeight - headerHeightOnPage) / oneRowHeight;
		double pageHeightForTable = oneRowHeight * numRowsOnAPage;
		int pagerowForTable = 1;// (tableView.getRowCount() + numRowsOnAPage - 1)/ numRowsOnAPage;
		// System.out.println(pagerowForTable+"|"+pagecolumnForTable);
		int totalNumPages = pagerowForTable * pagecolumnForTable;
		// System.out.println(totalNumPages);
		if (pageIndex >= totalNumPages) {
			return NO_SUCH_PAGE;
		}
		g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		g2.drawString("第" + (pageIndex + 1) + "页/共" + totalNumPages + "页",
				(int) pageWidth / 2 - 35,
				(int) (pageHeight + fontHeight - fontDesent));// bottom center
		// If this piece of the table is smaller than the size available,
		// clip to the appropriate bounds.
		int pagecolumn = pageIndex / pagerowForTable;
		int pagerow = pageIndex % pagerowForTable;
		g2.translate(-clnpages[pagecolumn][0], 0);// 设置头的水平偏移量
		g2.setClip(clnpages[pagecolumn][0], 0, clnpages[pagecolumn][1],
				(int) Math.ceil(headerHeightOnPage));
//		tableView.getTableHeader().paint(g2);// paint header at top
		g2.translate(0f, headerHeightOnPage);// 跳过头
		g2.translate(0f, -pagerow * pageHeightForTable);// 设置打印页所在TABLE中的垂直偏移量
		if (pagerow + 1 == pagerowForTable) {
			double lastRowPrinted = numRowsOnAPage * pagerow;
			double numRowsLeft = 10;//tableView.getRowCount() - lastRowPrinted;
			g2.setClip(clnpages[pagecolumn][0],
					(int) (pageHeightForTable * pagerow),
					clnpages[pagecolumn][1], (int) Math.ceil(oneRowHeight
							* numRowsLeft + rowMargin));
		}
		// else clip to the entire area available.
		else {
			g2.setClip(clnpages[pagecolumn][0],
					(int) (pageHeightForTable * pagerow),
					clnpages[pagecolumn][1], (int) Math.ceil(pageHeightForTable
							+ rowMargin));
		}
//		tableView.paint(g2);
		return Printable.PAGE_EXISTS;//会不停打印，NO_SUCH_PAGE打印工作才停止。
	}

	private int[][] getPageColumnWidthPos(int pagewidth) {
		TableColumnModel cm = null;//this.getColumnModel();
		int cnt = cm.getColumnCount();
		Vector<int[]> vt = new Vector<int[]>();
		int w = 0, x = 0, currentpagewidth = 0;
		int[] nextpagepos;
		for (int i = 0; i < cnt; i++) {
			w = cm.getColumn(i).getWidth();
			if (currentpagewidth + w > pagewidth) {
				if (currentpagewidth > 0) {
					nextpagepos = new int[2];
					nextpagepos[0] = x;
					x += currentpagewidth;
					nextpagepos[1] = currentpagewidth;
					vt.add(nextpagepos);
				}
				while (w >= pagewidth) {
					nextpagepos = new int[2];
					nextpagepos[0] = x;
					x += pagewidth;
					nextpagepos[1] = pagewidth;
					vt.add(nextpagepos);
					w -= pagewidth;
				}
				currentpagewidth = w;
			} else {
				currentpagewidth += w;
			}
		}
		if (currentpagewidth > 0) {
			nextpagepos = new int[2];
			nextpagepos[0] = x;
			x += currentpagewidth;
			nextpagepos[1] = x;
			vt.add(nextpagepos);
		}
		int[][] ret = new int[vt.size()][];
		vt.toArray(ret);
		return ret;
	}

	private int printPageCount(Graphics graphics) {
		PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
		Graphics2D g2 = (Graphics2D) graphics;
		JTable tableView = this;
		g2.setColor(Color.black);
		int fontHeight = g2.getFontMetrics().getHeight();
		int fontDesent = g2.getFontMetrics().getDescent();
		int pageHeight = (int) pageFormat.getImageableHeight() - fontHeight;
		int pageWidth = (int) pageFormat.getImageableWidth();
		int tableWidth = tableView.getColumnModel().getTotalColumnWidth();
		int[][] clnpages = this.getPageColumnWidthPos((int) pageWidth);
		int pagecolumnForTable = clnpages.length;
		int rowMargin = tableView.getRowMargin();
		int headerHeightOnPage = (tableView.getTableHeader().getHeight());
		int oneRowHeight = tableView.getRowHeight();
		int numRowsOnAPage = (pageHeight - headerHeightOnPage) / oneRowHeight;
		int pageHeightForTable = oneRowHeight * numRowsOnAPage;
		int pagerowForTable = (tableView.getRowCount() + numRowsOnAPage - 1)
				/ numRowsOnAPage;
		int totalNumPages = pagerowForTable * pagecolumnForTable;
		return totalNumPages;
	}

//	public void print() {
	public boolean print() {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
		if (job.printDialog()) {
			try {
				job.print();
			} catch (Exception exc) {
				exc.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public void printPreview() {
		PrintPreviewDialog ppd = new PrintPreviewDialog(null, "打印预览", true,
				this);
		ppd.show();
	}
	public static void main(String[] args) {
		MydbTable a = new MydbTable();
		a.doLayout();
		a.show();
	}
}

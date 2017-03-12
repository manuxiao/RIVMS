package org.gaixie.micrite.web;
//print和pageSetup两个按钮
/*
连接打印机打印excel 我写了一个应用程序，我用poi生成了excel表格， 想问下怎么通过java连接打印机打印excel表格
有人说用ireport和jasperReport可以实现，不过没有例子，时间又紧…
如果不能用java直接打印excel的话那能用什么方式代替…我的邮箱,278777851@qq.com

快沉了，自己顶上去，希望大家多多帮忙。。。
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Stephen
 */
public   class   PrintTest
{     public   static   void   main(String[]   args)
      {     JFrame   frame   =   new   PrintTestFrame();
            frame.show();
      }
}

class   PrintTestFrame   extends   JFrame implements   ActionListener
{
    public   PrintTestFrame()
    {
        setTitle( "PrintTest ");
        setSize(500,   500);
        addWindowListener(new   WindowAdapter()
              {     public   void   windowClosing(WindowEvent   e)
                    {     System.exit(0);
                    }
              }   );

        Container   contentPane   =   getContentPane();
        canvas   =   new   PrintPanel();
        //            contentPane.add(canvas,   "Center ");
        contentPane.add(canvas,BorderLayout.CENTER);
        JPanel   buttonPanel   =   new   JPanel();
        printButton   =   new   JButton( "Print ");
        buttonPanel.add(printButton);
        printButton.addActionListener(this);

        pageSetupButton   =   new   JButton( "Page   setup ");
        buttonPanel.add(pageSetupButton);
        pageSetupButton.addActionListener(this);

        //            contentPane.add(buttonPanel,   "North ");
         contentPane.add(buttonPanel, BorderLayout.NORTH);
      }

    public   void   actionPerformed(ActionEvent   event)
    {
        Object   source   =   event.getSource();
        if   (source   ==   printButton)
        {     PrinterJob   printJob   =   PrinterJob.getPrinterJob();
              if   (pageFormat   ==   null)
                    pageFormat   =   printJob.defaultPage();
              printJob.setPrintable(canvas,   pageFormat);
              if   (printJob.printDialog())
              {     try
                    {     printJob.print();
                    }
                    catch   (PrinterException   exception)
                    {     JOptionPane.showMessageDialog(this,   exception);
                    }
              }
        }
        else   if   (source   ==   pageSetupButton)
        {     PrinterJob   printJob   =   PrinterJob.getPrinterJob();
              if   (pageFormat   ==   null)
                    pageFormat   =   printJob.defaultPage();
              pageFormat   =   printJob.pageDialog(pageFormat);
        }
    }

      private   JButton   printButton;
      private   JButton   pageSetupButton;

      private   PrintPanel   canvas;
      private   PageFormat   pageFormat;
}

class   PrintPanel   extends   JPanel  implements   Printable
{
    @Override
     public   void   paintComponent(Graphics   g)
      {     super.paintComponent(g);
            Graphics2D   g2   =   (Graphics2D)g;
//            drawPage(g2);
      }

      public   int   print(Graphics   g,   PageFormat   pf,   int   page)throws   PrinterException
      {
          if   (page >= 1)
                return   Printable.NO_SUCH_PAGE;
            Graphics2D   g2   =   (Graphics2D)g;
            g2.setPaint(Color.black);
            g2.translate(pf.getImageableX(),   pf.getImageableY());
            g2.draw(new   Rectangle2D.Double(0,   0, pf.getImageableWidth(),   pf.getImageableHeight()));
            System.out.println("pf.getImageableWidth()" + pf.getImageableWidth());
            System.out.println("pf.getImageableHeight()" + pf.getImageableHeight());

            drawPage(g2);
            return   Printable.PAGE_EXISTS;//会不停打印，NO_SUCH_PAGE打印工作才停止。
      }

      public   void   drawPage(Graphics2D   g2)
      {
          FontRenderContext   context   =   g2.getFontRenderContext();
            Font   f   =   new   Font( "宋体",   Font.PLAIN,   18);
            GeneralPath   clipShape   =   new   GeneralPath();

            TextLayout   layout   =   new   TextLayout( "公司订购单 ",   f,   context);
            AffineTransform   transform  =   AffineTransform.getTranslateInstance(50,   18);
            Shape   outline   =   layout.getOutline(transform);
            clipShape.append(outline,   false);

            layout   =   new   TextLayout( "公司名： AAAA              日期：2010-10-10 ",   f,   context);
            transform =   AffineTransform.getTranslateInstance(0,   50);
            outline   =   layout.getOutline(transform);
            clipShape.append(outline,   false);

            layout   =   new   TextLayout( "品名             重量／斤      单价／元      金额/元 ",   f,   context);
            transform =   AffineTransform.getTranslateInstance(0,   100);
            outline   =   layout.getOutline(transform);
            clipShape.append(outline,   false);

            layout   =   new   TextLayout( "花生油1            2             2.2         4.4 ",   f,   context);
            transform =   AffineTransform.getTranslateInstance(0,   150);
            outline   =   layout.getOutline(transform);
            clipShape.append(outline,   false);

            layout   =   new   TextLayout( "花生油2            2             2.2         4.4 ",   f,   context);
            transform =   AffineTransform.getTranslateInstance(0,   200);
            outline   =   layout.getOutline(transform);
            clipShape.append(outline,   false);

            layout   =   new   TextLayout( "花生油3            2             2.2         4.4 ",   f,   context);
            transform =   AffineTransform.getTranslateInstance(0,   250);
            outline   =   layout.getOutline(transform);
            clipShape.append(outline,   false);

            layout   =   new   TextLayout( "花生油4            2             2.2         4.4 ",   f,   context);
            transform =   AffineTransform.getTranslateInstance(0,   300);
            outline   =   layout.getOutline(transform);
            clipShape.append(outline,   false);

            layout   =   new   TextLayout( "花生油5            2             2.2         4.4 ",   f,   context);
            transform =   AffineTransform.getTranslateInstance(0,   350);
            outline   =   layout.getOutline(transform);
            clipShape.append(outline,   false);

            layout   =   new   TextLayout( "花生油6            2             2.2         4.4 ",   f,   context);
            transform =   AffineTransform.getTranslateInstance(0,   400);
            outline   =   layout.getOutline(transform);
            clipShape.append(outline,   false);

            layout   =   new   TextLayout( "花生油7            2             2.2         4.4 ",   f,   context);
            transform =   AffineTransform.getTranslateInstance(0,   450);
            outline   =   layout.getOutline(transform);
            clipShape.append(outline,   false);

            layout   =   new   TextLayout( "花生油8            2             2.2         4.4 ",   f,   context);
            transform =   AffineTransform.getTranslateInstance(0,   500);
            outline   =   layout.getOutline(transform);
            clipShape.append(outline,   false);

            layout   =   new   TextLayout( "花生油9            2             2.2         4.4 ",   f,   context);
            transform =   AffineTransform.getTranslateInstance(0,   550);
            outline   =   layout.getOutline(transform);
            clipShape.append(outline,   false);

            layout   =   new   TextLayout( "花生油10            2             2.2         4.4 ",   f,   context);
            transform =   AffineTransform.getTranslateInstance(0,   600);
            outline   =   layout.getOutline(transform);
            clipShape.append(outline,   false);

            layout   =   new   TextLayout( "花生油11            2             2.2         4.4 ",   f,   context);
            transform =   AffineTransform.getTranslateInstance(0,   650);
            outline   =   layout.getOutline(transform);
            clipShape.append(outline,   false);

            layout   =   new   TextLayout( "花生油12            2             2.2         4.4 ",   f,   context);
            transform =   AffineTransform.getTranslateInstance(0,   700);
            outline   =   layout.getOutline(transform);
            clipShape.append(outline,   false);           

            g2.draw(clipShape);
            g2.clip(clipShape);

            final   int   NLINES   =50;
            Point2D   p   =   new   Point2D.Double(0,   0);
            for   (int   i   =   0;   i   <   NLINES;   i++)
            {     double   x   =   (2   *   getWidth()   *   i)   /   NLINES;
                  double   y   =   (2   *   getHeight()   *   (NLINES   -   1   -   i))  /   NLINES;
                  Point2D   q   =   new   Point2D.Double(x,   y);
                  g2.draw(new   Line2D.Double(p,   q));
            }
      }
}

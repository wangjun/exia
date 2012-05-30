/*  Copyright (c) 2010 Xiaoyun Zhu
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy  
 *  of this software and associated documentation files (the "Software"), to deal  
 *  in the Software without restriction, including without limitation the rights  
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell  
 *  copies of the Software, and to permit persons to whom the Software is  
 *  furnished to do so, subject to the following conditions:
 *  
 *  The above copyright notice and this permission notice shall be included in  
 *  all copies or substantial portions of the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,  
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE  
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER  
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,  
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN  
 *  THE SOFTWARE.  
 */
package cn.kk.exia;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class Main extends javax.swing.JFrame implements Logger {

	private static final String helpTarget = "本地文件夹，如：C:\\temp";

	private static final String HELP_TEXT = "<html>例：<br>1. 关键词：如\"chinese\" <br>--> 下载所有包含此关键词的漫画册<br>2. 漫画册网址：http://g.e-hentai.org/g/494953/7c3ec35c08/ <br>--> 下载整本漫画 <br>3. 漫画图网址：http://g.e-hentai.org/s/14b9c859ed/493328-1 <br>--> 下载从本页开始所有的漫画 </html>";

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/*
		 * Set the Nimbus look and feel
		 */
		// <editor-fold defaultstate="collapsed"
		// desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
		// </editor-fold>

		/*
		 * Create and display the form
		 */
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					new Main().setVisible(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private final StyledDocument doc;

	// Variables declaration - do not modify
	private javax.swing.JButton btnDownload;

	private javax.swing.JLabel lblTarget;

	private javax.swing.JLabel lblUrl;

	private javax.swing.JScrollPane spLog;

	private javax.swing.JTextField tfTarget;

	private javax.swing.JTextField tfUrl;

	private javax.swing.JTextPane tpLog;

	// End of variables declaration

	/**
	 * Creates new form Find
	 * 
	 * @throws IOException
	 */
	public Main() throws IOException {
		setIconImage(ImageIO.read(getClass().getResource("/e-down.png")));
		initComponents();

		doc = this.tpLog.getStyledDocument();
	}

	private void btnDownloadActionPerformed(java.awt.event.ActionEvent evt) {
		btnDownload.setEnabled(false);
		final String targetDir = tfTarget.getText();
		final String keyword = tfUrl.getText();
		new Thread() {
			@Override
			public void run() {
				if (-1 == keyword.indexOf("http://")) {
					MangaDownloader.downloadSearchResult(keyword, targetDir,
							Main.this);
				} else if (-1 == keyword.indexOf("/g/")) {
					MangaDownloader
							.downloadManga(keyword, targetDir, Main.this);
				} else {
					MangaDownloader.downloadGallery(keyword, targetDir,
							Main.this);
				}
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						btnDownload.setEnabled(true);
					}

				});
			}
		}.start();
	}

	public synchronized void err(final String text) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Style style = doc.getStyle(StyleContext.DEFAULT_STYLE);
					StyleConstants.setForeground(style, Color.RED);
					doc.insertString(doc.getLength(), text + "\n", style);
					tpLog.scrollRectToVisible(new Rectangle(0, tpLog
							.getHeight() - 2, 1, 1));
				} catch (BadLocationException ex) {
					System.err.println(ex.toString());
				}
			}
		});
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		lblUrl = new javax.swing.JLabel();
		tfUrl = new javax.swing.JTextField();
		btnDownload = new javax.swing.JButton();
		lblTarget = new javax.swing.JLabel();
		tfTarget = new javax.swing.JTextField();
		spLog = new javax.swing.JScrollPane();
		tpLog = new javax.swing.JTextPane();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("e下 （ 漫画流e-hentai.org\"慢\"速下载器）");

		lblUrl.setText("关键词或网址：");
		lblUrl.setToolTipText(HELP_TEXT);

		tfUrl.setText("chinese");
		tfUrl.setToolTipText(HELP_TEXT);

		btnDownload.setText("下载");
		btnDownload.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDownloadActionPerformed(evt);
			}
		});

		lblTarget.setText("保存至：");
		lblTarget.setToolTipText(helpTarget);

		tfTarget.setText(System.getProperty("user.home"));
		tfTarget.setToolTipText(helpTarget);

		spLog.setViewportView(tpLog);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addComponent(spLog)
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING)
																				.addComponent(
																						lblUrl)
																				.addComponent(
																						lblTarget))
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										tfTarget,
																										GroupLayout.DEFAULT_SIZE,
																										395,
																										Short.MAX_VALUE)
																								.addPreferredGap(
																										LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										btnDownload,
																										GroupLayout.PREFERRED_SIZE,
																										90,
																										GroupLayout.PREFERRED_SIZE))
																				.addComponent(
																						tfUrl))))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(lblUrl)
												.addComponent(
														tfUrl,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(lblTarget)
												.addComponent(
														tfTarget,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(btnDownload))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(spLog, GroupLayout.DEFAULT_SIZE,
										203, Short.MAX_VALUE).addContainerGap()));

		pack();
	}// </editor-fold>

	public synchronized void log(final String text) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Style style = doc.getStyle(StyleContext.DEFAULT_STYLE);
					StyleConstants.setForeground(style, Color.BLACK);
					doc.insertString(doc.getLength(), text + "\n", style);
					tpLog.scrollRectToVisible(new Rectangle(0, tpLog
							.getHeight() - 2, 1, 1));
				} catch (BadLocationException ex) {
					System.err.println(ex.toString());
				}
			}
		});
	}
}

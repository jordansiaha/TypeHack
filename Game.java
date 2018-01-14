

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class Game extends JFrame {

//	public static void main(String[] args) {
//		new Game().setVisible(true);
//	}

	private DefaultStyledDocument d = new DefaultStyledDocument();
	private StyleContext context = new StyleContext();
	private Style style = context.addStyle("test", null);
	private JTextPane textPane = new JTextPane(d);
	private char nextChar;
	private String text;
	private int count = 0;

	private int sec = 10;
	private int min = 0;
	private JLabel timeLabel;
	DecimalFormat df = new DecimalFormat("00");
	private int spaceCount = 0;
	private double errorCount = 0;
	
	private String filename;
	private String name;
	
	private boolean done;

	// Timer
	private Timer timer = new Timer(1000, new TimerListener()); // 1 sec

	private class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (sec % 60 == 0) {
				min--;
				sec = 59;
			} else {
				sec--;
			}
			timeLabel.setText(min + ":" + df.format(sec));
			if (min == 0 && sec == 0){
				DecimalFormat df = new DecimalFormat("0.00");
				String wpm = "WPM: " + spaceCount;
				double errorDiff = count - errorCount;
				String error = "" + df.format(((errorDiff / count) * 100)) + "%";
				JOptionPane.showMessageDialog(null, "Game Over!\n" + wpm + "\nAccuracy: " + error);
				saveInformation(spaceCount, error);
				done = true;
				dispose();
				return;
			}
		}

		private void saveInformation(int spaceCount, String error) {
			FileWriter fw = null;
			try {
				/* Yang: make sure to change file name when not appending */
				fw = new FileWriter(new File("Record.txt"), true);
				fw.write("\n" + name + ", " + spaceCount + ", " + error);
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public Game(String filename) {
		this.filename =filename;
		timeLabel = new JLabel(min + ":" + df.format(sec));
		timeLabel.setFont(new Font("Time New Roman", 1, 20));
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		Scanner s;
		try {
			s = new Scanner(new File(filename));
			StringBuilder sb = new StringBuilder();
			while (s.hasNextLine()) {
				String str = s.nextLine();
				sb.append(str);
			}
			text = sb.toString();
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setup();
	}

	public Game(String filename, String name) {
		this.filename =filename;
		this.name = name;
		timeLabel = new JLabel(min + ":" + df.format(sec));
		timeLabel.setFont(new Font("Time New Roman", 1, 20));
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		Scanner s;
		try {
			s = new Scanner(new File(filename));
			StringBuilder sb = new StringBuilder();
			while (s.hasNextLine()) {
				String str = s.nextLine();
				sb.append(str);
			}
			text = sb.toString();
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		setup();
	}

	private void setup() {
		this.setTitle("SpeedTyper");
		this.setSize(new Dimension(800, 600));

		textPane.addKeyListener(new KeyboardListener());

		textPane.setFont(new Font("Time New Roman", 1, 20));
		textPane.setAutoscrolls(true);
		textPane.setText(text);

		JScrollPane jsp = new JScrollPane(textPane);
		// str = textPane.getText();

		textPane.select(count, count + 1);

		textPane.setEditable(false);
		this.add(jsp, BorderLayout.CENTER);
		this.add(timeLabel, BorderLayout.NORTH);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
	}

	private class KeyboardListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent key) {

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent key) {
			timer.start();
			System.out.print(key.getKeyChar());
			if (key.getKeyChar() == '\b') {
				count--;
				if (count < 0) {
					count = 0;
				}
				nextChar = text.charAt(count);
				StyleConstants.setForeground(style, Color.BLACK);
				if (nextChar == ' ') {
					spaceCount--;
					StyleConstants.setBackground(style, Color.WHITE);
				}
				try {
					d.replace(count, 1, "" + nextChar, style);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				textPane.select(count, count + 1);
			} else {
				if (count > text.length() - 1) {
					count = text.length() - 1;
				}
				nextChar = text.charAt(count);
				if (key.getKeyChar() == nextChar) {
					StyleConstants.setForeground(style, Color.GREEN);
					try {
						d.replace(count, 1, "" + nextChar, style);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					errorCount++;
					StyleConstants.setForeground(style, Color.RED);
					if (nextChar == ' ') {
						StyleConstants.setBackground(style, Color.RED);
					} else {
						StyleConstants.setBackground(style, Color.WHITE);
					}
					try {
						d.replace(count, 1, "" + nextChar, style);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (nextChar == ' ') {
					spaceCount++;
				}
				count++;

				textPane.select(count, count + 1);
			}
		}

	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	

}

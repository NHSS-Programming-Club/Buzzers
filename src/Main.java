import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Main extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;
	JLabel label;
	JButton add;
	JButton sub;
	JLabel who;
	JLabel scoreDisplay;
	JButton reset;
	JLabel ph1 = new JLabel(" ");
	JLabel ph2 = new JLabel(" ");

	public boolean[] pressed = new boolean[8];
	public String[] names = new String[pressed.length];
	int[] scores = new int[pressed.length];
	int tempScore = 0;
	int tempIndex = -1;
	boolean isReset = true;
	boolean team1 = true;
	boolean team2 = false;

	public Main(String s) throws InterruptedException {
		super(s);
		while(true) {
			for(int i = 0; i < pressed.length; i++) {
				if(pressed[i] && isReset) {
					playSound();

					who.setText(names[i]);
					add.setVisible(true);
					sub.setVisible(true);
					ph1.setVisible(false);
					ph2.setVisible(false);
					tempIndex = i;

					isReset = false;
				}

				if(tempIndex != -1) {
					scores[tempIndex] += tempScore;
					tempScore = 0;

					String scoreString = "";
					for(int j = 0; j < pressed.length; j++) {
						scoreString += names[j] + ": " + scores[j] + "    ";
					}
					scoreDisplay.setText(scoreString);
					revalidate();
				}

				if(isReset) {
					tempIndex = -1;
					who.setText(" ");
					add.setVisible(false);
					sub.setVisible(false);
					ph1.setVisible(true);
					ph2.setVisible(true);
				}
			}
			Thread.sleep(50l);
		}
	}

	public void initFrame() throws InterruptedException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setNames();

		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		label = new JLabel("Buzzah!!", SwingConstants.CENTER);
		label.setFont(new Font(label.getText(), 50, 50));
		p.add(label);

		who = new JLabel(" ", SwingConstants.CENTER);
		who.setFont(new Font(who.getText(), 35, 35));
		p.add(who);

		add = new JButton();
		add.setText("+");
		sub = new JButton();
		sub.setText("-");
		add.setVisible(false);
		sub.setVisible(false);
		add.setFocusable(false);
		sub.setFocusable(false);
		p.add(add);
		p.add(sub);
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				tempScore++;
			}
		});
		sub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				tempScore--;
			}
		});
		ph1.setFont(new Font(ph1.getText(), 26, 26));
		p.add(ph1);
		p.add(ph2);

		for(int i = 0; i < pressed.length; i++)
			scores[i] = 0;

		scoreDisplay = new JLabel("", SwingConstants.CENTER);
		p.add(scoreDisplay);

		JButton reset = new JButton("Reset Buzzers");
		reset.setFocusable(false);
		p.add(reset);
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				isReset = true;
			}
		});

		add(p);

		addKeyListener(this);
		setSize(500, 500);
		setVisible(true);
	}

	public void playSound() {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("buzz.wav").getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch(Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}

	public void setNames() throws InterruptedException {
		JFrame f = new JFrame("I can haz namez?");
		f.getContentPane().setLayout(new FlowLayout());

		final JTextField t1p1 = new JTextField("T1 P1", 8);
		final JTextField t1p2 = new JTextField("T1 P2", 8);
		final JTextField t1p3 = new JTextField("T1 P3", 8);
		final JTextField t1p4 = new JTextField("T1 P4", 8);
		final JTextField t2p1 = new JTextField("T2 P1", 8);
		final JTextField t2p2 = new JTextField("T2 P2", 8);
		final JTextField t2p3 = new JTextField("T2 P3", 8);
		final JTextField t2p4 = new JTextField("T2 P4", 8);
		JButton b = new JButton("Confirm");	

		f.add(t1p1);
		f.add(t1p2);
		f.add(t1p3);
		f.add(t1p4);
		f.add(t2p1);
		f.add(t2p2);
		f.add(t2p3);
		f.add(t2p4);
		f.add(b);

		f.pack();
		f.setVisible(true);

		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				names[0] = t1p1.getText();
				names[1] = t1p2.getText();
				names[2] = t1p3.getText();
				names[3] = t1p4.getText();
				names[4] = t2p1.getText();
				names[5] = t2p2.getText();
				names[6] = t2p3.getText();
				names[7] = t2p4.getText();
			}
		});
		while(names[0] == null)
			Thread.sleep(100l);
		f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_1:
			pressed[0] = true;
			break;
		case KeyEvent.VK_2:
			pressed[1] = true;
			break;
		case KeyEvent.VK_3:
			pressed[2] = true;
			break;
		case KeyEvent.VK_4:
			pressed[3] = true;
			break;
		case KeyEvent.VK_A:
			pressed[4] = true;
			break;
		case KeyEvent.VK_B:
			pressed[5] = true;
			break;
		case KeyEvent.VK_C:
			pressed[6] = true;
			break;
		case KeyEvent.VK_D:
			pressed[7] = true;
			break;
		case KeyEvent.VK_SPACE:
			isReset = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_1:
			pressed[0] = false;
			break;
		case KeyEvent.VK_2:
			pressed[1] = false;
			break;
		case KeyEvent.VK_3:
			pressed[2] = false;
			break;
		case KeyEvent.VK_4:
			pressed[3] = false;
			break;
		case KeyEvent.VK_A:
			pressed[4] = false;
			break;
		case KeyEvent.VK_B:
			pressed[5] = false;
			break;
		case KeyEvent.VK_C:
			pressed[6] = false;
			break;
		case KeyEvent.VK_D:
			pressed[7] = false;
			break;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new Main("Aashish is so boss that he is boss");
	}
}
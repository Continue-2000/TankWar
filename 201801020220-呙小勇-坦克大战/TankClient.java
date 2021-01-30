package tankwar;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;


public class TankClient extends Frame {
	MainTank tank = new MainTank(350, 480, this);// �½�̹�˶���
	// ��Ŵ��̹�˵��б�
	public static List<Tank> liveTank = new ArrayList<Tank>();
	// public static List<Tank> liveEnemyTank = new ArrayList<Tank>();
	public static List<Missile> liveMissile = new ArrayList<Missile>();

	public static List<Wall> liveWall = new ArrayList<Wall>();

	// Image tankCurImage = tank.getTankUImage();// ���浱ǰ̹��ͼ��
	// ����ͼƬ����Ҫ�������ݻ���ͼƬ�ϣ�һ������ʾ����
	Image virtualImage = null;
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 500;
	private final List<Missile> missileList = tank.getMissileList();

	public static int getWindowWidth() {
		return WINDOW_WIDTH;
	}

	public static int getWindowHeight() {
		return WINDOW_HEIGHT;
	}

	public void drawGameOver(Graphics g) {
		Image img = new ImageIcon(System.getProperty("user.dir")
				+ "\\src\\pic\\bullet").getImage();
		g.drawImage(img, this.getWindowWidth() / 2 - img.getWidth(null),
				this.getWindowHeight() / 2 - img.getHeight(null), 100, 100,
				null);
	}

	// ��дpaint����
	@Override
	public void paint(Graphics g) {

		tank.draw(g);
		if (!tank.isLive()) {
			drawGameOver(g);
			
		}
		

		for (int i = 0; i < liveWall.size(); i++) {
			Wall w = liveWall.get(i);
			if (!w.isLive()) {
				liveWall.remove(i);
				continue;
			}
			w.draw(g);
	
		}
		for (int i = 1; i < liveTank.size(); i++) {
			if (!liveTank.get(i).isLive()) {
				liveTank.remove(i);
				continue;
			}
			liveTank.get(i).draw(g);
		}
		
		// ���������ӵ�
		if (missileList.size() > 0) {
			// û�в����쳣����foreach�в����쳣
			for (int i = 0; i < missileList.size(); i++) {
				if (!missileList.get(i).isLive()) {// �ӵ�����
					missileList.remove(i);
					continue;
				}
				missileList.get(i).draw(g);
				missileList.get(i).move();
			}
		}
		for (int i = 0; i < liveMissile.size(); i++) {
			if (!liveMissile.get(i).isLive()) {// �ӵ�����
				liveMissile.remove(i);
				continue;
			}
			liveMissile.get(i).draw(g);
			liveMissile.get(i).move();
		}
		// �����ӵ�����
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.setColor(c);
		
	}

	// ˫���壬�����˸,ԭ��
	@Override
	public void update(Graphics g) {
		if (virtualImage == null) {
			virtualImage = this.createImage(WINDOW_WIDTH, WINDOW_HEIGHT);// һ�δ�������
		}
		Graphics virtualG = virtualImage.getGraphics();// ���ͼƬ�Ļ���
		Color c = virtualG.getColor();

		virtualG.setColor(this.getBackground());
		virtualG.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

		virtualG.setColor(c);
		paint(virtualG);

		g.drawImage(virtualImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);
	}

	public void getFrame() {

				String iconPath = System.getProperty("user.dir")
				+ "\\src\\pic\\home.jpg";
		ImageIcon image = new ImageIcon(iconPath);
		
		this.setIconImage(image.getImage());// ����ͼ��
		this.setTitle("̹�˴�ս02");
	
		this.setBackground(Color.black);
		this.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);// ���ô�С
		this.setResizable(false);// ���ɸı��С
		this.setVisible(true);

		// �ع� �� ��������������̹������-��Ԫ����ײ
		// ����������Ϣ
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				tank.keyPress(e);
			}
		});
		// ��Ӵ��ڹرռ�����
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
			}

		});
		liveTank.add(tank);// �±�0
		

		initEnemyTank();// �±�1-size()
		initWall();
		new Thread(new PaintThread()).start();
		new Thread(new EnemyThread()).start();
	}

	public void initEnemyTank() {
		for (int i = 0; i < 10; i++) {
			liveTank.add(new EnemyTank(i * 50, 30, this));
		}
	}

	public void initWall() {
		for (int i = 0; i < 30; i++) {
			liveWall.add(new Wall(i * 20, 30 + 100, this, false, false));
		}
		for (int i = 0; i < 30; i++) {
			liveWall.add(new Wall(i * 20, 30 + 80, this, false, false));
		}
		for (int i = 0; i < 30; i++) {
			liveWall.add(new Wall(i * 40, 30 + 200, this, false, false));
		}
		for (int i = 0; i < 20; i++) {
			liveWall.add(new Wall(i * 20, 30 + 300, this, false, false));
		}
		for (int i = 0; i < 20; i++) {
			liveWall.add(new Wall(i * 20, 30 + 320, this, false, false));
		}
		for (int i = 200; i <=260; ) {
			liveWall.add(new Wall(i, 30 + 410, this, false, false));
			i+=20;
		}
		for (int j = 440; j <=500; ) {
			liveWall.add(new Wall(200, j, this, false, false));
			j+=20;
		}
		for (int j = 440; j <=500; ) {
			liveWall.add(new Wall(260, j, this, false, false));
			j+=20;
		}
			
		
		for (int i = 0; i < 6; i++) {
			liveWall.add(new Wall(i * 40 + 100, 300, this, true, false));
		}
		for (int i = 0; i < 6; i++) {
			liveWall.add(new Wall(i * 40 + 50, 170, this, true, false));
		}
		
	}

	// ��Ȼ��̹�˵��ƶ����ɣ���ȻҪ��̹�����ж��壬�������
	class EnemyThread implements Runnable {

		@Override
		public void run() {
			while (liveTank.size() > 1) {
				for (int i = 1; i < liveTank.size(); i++) {
					Tank t = liveTank.get(i);
					
					t.move();

				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	class PaintThread implements Runnable {
		// ÿ��40MS���ػ�����
		@Override
		public void run() {
			while (true) {
				repaint();

				// �ӳ�40ms�ػ����н���
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}

	}

	// ��̹���ƶ��߳�
	class TankMoveThread implements Runnable {
		@Override
		public void run() {

		}
	}

}

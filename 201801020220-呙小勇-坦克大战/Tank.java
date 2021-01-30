package tankwar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;


// 所有坦克的 父类
public class Tank {
	private String name;// 名字
	private boolean good;
	private int SPEED = 8;

	private static Random rand = new Random();

	protected String tankUPath = System.getProperty("user.dir") + "\\src\\pic\\mytank1.jpg";// 向上坦克图片所在文件路径
	protected String tankRPath = System.getProperty("user.dir") + "\\src\\pic\\mytank2.jpg";// 向右坦克图片所在文件路径
	protected String tankDPath = System.getProperty("user.dir") + "\\src\\pic\\mytank3.jpg";// 向下坦克图片所在文件路径
	protected String tankLPath = System.getProperty("user.dir") + "\\src\\pic\\mytank4.jpg";// 向左坦克图片所在文件路径

	protected Image tankUImage = new ImageIcon(tankUPath).getImage();
	protected Image tankRImage = new ImageIcon(tankRPath).getImage();
	protected Image tankDImage = new ImageIcon(tankDPath).getImage();
	protected Image tankLImage = new ImageIcon(tankLPath).getImage();
	protected Image tankCurImage = getTankUImage();

	private int initXPos;// 坦克初始坐标
	private final int initYPos = 100;// 标题栏宽度
	private int curXPos; // 坦克当前坐标
	private int curYPos;

	private final int minX = 0;
	private final int minY = 30;// 标题栏宽度
	private int maxX;
	private int maxY;

	private static final int TANK_WIDTH = 20;
	private static final int TANK_HEIGHT = 20;

	private Direction dir = Direction.W;

	private boolean live = true;

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	TankClient tc;

	public Tank() {
	}

	public Tank(int curX, int curY, TankClient tc) {
		this.tc = tc;
		curXPos = curX;
		curYPos = curY;
	}

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public int getCurX() {
		return curXPos;
	}

	public void setCurX(int curXPos) {
		this.curXPos = curXPos;
	}

	public int getCurY() {
		return curYPos;
	}

	public void setCurY(int curYPos) {
		this.curYPos = curYPos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// ------------------------------------------------------------
	public Image getTankUImage() {
		return tankUImage;
	}

	public Image getTankRImage() {
		return tankRImage;
	}

	public Image getTankDImage() {
		return tankDImage;
	}

	public Image getTankLImage() {
		return tankLImage;
	}

	// ------------------------------------------------------------
	public String getTankUPath() {
		return tankUPath;
	}

	public void setTankUPath(String tankUPath) {
		this.tankUPath = tankUPath;
	}

	public String getTankRPath() {
		return tankRPath;
	}

	public void setTankRPath(String tankRPath) {
		this.tankRPath = tankRPath;
	}

	public String getTankDPath() {
		return tankDPath;
	}

	public void setTankDPath(String tankDPath) {
		this.tankDPath = tankDPath;
	}

	public String getTankLPath() {
		return tankLPath;
	}

	public void setTankLPath(String tankLPath) {
		this.tankLPath = tankLPath;
	}

	public static int getTankWidth() {
		return TANK_WIDTH;
	}

	public static int getTankHeight() {
		return TANK_HEIGHT;
	}

	public Image getTankCurImage() {
		return tankCurImage;
	}

	public void setTankCurImage(Image tankCurImage) {
		this.tankCurImage = tankCurImage;
	}

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	public int getSPEED() {
		return SPEED;
	}

	public void setSPEED(int sPEED) {
		SPEED = sPEED;
	}

	// 坦克移动
	public void tankMoveUp() {
		curYPos -= SPEED;
	}

	public void tankMoveRight() {
		curXPos += SPEED;
	}

	public void tankMoveDown() {
		curYPos += SPEED;
	}

	public void tankMoveLeft() {
		curXPos -= SPEED;
	}

	public Rectangle getRec() {
		return new Rectangle(curXPos, curYPos, TANK_WIDTH, TANK_HEIGHT);
	}

	// 判断坦克是否在边界位置
	public boolean isInArea(Direction d) {
		switch (d) {
		
		case W:
			if (curYPos - SPEED < 30)
				return false;
			break;
		case S:
			if (curYPos + SPEED + TANK_HEIGHT > tc.getWindowHeight())
				return false;
			break;
		case A:
			if (curXPos - SPEED < 0)
				return false;
			break;
		case D:
			if (curXPos + SPEED + TANK_WIDTH > tc.getWindowWidth())
				return false;
			break;
		}
		
		return true;
	}

	public boolean judgeWall(Direction d) {// d表示一条和坦克方向对着的线线
		for (int i = 0; i < tc.liveWall.size(); i++)
			// 和墙相交
			if (this.getRec().intersectsLine(tc.liveWall.get(i).getLine(d))
					|| this.getRec().intersects(tc.liveWall.get(i).getRec()))
				return false;
		return true;

	}

	// 取反方向 目前没用到
	private Direction reDirection(Direction d) {
		switch (d) {
		case W:
			return Direction.W;
		case S:
			return Direction.S;
		case A:
			return Direction.A;
		case D:
			return Direction.D;
		}
		return null;
	}

	private int step = rand.nextInt(12) + 3;

	// ------------------------------------------------------------
	// tank移动方法
	public void move() {
		if (!isInArea(this.getDir())) {
			step = 0;
			// return;
		}

		switch (this.dir) {
		case W:
			tankCurImage = tankUImage;
			// tankMoveUp();
			if (isInArea(Direction.W))
				tankMoveUp();
			// 判断是否和墙相交
			if (!judgeWall(Direction.D))
				tankMoveDown();
			break;
		case S:
			tankCurImage = tankDImage;
			// tankMoveDown();
			if (isInArea(Direction.D))
				tankMoveDown();
			if (!judgeWall(Direction.W))
				tankMoveUp();
			break;
		case A:
			tankCurImage = tankLImage;
			// tankMoveLeft();
			if (isInArea(Direction.A))
				tankMoveLeft();
			if (!judgeWall(Direction.D))
				tankMoveRight();
			break;
		case D:
			// tankMoveRight();
			tankCurImage = tankRImage;
			if (isInArea(Direction.D))
				tankMoveRight();
			if (!judgeWall(Direction.A))
				tankMoveLeft();
			break;
		}
		Direction[] array = Direction.values();// 将枚举转换为数组
		if (!good) {
			Direction dir = array[rand.nextInt(array.length)];
			if (step == 0) {
				this.setDir(dir);
				// tc.liveMissile.add(this.fire());
				step = rand.nextInt(100) + 3;

			} else
				step--;
			if (rand.nextInt(50) > 47)
				tc.liveMissile.add(this.fire());
		}
		
	}

	public void draw(Graphics g) {

		if (this.live)
			// 画出坦克
			g.drawImage(tankCurImage, getCurX(), getCurY(), getTankWidth(), getTankHeight(), tc.getBackground(), null);
	}

	public Missile fire() {

		int missileX = this.curXPos;
		int missileY = this.curYPos;

		// 算出子弹左上角坐标
		switch (this.dir) {
		case W:
			missileX = this.curXPos + TANK_WIDTH / 2 - Missile.getR() / 2;
			missileY = this.curYPos - Missile.getR();
			break;
		case S:
			missileX = this.curXPos + TANK_WIDTH / 2 - Missile.getR() / 2;
			missileY = this.curYPos + TANK_HEIGHT;
			break;
		case A:
			missileX = this.curXPos - Missile.getR();
			missileY = this.curYPos + TANK_HEIGHT / 2 - Missile.getR() / 2;
			break;
		case D:
			missileX = this.curXPos + TANK_WIDTH;
			missileY = this.curYPos + TANK_HEIGHT / 2 - Missile.getR() / 2;
			break;
		}
		// new 子弹，传入坦克当前位置及方向
		Missile m = new Missile(missileX, missileY, this.getDir(), tc, this.isGood());
		return m;
	}
}

class EnemyTank extends Tank {
	EnemyTank() {
		// 改路径
		this.setTankDPath(System.getProperty("user.dir") + "\\src\\pic\\mytank3.jpg");
		this.setTankUPath(System.getProperty("user.dir") + "\\src\\pic\\mytank1.jpg");
		this.setTankLPath(System.getProperty("user.dir") + "\\src\\pic\\mytank4.jpg");
		this.setTankRPath(System.getProperty("user.dir") + "\\src\\pic\\mytank2.jpg");
		this.setCurX(0);
		this.setCurY(30);
	}

	EnemyTank(int curX, int curY, TankClient tc) {
		super(curX, curY, tc);
		this.setTankDPath(System.getProperty("user.dir") + "\\src\\pic\\tankdown.jpg");
		// 此处路径曾出错导致坦克向上图片为空
		this.setTankUPath(System.getProperty("user.dir") + "\\src\\pic\\tankup.jpg");
		this.setTankLPath(System.getProperty("user.dir") + "\\src\\pic\\tankleft.jpg");
		this.setTankRPath(System.getProperty("user.dir") + "\\src\\pic\\tankright.jpg");
		tankUImage = new ImageIcon(tankUPath).getImage();
		tankDImage = new ImageIcon(tankDPath).getImage();
		tankLImage = new ImageIcon(tankLPath).getImage();
		tankRImage = new ImageIcon(tankRPath).getImage();
		this.tankCurImage = tankUImage;
		this.setDir(Direction.S);
		this.setGood(false);

	}

}

class MainTank extends Tank {
	// 子弹容器
	private static final List<Missile> missileList = new ArrayList<Missile>();

	public static List<Missile> getMissileList() {
		return missileList;
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
	}

	// 对主坦克的操作
	public void keyPress(KeyEvent e) {
		switch (e.getKeyCode()) {
		case (KeyEvent.VK_W):// 箭头上按下
			if (this.getDir() == Direction.W)
				move();
			else {
				this.tankCurImage = this.tankUImage;
			}
			setDir(Direction.W);
			break;

		case (KeyEvent.VK_D):// 箭头右按下
			if (this.getDir() == Direction.D)
				move();
			else {
				this.tankCurImage = this.tankRImage;
			}
			setDir(Direction.D);
			break;
		case (KeyEvent.VK_S):// 箭头下按下
			if (this.getDir() == Direction.S)
				move();
			else {
				this.tankCurImage = this.tankDImage;
			}
			setDir(Direction.S);
			break;
		case (KeyEvent.VK_A):// 箭头左按下

			if (this.getDir() == Direction.A)
				move();
			else {
				this.tankCurImage = this.tankLImage;
			}
			setDir(Direction.A);
			break;
		case (KeyEvent.VK_SPACE):// 发子弹
			// 可以加声音
			missileList.add(fire());
			break;
		}
	}

	MainTank() {
	}

	MainTank(int curX, int curY, TankClient tc) {
		super(curX, curY, tc);
		this.setGood(true);
		super.setSPEED(5);
	}
}

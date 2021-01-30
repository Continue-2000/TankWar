package tankwar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import javax.swing.ImageIcon;


public class Wall {
	private final int curX;
	private final int curY;

	private final String brickWallPath = System.getProperty("user.dir")
			+ "\\src\\pic\\p0.jpg";
	private final String steelWallPath = System.getProperty("user.dir")
			+ "\\src\\pic\\steelwall.png";
	private final String homeWallPath = System.getProperty("user.dir")
			+ "\\src\\pic\\home.jpg";

	private Image brickWall;//可以损坏的墙
	private Image steelWall;//不可以损坏的墙
	private Image homeWall;//家

	private static final int WALL_WIDTH = 20;
	private static final int WALL_HEIGHT = 20;

	private final TankClient tc;
	private boolean live = true;
	private final Image img;
	private final Image img2;
	private final boolean isSteel;
	private final boolean ishome;

	public Wall(int curX, int curY, TankClient tc, boolean isSteel,boolean ishome) {
		super();
		this.curX = curX;
		this.curY = curY;
		this.tc = tc;
		this.isSteel = isSteel;
		this.ishome = ishome;
		img = isSteel ? getSteelWall() : getBrickWall();// 选择砖墙还是铁墙
		img2 = gethomeWall() ;// 选择家还是铁墙
	}

	public boolean isSteel() {
		return isSteel;
	}
	public boolean ishome() {
		return ishome;
	}

	public int getCurX() {
		return curX;
	}

	public int getCurY() {
		return curY;
	}

	public void draw(Graphics g) {
		if (live) {
			g.drawImage(img, curX, curY, WALL_WIDTH, WALL_HEIGHT, null);
			g.drawImage(img2, 230, 480, 20, 20, null);
			
		
		}
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public static int getWallWidth() {
		return WALL_WIDTH;
	}

	public static int getWallHeight() {
		return WALL_HEIGHT;
	}

	public Image getBrickWall() {
		Image brickWall = new ImageIcon(brickWallPath).getImage();
		return brickWall;
	}

	public void setBrickWall(Image brickWall) {
		this.brickWall = brickWall;
	}

	public Image getSteelWall() {
		Image steelWall = new ImageIcon(steelWallPath).getImage();
		return steelWall;
	}

	public void setSteelWall(Image steelWall) {
		this.steelWall = steelWall;
	}
	public Image gethomeWall() {
		Image homeWall = new ImageIcon(homeWallPath).getImage();
		return homeWall;
	}

	public void sethomeWall(Image homeWall) {
		this.homeWall = homeWall;
	}


	public Rectangle getRec() {
		return new Rectangle(curX, curY, WALL_WIDTH, WALL_HEIGHT);
	}

	public Line2D getLine(Direction d) {
		switch (d) {
		case W:
			return new Line2D.Double(curX, curY, curX + WALL_WIDTH, curY);
		case S:
			return new Line2D.Double(curX, curY + WALL_HEIGHT, curX
					+ WALL_WIDTH, curY + WALL_HEIGHT);
		case A:
			return new Line2D.Double(curX, curY, curX, curY + WALL_HEIGHT);
		case D:
			return new Line2D.Double(curX + WALL_HEIGHT, curY, curX
					+ WALL_WIDTH, curY + WALL_HEIGHT);
		}
		return null;
	}
}

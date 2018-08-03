import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.*;
class bmb extends JFrame implements KeyListener{    
	static int xPixel = 70;   // Player1
	static int yPixel = 90;
	static int xPixel1 = 670; // Player2
	static int yPixel1 = 690;
	//public JTextField Score;
	//private JPanel panel;
	int ok;
	int Player1=1,Player2=1; //Player life
	int x=1,y=1,x1=13,y1=13; //coord Player1 & PLayer2
	long time1,time2,t1,t2;   //time form bomb & explosion
	int v[][]= {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,},  //map
			{1,0,0,0,2,2,2,2,2,2,2,0,0,0,1,},
			{1,0,1,2,1,2,1,0,1,2,1,0,1,0,1,},
			{1,0,2,2,2,0,2,0,2,2,2,0,2,0,1,},
			{1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,},
			{1,2,0,2,2,2,2,2,2,2,0,2,2,0,1,},
			{1,0,1,2,1,2,1,0,1,2,1,0,1,0,1,},
			{1,2,0,2,2,2,2,2,2,2,2,2,2,2,1,},
			{1,2,1,2,1,2,1,0,1,2,1,2,1,2,1,},
			{1,2,2,2,2,0,2,0,0,2,2,0,0,0,1,},
			{1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,},
			{1,0,0,2,2,2,2,2,2,2,0,2,2,0,1,},
			{1,0,1,2,1,2,1,0,1,2,1,0,1,0,1,},
			{1,0,0,0,2,2,2,2,2,2,2,0,0,0,1,},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,},};
	int i,j,ok1=0,ok2=0,cx,cy,cx1,cy1,c1,c2,C1,C2,bomb1,bomb2,Bomb1Level=1,Bomb2Level=1;
	Image Image,Image1,Image2,Image3,Image4,Image5,ImageC,ImageL,ImageR,ImageU,ImageD,ImageP,ImageV,ImageO, offScreenImage;
	Graphics offScreenGraphics; 
	public bmb() {
		try {
			Image = Toolkit.getDefaultToolkit().getImage("./ImagesRes/P1.png");
			Image1 = Toolkit.getDefaultToolkit().getImage("./ImagesRes/P2.png");
			Image2 = Toolkit.getDefaultToolkit().getImage("./ImagesRes/barrel.png"); 
			Image3 = Toolkit.getDefaultToolkit().getImage("./ImagesRes/block.png");
			Image4 = Toolkit.getDefaultToolkit().getImage("./ImagesRes/bmb1.png");
			Image5 = Toolkit.getDefaultToolkit().getImage("./ImagesRes/bmb2.png");
			ImageC = Toolkit.getDefaultToolkit().getImage("./ImagesRes/center.png");
			ImageL = Toolkit.getDefaultToolkit().getImage("./ImagesRes/left.png");
			ImageR = Toolkit.getDefaultToolkit().getImage("./ImagesRes/right.png");
			ImageU = Toolkit.getDefaultToolkit().getImage("./ImagesRes/up.png");
			ImageD = Toolkit.getDefaultToolkit().getImage("./ImagesRes/down.png");
			ImageP = Toolkit.getDefaultToolkit().getImage("./ImagesRes/powerup.png");
			ImageV = Toolkit.getDefaultToolkit().getImage("./ImagesRes/vertical.png");
			ImageO = Toolkit.getDefaultToolkit().getImage("./ImagesRes/orizontal.png");
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		setSize(800,800);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(this);
	}
	public void update(Graphics g) {
		paint(g);
	}     
	public void paint(Graphics g) {

		int width  = getWidth();
		int height = getHeight();

		if (offScreenImage == null) {
			offScreenImage    = createImage(width, height);
			offScreenGraphics = offScreenImage.getGraphics();
		}
		offScreenGraphics.clearRect(0, 0, width + 1, height + 1);
		for(i=0;i<15;i++)
			for(j=0;j<15;j++)
			{
				if(v[i][j]==2)
					offScreenGraphics.drawImage(Image2, 20+j*50, 38+i*50, this);
				if(v[i][j]==1)
					offScreenGraphics.drawImage(Image3, 20+j*50, 40+i*50, this);
				if(v[i][j]==3)
					offScreenGraphics.drawImage(ImageP, 20+j*50, 40+i*50, this);
			}
		if(ok1==1) //if bomb1 is placed
			{
				repaint();
				offScreenGraphics.drawImage(Image4, cx-10, cy-10, this);
				long Time1 = System.nanoTime() - time1;
				if(Time1/1000000>3000) //3 seconds explode time
					{
						ok1=0;
						boom(c2,c1,Bomb1Level);  //explode
						bomb1=1;
						v[c2][c1]=0;
						t1=System.nanoTime();
						repaint();
					}
			}
		if(ok2==1)
			{
				repaint();
				offScreenGraphics.drawImage(Image5, cx1-10, cy1-10, this);
				long Time2 = System.nanoTime() - time2;
				if(Time2/1000000>3000)
				{
					ok2=0;
					boom(C2,C1,Bomb2Level);
					v[C2][C1]=0;
					bomb2=1;
					t2=System.nanoTime();
					repaint();
				}
			}
		if(bomb1==1)
		{
				repaint();
				offScreenGraphics.drawImage(ImageC, cx-20, cy-10, this);
				if(Bomb1Level==1)
				{
					offScreenGraphics.drawImage(ImageL, cx-70, cy-10, this);
					offScreenGraphics.drawImage(ImageR, cx+25, cy-10, this);
					offScreenGraphics.drawImage(ImageU, cx-20, cy-55, this);
					offScreenGraphics.drawImage(ImageD, cx-20, cy+35, this);
				}
				else
				{
					for(int i=1;i<Bomb1Level;i++)
						{
						offScreenGraphics.drawImage(ImageO, cx-20-50*i, cy-10, this);
						offScreenGraphics.drawImage(ImageO, cx-20+50*i, cy-10, this);
						offScreenGraphics.drawImage(ImageV, cx-20, cy-10-50*i, this);
						offScreenGraphics.drawImage(ImageV, cx-20, cy-10+50*i, this);
						}
					offScreenGraphics.drawImage(ImageL, cx-20-50*Bomb1Level, cy-10, this);
					offScreenGraphics.drawImage(ImageR, cx-20+50*Bomb1Level, cy-10, this);
					offScreenGraphics.drawImage(ImageU, cx-20, cy-10-50*Bomb1Level, this);
					offScreenGraphics.drawImage(ImageD, cx-20, cy-10+50*Bomb1Level, this);
						
				}
				long Time1 = System.nanoTime() - t1;
				if(Time1/1000000>1000) //1 second explode
				{
						bomb1=0;
						repaint();
				}
		}
		if(bomb2==1)
		{
				repaint();
				offScreenGraphics.drawImage(ImageC, cx1-20, cy1-10, this);
				if(Bomb2Level==1)
				{
					offScreenGraphics.drawImage(ImageL, cx1-70, cy1-10, this);
					offScreenGraphics.drawImage(ImageR, cx1+25, cy1-10, this);
					offScreenGraphics.drawImage(ImageU, cx1-20, cy1-55, this);
					offScreenGraphics.drawImage(ImageD, cx1-20, cy1+35, this);
				}
				else
				{
					for(int i=1;i<Bomb2Level;i++)
						{
						offScreenGraphics.drawImage(ImageO, cx1-20-50*i, cy1-10, this);
						offScreenGraphics.drawImage(ImageO, cx1-20+50*i, cy1-10, this);
						offScreenGraphics.drawImage(ImageV, cx1-20, cy1-10-50*i, this);
						offScreenGraphics.drawImage(ImageV, cx1-20, cy1-10+50*i, this);
						}
					offScreenGraphics.drawImage(ImageL, cx1-20-50*Bomb2Level, cy1-10, this);
					offScreenGraphics.drawImage(ImageR, cx1-20+50*Bomb2Level, cy1-10, this);
					offScreenGraphics.drawImage(ImageU, cx1-20, cy1-10-50*Bomb2Level, this);
					offScreenGraphics.drawImage(ImageD, cx1-20, cy1-10+50*Bomb2Level, this);
						
				}
				long Time2 = System.nanoTime() - t2;
				if(Time2/1000000>1000) //2 seconds explode
				{
						bomb2=0;
						repaint();
				}
		}
		if(Player1>0)
		offScreenGraphics.drawImage(Image, xPixel, yPixel, this);
		if(Player2>0)
		offScreenGraphics.drawImage(Image1, xPixel1, yPixel1, this);
		g.drawImage(offScreenImage, 0, 0, this);
		/*Score = new JTextField();
    	Score.setSize(50, 50);
    	Score.setLocation(0,600);
    	panel.add(Score);*/
	}
	public void boom(int yy,int xx,int player) //clears barrels
	{
		die(yy,xx);
		int i=0;
		while(++i<=player)
			{
			if(v[yy-i][xx]==2)
				{
					v[yy-i][xx]=0;
					power(yy-i,xx);
				}
			else 
				{
				if(v[yy-i][xx]==1)
				i=player+1;
				else die(yy-i,xx);
				}
			}
		i=0;
		while(++i<=player)
			if(v[yy][xx-i]==2)
				{
					v[yy][xx-i]=0;
					power(yy,xx-i);
				}
			else 
			{
				if(v[yy][xx-i]==1)
					i=player+1;
				else die(yy,xx-i);
			}
		i=0;
		while(++i<=player)
			if(v[yy+i][xx]==2)
				{
					v[yy+i][xx]=0;
					power(yy+i,xx);
				}
			else 
				{
				if(v[yy+i][xx]==1)
				i=player+1;
				else die(yy+i,xx);
				}
		i=0;
		while(++i<=player)
			if(v[yy][xx+i]==2)
				{
					v[yy][xx+i]=0;
					power(yy,xx+i);
				}
			else 
			{
				if(v[yy][xx+i]==1)
				i=player+1;
				else die(yy,xx+i);
			}
	}
	public void die(int yy,int xx)
	{
		if(yy==y&&xx==x)
			Player1=0;
		if(yy==y1&&xx==x1)
			Player2=0;
	}
	public void power(int a,int b)
	{
		int rand = ThreadLocalRandom.current().nextInt(1, 10 + 1);
		if(rand==3)
			v[a][b]=3;
	}
	public void atrib(int a,int b,int player)
	{
		if(v[a][b]==3)
		{
			if(player==1)
				{
					Bomb1Level++;
					v[a][b]=0;
				}
			else {
					Bomb2Level++;
					v[a][b]=0;
				}
		}
	}
	public int valid (int i,int j) //if moving position is valid
	{
		if(v[i][j]!=1&&v[i][j]!=2&&v[i][j]!=5)
			return 1;
		return 0;
	}
	public void keyPressed(KeyEvent ke) {
		if(Player1>0)
		{switch (ke.getKeyCode()) {
		case KeyEvent.VK_RIGHT: {
			{
				if(valid(y,x+1)==1)
				{
					xPixel+=50;
					x++;
					atrib(y,x,1);
				}
			}
		}
		break;
		case KeyEvent.VK_LEFT: {
			{
				if(valid(y,x-1)==1)
				{
					xPixel-=50;
					x--;
					atrib(y,x,1);
				}
			}
		}
		break;
		case KeyEvent.VK_DOWN: {
			{
				if(valid(y+1,x)==1)
				{
					yPixel+=50;
					y++;
					atrib(y,x,1);
				}
			};
		}
		break;
		case KeyEvent.VK_UP: {
			{
				if(valid(y-1,x)==1)
				{
					yPixel-=50;
					y--;
					atrib(y,x,1);
				}
			}
		}
		break;
		case '/': {
			{
				if(ok1==0&&bomb1==0)
				{ok1=1;
				cx=xPixel+20;
				c1=x;
				cy=yPixel+10;
				c2=y;
				v[c2][c1]=5;
				time1=System.nanoTime();}
			}
		}
		break;
		}}
		repaint();
		if(Player2>0)
		{switch (ke.getKeyChar()) {
		case 'd': {
			{
				if(valid(y1,x1+1)==1)
				{
					xPixel1+=50;
					x1++;
					atrib(y1,x1,2);
				}
			}
		}
		break;
		case 'a': {
			{
				if(valid(y1,x1-1)==1)
				{
					xPixel1-=50;
					x1--;
					atrib(y1,x1,2);
				}
			}
		}
		break;
		case 's': {
			{
				if(valid(y1+1,x1)==1)
				{
					yPixel1+=50;
					y1++;
					atrib(y1,x1,2);
				}
			};
		}
		break;
		case 'w': {
			{
				if(valid(y1-1,x1)==1)
				{
					yPixel1-=50;
					y1--;
					atrib(y1,x1,2);
				}
			}
		}
		break;
		case 'f': {
		{
			if(ok2==0&&bomb2==0)
			{
			ok2=1;
			cx1=xPixel1+20;
			C1=x1;
			cy1=yPixel1+10;
			C2=y1;
			time2=System.nanoTime();
			v[C2][C1]=5;
			}
		}
		}
		break;
		}
		}
		repaint();
	}
	public static void main(String args[]){

		bmb me = new bmb();

	}
	//When a key is typed (once)
	public void keyTyped(KeyEvent ke) {}    
	//When a key is released (typed or pressed)
	public void keyReleased(KeyEvent ke) {
		repaint();
		try { Thread.sleep(50); }   
		catch (InterruptedException e) { System.err.println("sleep exception"); }
	}

}
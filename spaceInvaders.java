/*
spaceInvaders.java
Nafiz Hasan

*/

//importing
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.MouseInfo;

public class spaceInvaders extends JFrame implements ActionListener
{
	Timer myTimer;
	GamePanel game;

    public spaceInvaders()
    {
		super("Space Invaders");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(650,700);

		myTimer = new Timer(10, this);	 // trigger every 10 ms

		game = new GamePanel(this);
		add(game);

		setResizable(false);
		setVisible(true);
    }

	public void start()
	{
		myTimer.start();
	}

	public void actionPerformed(ActionEvent evt)
	{
		game.actions(); //calling all methods
		game.repaint(); //drawing the scene
	}

    public static void main(String[] arguments)
    {
		spaceInvaders frame = new spaceInvaders();
    }
}

class GamePanel extends JPanel implements KeyListener
{
	//sounds
	File shootwavFile = new File("sounds/shoot.wav");
	File killedwavFile = new File("sounds/invaderkilled.wav");
	File ufowavFile = new File("sounds/ufo.wav");
	File deathwavFile = new File("sounds/explosion.wav");
	AudioClip sound;

	//variables
	private int roundcounter = 0;
	private int score;
	private int playerx,playery;
	private int bulletx,bullety;
	private boolean []keys;
	private spaceInvaders mainFrame;
	private boolean bullet = false;
	private boolean amovingRight = true;
	private boolean amovingLeft = false;
	private boolean amovingDown = false;
	private boolean hit = false;
	private boolean shieldhit = false;
	private boolean end = false;
	private int alienbulletx = -10;
	private int alienbullety = -10;
	private ArrayList<Alien>aliens = new ArrayList<Alien>();
	private ArrayList<Shield>shields = new ArrayList<Shield>();
	private ArrayList<Alien>potentialshooters = new ArrayList<Alien>();
	private boolean AlienCanShoot = false;
	private boolean AliensFirstShot = false;
	private int lives = 3;
	private int livesvisualx,livesvisualy;
	private int specialalienx,specialalieny;
	private boolean MoveSpecialAlien = false;
	private boolean alienPassed = false;
	private int explodewait = 0;

	//images
	private Image back;
	private Image ufosprite;
	private Image livessprite;
	private Image playersprite;
	private Image aliensprite1;
	private Image aliensprite2;
	private Image aliensprite3;
	private Image aliensprite4;
	private Image aliensprite5;
	private Image aliensprite6;
	private Image explodesprite;

	public GamePanel(spaceInvaders m)
	{
		keys = new boolean[KeyEvent.KEY_LAST+1];

		//resizing images
		back = new ImageIcon("OuterSpace.png").getImage().getScaledInstance(1000,700,Image.SCALE_SMOOTH);
		ufosprite = new ImageIcon("sprites/ufosprite.png").getImage().getScaledInstance(50,17,Image.SCALE_SMOOTH);
		livessprite = new ImageIcon("sprites/playersprite.png").getImage().getScaledInstance(25,15,Image.SCALE_SMOOTH);
		playersprite = new ImageIcon("sprites/playersprite.png").getImage().getScaledInstance(50,25,Image.SCALE_SMOOTH);
		aliensprite1 = new ImageIcon("sprites/spacesprites001.png").getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH);
		aliensprite2 = new ImageIcon("sprites/spacesprites002.png").getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH);
		aliensprite3 = new ImageIcon("sprites/spacesprites003.png").getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH);
		aliensprite4 = new ImageIcon("sprites/spacesprites004.png").getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH);
		aliensprite5 = new ImageIcon("sprites/spacesprites005.png").getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH);
		aliensprite6 = new ImageIcon("sprites/spacesprites006.png").getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH);
		explodesprite = new ImageIcon("sprites/explodesprite.png").getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH);

		mainFrame = m;
		//setting starting variables
	    playerx = 300;
        playery = 580;
	    bulletx = playerx;
        bullety = playery+10; //+10 because the bullet is 10 long
        specialalienx = -50;
        specialalieny = 15;
        addKeyListener(this);
        makeAliens();
        makeShields();
	}
	
    public void addNotify()
    {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }

	private void alienShooters()
	{
		potentialshooters.clear();
		int y1,y2,y3,y4,y5,y6,y7,y8,y9,y10,y11;
		Alien alien1,alien2,alien3,alien4,alien5,alien6,alien7,alien8,alien9,alien10,alien11;
		y1=y2=y3=y4=y5=y6=y7=y8=y9=y10=y11=0;
		alien1=alien2=alien3=alien4=alien5=alien6=alien7=alien8=alien9=alien10=alien11=aliens.get(0);
		if(AlienCanShoot == false)
		{
			for(int i=0;i<aliens.size();i++)
			{
				if(aliens.get(i).getposx()==1)
				{
					if(aliens.get(i).getposy()>y1)
					{
						y1=aliens.get(i).getposy();
						alien1=aliens.get(i);
					}
				}
				if(aliens.get(i).getposx()==2)
				{
					if(aliens.get(i).getposy()>y2)
					{
						y2=aliens.get(i).getposy();
						alien2=aliens.get(i);
					}
				}
				if(aliens.get(i).getposx()==3)
				{
					if(aliens.get(i).getposy()>y3)
					{
						y3=aliens.get(i).getposy();
						alien3=aliens.get(i);
					}
				}
				if(aliens.get(i).getposx()==4)
				{
					if(aliens.get(i).getposy()>y4)
					{
						y4=aliens.get(i).getposy();
						alien4=aliens.get(i);
					}
				}
				if(aliens.get(i).getposx()==5)
				{
					if(aliens.get(i).getposy()>y5)
					{
						y5=aliens.get(i).getposy();
						alien5=aliens.get(i);
					}
				}
				if(aliens.get(i).getposx()==6)
				{
					if(aliens.get(i).getposy()>y6)
					{
						y6=aliens.get(i).getposy();
						alien6=aliens.get(i);
					}
				}
				if(aliens.get(i).getposx()==7)
				{
					if(aliens.get(i).getposy()>y7)
					{
						y7=aliens.get(i).getposy();
						alien7=aliens.get(i);
					}
				}
				if(aliens.get(i).getposx()==8)
				{
					if(aliens.get(i).getposy()>y8)
					{
						y8=aliens.get(i).getposy();
						alien8=aliens.get(i);
					}
				}
				if(aliens.get(i).getposx()==9)
				{
					if(aliens.get(i).getposy()>y9)
					{
						y9=aliens.get(i).getposy();
						alien9=aliens.get(i);
					}
				}
				if(aliens.get(i).getposx()==10)
				{
					if(aliens.get(i).getposy()>y10)
					{
						y10=aliens.get(i).getposy();
						alien10=aliens.get(i);
					}
				}
				if(aliens.get(i).getposx()==11)
				{
					if(aliens.get(i).getposy()>y11)
					{
						y11=aliens.get(i).getposy();
						alien11=aliens.get(i);
					}
				}
			}
			if(y1!=0)
			{
				potentialshooters.add(alien1);
			}
			if(y2!=0)
			{
				potentialshooters.add(alien2);
			}
			if(y3!=0)
			{
				potentialshooters.add(alien3);
			}
			if(y4!=0)
			{
				potentialshooters.add(alien4);
			}
			if(y5!=0)
			{
				potentialshooters.add(alien5);
			}
			if(y6!=0)
			{
				potentialshooters.add(alien6);
			}
			if(y7!=0)
			{
				potentialshooters.add(alien7);
			}
			if(y8!=0)
			{
				potentialshooters.add(alien8);
			}
			if(y9!=0)
			{
				potentialshooters.add(alien9);
			}
			if(y10!=0)
			{
				potentialshooters.add(alien10);
			}
			if(y11!=0)
			{
				potentialshooters.add(alien11);
			}
			AlienCanShoot = true;
			AliensFirstShot = true;
		}
	}

	private void makeAliens()
	{
		for (int y = 1; y < 6; y++) //5 rows of aliens
		{
			for (int x = 1; x < 12; x++) //11 columns of aliens
			{
				//creates aliens based on y position for points
				if(y==1)
				{
					aliens.add(new Alien(x*40, y*40, 30,x,y,false)); //spaces the aliens apart by using the for loop x and y value and multiplying by 40
				}
				if(y==2 || y==3)
				{
					aliens.add(new Alien(x*40, y*40, 20,x,y,false));
				}
				if(y==4 || y==5)
				{
					aliens.add(new Alien(x*40, y*40, 10,x,y,false));
				}
			}
		}
	}

	public void makeShields()
	{
		//shield has 3 layers with each block spaced 20 apart
		
		//first layer has 3 blocks
		for(int y = 490; y<510; y+=20)
		{
			for(int x = 110; x<170; x+=20) //has a range of 60 because there are 3 blocks
			{
				shields.add(new Shield(x,y,2)); //uses the for loop numbers to make the x and y of the shields
			}
			for(int x = 290; x<350; x+=20)
			{
				shields.add(new Shield(x,y,2));
			}
			for(int x = 470; x<530; x+=20)
			{
				shields.add(new Shield(x,y,2));
			}
		}
		//the rest have 5 blocks
		for(int y = 510; y<550; y+=20)
		{
			for(int x = 90; x<190; x+=20) //has a range of 100 because there are 5 blocks
			{
				shields.add(new Shield(x,y,2));
			}
			for(int x =270; x<370; x+=20)
			{
				shields.add(new Shield(x,y,2));
			}
			for(int x = 450; x<550; x+=20)
			{
				shields.add(new Shield(x,y,2));
			}
		}
	}

	public void playerMove()
	{
		//allows the player to move left or right if he is in the boundries
		if(keys[KeyEvent.VK_RIGHT] && playerx <= 580)
		{
			playerx += 3; //moves the player by 3
			if(bullet != true) //if the bullet is not shot it moves with the player
			{
				bulletx+=3;
			}
		}
		if(keys[KeyEvent.VK_LEFT] && playerx >= 4)
		{
			playerx -= 3;
			if(bullet != true)
			{
				bulletx-=3;
			}
		}
	}

	public void playerShoot()
	{
		//allows the player to shoot if there is no bullet shot
		if(keys[KeyEvent.VK_SPACE] && bullet == false) 
		{
			bullet=true; //sets bullet shot to true
			//plays shooting sound
		    try{sound = Applet.newAudioClip(shootwavFile.toURL());}
		    catch(Exception e){e.printStackTrace();}
		    sound.play();
		}
		if(bullet == true)
		{
			bullety-= 9; //moves the bullet by 9
			
			//checks if bullet has hit a shield
			for(int i=shields.size()-1; i>=0;i--)
			{
				shieldhit=shields.get(i).checkShieldHit(bulletx,bullety);
				if(shieldhit == true)
				{
					if(shields.get(i).getshieldhp() == 0) //if shield has no hp left it gets removed
					{
						shields.remove(i);
					}
					//resets bullet
					bullety = playery+10;
					bulletx = playerx;
					bullet = false;
				}
			}
			
			//checks if bullet has hit an alien
			for(int i=aliens.size()-1; i>=0;i--)
			{
				hit=aliens.get(i).CheckAlienHit(bulletx,bullety);
				if(hit == true)
				{
					//plays alien dieing sound
				    try{sound = Applet.newAudioClip(killedwavFile.toURL());}
				    catch(Exception e){e.printStackTrace();}
				    sound.play();
					score += aliens.get(i).getalienpoints(); //adds points based on the alien killed
					//resets bullet
					bullety = playery+10;
					bulletx = playerx;
					bullet = false;
				}
			}

			//checks if bullet has hit the ufo
			if(bulletx+2>specialalienx-25 && bulletx+2<specialalienx+25 && bullety>specialalieny-17 && bullety<specialalieny)
			{
				//plays alien dieing sound
			    try{sound = Applet.newAudioClip(killedwavFile.toURL());}
			    catch(Exception e){e.printStackTrace();}
			    sound.play();
			    //ufo is reset back to its position
				specialalienx = -50;
				specialalieny = 15;
				MoveSpecialAlien = false;
				//the points are determined at random
				Random rand = new Random();
				int randomscore = rand.nextInt(10)+1; //picks a number between 1 and 10
				score+=randomscore*100; //multiplies that number by 100 and adds it to the score
				//resets bullet
				bullety = playery+10;
				bulletx = playerx;
				bullet = false;
			}
			
			if(bullety < 0) //if the bullet has went past the screen it gets reset to the players position
			{
				bullety = playery+10;
				bulletx = playerx;
				bullet = false;
			}
		}
	}
	
	public void alienShoot()
	{
		if(AliensFirstShot)
		{
			Random rand = new Random();
			int RandomAlien = rand.nextInt(potentialshooters.size());
			alienbulletx = potentialshooters.get(RandomAlien).getalienx();
			alienbullety = potentialshooters.get(RandomAlien).getalieny();
			AliensFirstShot = false;
		}
		if(AlienCanShoot)
		{
			alienbullety+=3;
			for(int i=shields.size()-1; i>=0;i--)
			{
				shieldhit=shields.get(i).checkShieldHitAlien(alienbulletx,alienbullety);
				if(shieldhit == true)
				{
					if(shields.get(i).getshieldhp() == 0)
					{
						shields.remove(i);
					}
					alienbullety = -100;
					alienbulletx = -100;
					AlienCanShoot = false;
				}
			}
			if(alienbullety>710)
			{
				alienbullety = -100;
				alienbulletx = -100;
				AlienCanShoot = false;
			}
		}
	}
	
	public void alienMove()
	{
		//moves the alien based on if that boolean direction is true
		if(amovingRight == true)
		{
			for(int i=aliens.size()-1; i>=0;i--)
			{
				aliens.get(i).alienRight(); //calls the method in class alien
			}
		}
		if(amovingLeft == true)
		{
			for(int i=aliens.size()-1; i>=0;i--)
			{
				aliens.get(i).alienLeft();
			}
		}
		if(amovingDown == true)
		{
			for(int i=aliens.size()-1; i>=0;i--)
			{
				aliens.get(i).alienDown();
			}
			amovingDown = false; //sets down direction to false because it only moves down once at a time
		}
	}
	
	public void alienDirection(int i)
	{
		//decides direction to move next by seeing if the alien passed a certain point on the screen
		if(aliens.get(i).getalienx() > 550)
		{
			//if alien passes right point it moves left and down
			amovingRight = false;
			amovingLeft = true;
			amovingDown = true;
		}
		if(aliens.get(i).getalienx() < 50)
		{
			//if alien passes left point it moves right and down
			amovingRight = true;
			amovingLeft = false;
			amovingDown = true;
		}
	}
	public boolean roundEnd(int num)
	{
		//checks if the round has ended by checking the size of the alien list
		if(num==0) //if there are no aliens left the round is done
		{
			return true;
		}
		return false;
	}
	
	public void checkAlienPassed(int i)
	{
		//checks if any alien passes the player, which ends the game
		if(aliens.get(i).getalieny() > playery)
		{
			alienPassed = true;
		}
	}
	
	public void checkPlayerHit()
	{
		int x = playerx+37;
		int y = playery;
		int x2 = playerx-13;
		int y2 = playery-25;
		if(alienbulletx>x2 && alienbulletx<x && alienbullety>y2 && alienbullety<y)
		{
			lives-=1;
		    try{sound = Applet.newAudioClip(deathwavFile.toURL());}
		    catch(Exception e){e.printStackTrace();}
		    sound.play();
			alienbullety = -100;
			alienbulletx = -100;
			AlienCanShoot = false;
		}
	}
	public void gameOver()
	{
		if(lives==0 || alienPassed == true)
		{
			System.exit(0);
		}
	}
	public void newRound(boolean end)
	{
		if(end == true)
		{
			roundcounter += 1;
			makeAliens();
			makeShields();
			alienbullety = -100;
			alienbulletx = -100;
			specialalienx = -50;
			specialalieny = 15;
			MoveSpecialAlien = false;
			for(int i=0; i<aliens.size();i++)
			{
				aliens.get(i).increaseSpeed(roundcounter*5);
			}
		}
	}
	public void alienRemove(int i)
	{
		if(aliens.get(i).getexploded() == true)
		{
	    	explodewait += 1;
		}
		if(explodewait == 10)
		{
			aliens.remove(i);
			explodewait = 0;
		}
	}

	public void specialAlien(int RandomChance)
	{
		if(RandomChance==1 && MoveSpecialAlien == false)
		{
			MoveSpecialAlien=true;
		    try{sound = Applet.newAudioClip(ufowavFile.toURL());}
		    catch(Exception e){e.printStackTrace();}
		    sound.play();
		}
		if(MoveSpecialAlien)
		{
			specialalienx+=1;
			if(specialalienx>670)
			{
				specialalienx=0;
				MoveSpecialAlien=false;
			}
		}
	}
    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e)
    {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e)
    {
        keys[e.getKeyCode()] = false;
    }

	public void actions()
	{
		playerMove();
		playerShoot();
		alienMove();
    	for(int i=0; i<aliens.size(); i++)
    	{
    		checkAlienPassed(i);
   			alienDirection(i);
			alienRemove(i);
    	}
		gameOver();
		Random rand = new Random();
		int RandomChance = rand.nextInt(5000);
		specialAlien(RandomChance);
		checkPlayerHit();
		newRound(roundEnd(aliens.size()));
		alienShooters();
		try
		{
			alienShoot();
		}
		catch(Exception e)
		{

		}
	}

    public void paintComponent(Graphics g)
    {
    	//lives sprite positions
		livesvisualx = 600;
		livesvisualy = 640;

    	g.drawImage(back,0,0,null); //draws background
		g.setColor(Color.white);
		
		//+23 to get the bullet to shoot in the middle
		g.fillRect(bulletx+23,bullety,4,10); //draws bullet
		
		//+12 to get the alien bullet to shoot in the middle
		g.fillRect(alienbulletx+12,alienbullety+23,4,10); //draws alien bullet
		
		if(MoveSpecialAlien == true)
		{
			g.drawImage(ufosprite,specialalienx,specialalieny,null); //draws ufo sprite if it is moving
		}
		
		g.drawImage(playersprite,playerx,playery,null);	//draws player sprite

		//draws current score
		g.setFont(new Font("Dialogue", Font.PLAIN, 20));
		g.drawString(Integer.toString(score)+" POINTS", 10, 650);

		//draws the lives in the corner
		for(int i=0;i<lives;i++)
		{
			g.drawImage(livessprite,livesvisualx,livesvisualy,null);
			livesvisualx-=45; //every life is 45 apart
		}

		for(int i=0; i<shields.size();i++) //shields
		{
			//draws shields based on the hp
			if(shields.get(i).getshieldhp() == 1) //if 1 it draws it as darkgreen
			{
				Color darkgreen = new Color(0, 128, 0);
				g.setColor(darkgreen);
				g.fillRect((shields.get(i)).getshieldx(),(shields.get(i)).getshieldy(),20,20);
			}
			else //if 2 it draws it as green
			{
				g.setColor(Color.green);
				g.fillRect((shields.get(i)).getshieldx(),(shields.get(i)).getshieldy(),20,20);
			}
		}

		for(int i=0; i<aliens.size(); i++) //draws aliens
		{
			if(aliens.get(i).getexploded() == true) //if the alien died it draws the exploded sprite
			{
				g.drawImage(explodesprite,(aliens.get(i)).getalienx(),(aliens.get(i)).getalieny(),null);
			}

			else
			{
				//draws frames based on the current frame of the alien
				if(aliens.get(i).getalienframe() == 1)
				{
					//draws different alien sprites based on the points
					if(aliens.get(i).getalienpoints() == 10)
					{
						g.drawImage(aliensprite1,(aliens.get(i)).getalienx(),(aliens.get(i)).getalieny(),null);
					}
					if(aliens.get(i).getalienpoints() == 20)
					{
						g.drawImage(aliensprite3,(aliens.get(i)).getalienx(),(aliens.get(i)).getalieny(),null);
					}
					if(aliens.get(i).getalienpoints() == 30)
					{
						g.drawImage(aliensprite5,(aliens.get(i)).getalienx(),(aliens.get(i)).getalieny(),null);
					}
				}
				if(aliens.get(i).getalienframe() == 2)
				{
					if(aliens.get(i).getalienpoints() == 10)
					{
						g.drawImage(aliensprite2,(aliens.get(i)).getalienx(),(aliens.get(i)).getalieny(),null);
					}
					if(aliens.get(i).getalienpoints() == 20)
					{
						g.drawImage(aliensprite4,(aliens.get(i)).getalienx(),(aliens.get(i)).getalieny(),null);
					}
					if(aliens.get(i).getalienpoints() == 30)
					{
						g.drawImage(aliensprite6,(aliens.get(i)).getalienx(),(aliens.get(i)).getalieny(),null);
					}
				}
			}
		}
    }
}

class Shield
{
	private int shieldx,shieldy;
	private int shieldhp;

	public Shield(int x, int y, int hp)
	{
		shieldx = x;
		shieldy = y;
		shieldhp = hp;
	}
	public int getshieldx()
	{
		return shieldx;
	}

	public int getshieldy()
	{
		return shieldy;
	}

	public int getshieldhp()
	{
		return shieldhp;
	}

	public boolean checkShieldHit(int bx, int by)
	{
		int x = shieldx-1;
		int y = shieldy;
		int x2 = shieldx-25;
		int y2 = shieldy-20;
		if(bx+2>x2 && bx+2<x && by>y2 && by<y)
		{
			shieldhp -= 1;
			return true;
		}
		return false;
	}
	public boolean checkShieldHitAlien(int abx, int aby)
	{
		int x = shieldx+15;
		int y = shieldy;
		int x2 = shieldx-13;
		int y2 = shieldy-20;
		if(abx>x2 && abx<x && aby>y2 && aby<y)
		{
			shieldhp -= 1;
			return true;
		}
		return false;
	}
}
class Alien
{
	private int alienx,alieny;
	private int stoptime = 0;
	private int stopdowntime = 0;
	private int points;
	private int speed = 50;
	private int posx,posy;
	private int frame = 1;
	private boolean exploded;

	public Alien(int x, int y, int pnts, int pox, int poy, boolean ex)
	{
		alienx = x;
		alieny = y;
		points = pnts;
		posx = pox;
		posy = poy;
		exploded = ex;
	}

	public void increaseSpeed(int speedincrease)
	{
		if(speedincrease <= 45)
		{
			speed -= speedincrease;
		}
		else
		{
			speed -= 45;
		}
	}

	public void alienDown()
	{
		if(stopdowntime == speed)
		{
			alieny += 25;
			stopdowntime = 0;
		}
		else
		{
			stopdowntime += 1;
		}
	}

	public void alienRight()
	{
		if(stoptime == speed)
		{
			if(frame == 1)
			{
				frame = 2;
			}
			else if(frame == 2)
			{
				frame = 1;
			}
			alienx += 10;
			stoptime = 0;
		}
		else
		{
			stoptime += 1;
		}
	}

	public void alienLeft()
	{
		if(stoptime == speed)
		{
			if(frame == 1)
			{
				frame = 2;
			}
			else if(frame == 2)
			{
				frame = 1;
			}
			alienx -= 10;
			stoptime = 0;
		}
		else
		{
			stoptime += 1;
		}
	}

	public int getposx()
	{
		return posx;
	}

	public int getposy()
	{
		return posy;
	}

	public int getalienx()
	{
		return alienx;
	}

	public int getalieny()
	{
		return alieny;
	}

	public int getalienframe()
	{
		return frame;
	}

	public int getalienpoints()
	{
		return points;
	}

	public boolean getexploded()
	{
		return exploded;
	}

	public String toString()
	{
		return (alienx+" "+alieny);
	}

	public boolean CheckAlienHit(int bx, int by)
	{
		int x = alienx+1;
		int y = alieny;
		int x2 = x-26;
		int y2 = y-25;
		if(bx+2>x2 && bx+2<x && by>y2 && by<y)
		{
			exploded = true;
			return true;
		}
		return false;
	}
}
package ourGame;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javafx.scene.input.KeyCode;

import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePlay {
	
	private String powerUps[] = new String[6];
	private boolean used[];
	
	public GamePlay() throws IOException{
		int numPlayers = 3;
		
		used = new boolean[6];
		for(int i = 0;i<6;i++) used[i] = false;
		int cont = 0;
		while(cont < 6) {
			Random rand = new Random();
			int k = rand.nextInt(6);
			if(!used[k]) {
				powerUps[k] = "pw"+(cont+1)+".png";
				used[k] = true;
				cont++;
			}
		}
		
		
		JFrame frame = new JFrame();
		frame.setSize(1125,720);
		int frameWidth = (int)(frame.getSize().getWidth()/numPlayers);
		frame.setTitle("My game ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		Image i = ImageIO.read(getClass().getResource("player1.png"));
		Player p1 = new Player(1,0+(frameWidth/2), 0, KeyCode.A, KeyCode.D, i);
		i = ImageIO.read(getClass().getResource("player2.png"));
		Player p2 = new Player(2,0+(frameWidth/2), 0, KeyCode.B, KeyCode.M, i);
		i = ImageIO.read(getClass().getResource("player3.png"));
		Player p3 = new Player(3,0+(frameWidth/2), 0, KeyCode.LEFT, KeyCode.RIGHT, i);
		
		GeradorPwUp gerador = new GeradorPwUp();
		
		Image memImg = ImageIO.read(getClass().getResource("mem.png"));
		Memoria mem = new Memoria(0,740,memImg);
		
		JPanel bg1 = new BackGround(0, 0, "cplusplus.png" ,frameWidth, 3623, p1, 1, gerador, powerUps, mem);
		bg1.setBounds(0, 0, 370, 720);
		frame.getContentPane().add(bg1);
		
		JPanel bg2 = new BackGround(0, 0, "java.png" ,frameWidth, 3623, p2, 2, gerador, powerUps,mem);
		bg2.setBounds(370, 0, 370, 720);
		frame.getContentPane().add(bg2);
		
		JPanel bg3 = new BackGround(0, 0, "python.png" ,frameWidth, 3623, p3,  3, gerador, powerUps,mem);
		bg3.setBounds(740, 0, 370, 720);
		frame.getContentPane().add(bg3);
		
		
		
		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent arg0) {
				((BackGround)bg1).keyPressed(arg0);
				((BackGround)bg2).keyPressed(arg0);
				((BackGround)bg3).keyPressed(arg0);
			}
			public void keyReleased(KeyEvent e) {
				((BackGround)bg1).keyReleased(e);
				((BackGround)bg2).keyReleased(e);
				((BackGround)bg3).keyReleased(e);
			}
		});
		
		Thread t1 = new Thread((Runnable) bg1);
		Thread t2 = new Thread((Runnable) bg2);
		Thread t3 = new Thread((Runnable) bg3);
		
		t1.start();
		t2.start();
		t3.start();
		
		GerenciadorMemoria gm = new GerenciadorMemoria((BackGround)bg1, (BackGround)bg2, (BackGround)bg3);
		Thread gmT = new Thread(gm);
		
		gerador.getCont1().isPaused = false;
		gerador.getCont2().isPaused = false;
		gerador.getCont3().isPaused = false;
		gerador.start();
		gm.run();
		
	}
	
	public static void main(String[] args) throws IOException{
		new GamePlay();
	}
}
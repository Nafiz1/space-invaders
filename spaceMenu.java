import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class spaceMenu extends JFrame{
	private JLayeredPane layeredPane=new JLayeredPane();

    public spaceMenu() {
		super("Space Invaders");
		setSize(650,700);

		ImageIcon backPic = new ImageIcon("menu.jpg");
		JLabel back = new JLabel(backPic);
		back.setBounds(0, 0,backPic.getIconWidth(),backPic.getIconHeight());
		layeredPane.add(back,1);

		ImageIcon startPic = new ImageIcon("start.gif");
		JButton startBtn = new JButton(startPic);
		startBtn.addActionListener(new ClickStart());
		startBtn.setBounds(225,550,startPic.getIconWidth(),startPic.getIconHeight());
		layeredPane.add(startBtn,2);

		setContentPane(layeredPane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
    }

    public static void main(String[] arguments) {
		spaceMenu frame = new spaceMenu();
    }

    class ClickStart implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent evt){
    		spaceInvaders game = new spaceInvaders();
    		GameMusic frame = new GameMusic();	
    		setVisible(false);
    	}
    }
}

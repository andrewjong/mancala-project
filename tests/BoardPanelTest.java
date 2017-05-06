import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class BoardPanelTest {
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setLayout(new FlowLayout());
		
		
		
		BoardPanel boardPanel = new BoardPanel();
		
		// left side mancala
		JPanel mancalaLeft = new JPanel();
		mancalaLeft.setPreferredSize(new Dimension(200,400));
		mancalaLeft.setBackground(java.awt.Color.GREEN);
		boardPanel.addLeft(mancalaLeft);
		
//		PitPanel panel = new PitPanel();
//		panel.setSize(100,100);
//		boardPanel.left.add(panel);
		
		// right side mancala
		JPanel mancalaRight = new JPanel();
		mancalaRight.setPreferredSize(new Dimension(200,400));
		mancalaRight.setBackground(java.awt.Color.GREEN);
		boardPanel.addRight(mancalaRight);
		
		
		// board center
		JPanel boardCenter = new JPanel();
		boardCenter.setPreferredSize(new Dimension(600,400));
		boardCenter.setBackground(java.awt.Color.PINK);
		boardPanel.addCenter(boardCenter);
		
		
		frame.add(boardPanel);
		frame.pack();
		frame.setVisible(true);
	}
}

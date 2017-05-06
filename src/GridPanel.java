import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JPanel;

/**

 * Creates a panel with a Gridlayout storing PitPanels
 * @author Prem Panchal
 *
 */

public class GridPanel extends JPanel
{
	private PitPanel[] p1Pits;
	private PitPanel[] p2Pits;
	

	GridPanel()
	{
		StoneIcon imageIcon = new StoneIcon.ImageStoneIcon(30,"images/white_stone.png");

		p1Pits = new PitPanel[6];
		p2Pits = new PitPanel[6];
		for(int i = 0; i<6;i++)
		{
			PitPanel pit = new PinkPitPanel(imageIcon, 4);
			pit.setSize(100, 100);
			p1Pits[i] = pit;
			pit.addMouseListener(new Controller.PitPanelListener(Side.P1, 5-i, pit));
		}
		for(int i=0; i<6;i++)
		{
			PitPanel pit = new PinkPitPanel(imageIcon, 4);
			pit.setSize(100, 100);
			p2Pits[i] = pit;
			pit.addMouseListener(new Controller.PitPanelListener(Side.P2, i, pit));
		}		
	}	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		//set grid layout and add to panel
		this.setLayout(new GridLayout(2,6));
		for(int i=0; i < p1Pits.length; i++)
		{
			this.add(p1Pits[i]);
		}
		for(int i=0; i < p2Pits.length; i++)
		{
			this.add(p2Pits[i]);
		}
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(600,200);
	}
	
	public PitPanel[] getP1Pits()
	{
		return p1Pits;
	}
	public PitPanel[] getP2Pits()
	{
		return p2Pits;
	}

	public void setP1Pits(PinkPitPanel[] p1Pits) {
		this.p1Pits = p1Pits;
	}

	public void setP2Pits(PinkPitPanel[] p2Pits) {
		this.p2Pits = p2Pits;
	}
	
}

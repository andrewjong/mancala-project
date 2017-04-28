import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JPanel;


public class GridPanel extends JPanel
{
	private PitPanel[] p1Pits;
	private PitPanel[] p2Pits;
	
	
	GridPanel()
	{
		p1Pits = new PitPanel[6];
		p2Pits = new PitPanel[6];
		for(int i = 0; i<6;i++)
		{
			PitPanel pit = new PitPanel();
			pit.setSize(100, 100);
			p1Pits[i] = pit;
			System.out.println("pit");
			pit.addMouseListener(new Controller.PitPanelListener(0, 5-i, pit));
		}
		for(int i=0; i<6;i++)
		{
			PitPanel pit = new PitPanel();
			pit.setSize(100, 100);
			p2Pits[i] = pit;
			pit.addMouseListener(new Controller.PitPanelListener(1, i, pit));
		}		
	}	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
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
	
	
	public PitPanel[] getP1Pits()
	{
		return p1Pits;
	}
	public PitPanel[] getP2Pits()
	{
		return p2Pits;
	}
}

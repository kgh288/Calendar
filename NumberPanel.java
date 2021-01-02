import javax.swing.JPanel;

public class NumberPanel extends JPanel {

	
	public boolean selected;
	
	public NumberPanel()
	{
		selected = false;
	}
	
	public boolean getSelected()
	{
		return selected;
	}
	
	public void setSelected(boolean boo)
	{
		selected = boo;
	}
}

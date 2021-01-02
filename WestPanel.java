import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class WestPanel extends JPanel implements ChangeListener{

	private CalendarModel model;
	private CalendarPanel calendarPanel;
	private JTextArea center;
	public WestPanel(CalendarModel model)
	{
		this.model = model;
		model.getChangeListeners().add(this);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		
		//initialize the north button
		JButton createButton = new JButton("CREATE");
		createButton.setBackground(new Color(255,200,200));
		
		ActionListener createListener = new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						CreateDialog a = new CreateDialog(model);

					}
			
				};
		createButton.addActionListener(createListener);
		
		//initialize the center TextArea
		center = new JTextArea();
		center.setText("   "+model.getMonth() + " " + model.getYear());
		center.setEditable(false);
		
		//initialize the southPanel of westPanel
		calendarPanel = new CalendarPanel(model);
		
	
		
		this.add(createButton, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(calendarPanel, BorderLayout.SOUTH);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		center.setText("   "+model.getMonth() + " " + model.getYear());
	}
	
	public CalendarPanel getCalendarPanel()
	{
		return calendarPanel;
	}
}

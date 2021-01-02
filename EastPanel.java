import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EastPanel extends JPanel implements ChangeListener{

	
	private CalendarModel model;
	private DateFormat fileDateFormat;
	private JTextArea dateText, textArea;
	public EastPanel(CalendarModel model)
	{
		this.model = model;
		fileDateFormat =  new SimpleDateFormat("MM/dd/yy");
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		
		model.getChangeListeners().add(this);
		this.setLayout(new BorderLayout());
		
		//initialize a JPanel for buttons on the top
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		
		//buttons for buttonPanel
		JButton left = new JButton("<");
		left.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						model.getWestPanel().getCalendarPanel().moveLeft();
					}
				});
		
		JButton right = new JButton(">");
		right.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				model.getWestPanel().getCalendarPanel().moveRight();
			}
		});
		
		JButton quit = new JButton("Quit");
		quit.setBackground(Color.WHITE);
		quit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try {
					model.saveEventFile();
					System.exit(0);
				} catch (IOException e1) {
					System.out.println("Save failed");
				}
			}
		});
		
		buttonPanel.add(left);
		buttonPanel.add(right);
		buttonPanel.add(quit);
		
		
		//Date Text Area
		dateText = new JTextArea(1, 30);
		dateText.setEditable(false);
		dateText.setBorder(BorderFactory.createRaisedBevelBorder());

		dateText.setText(fileDateFormat.format(model.getCalendar().getTime()));
		
		
		// Event Text Area
		textArea = new JTextArea(13, 30);
		textArea.setEditable(false);
		textArea.setBackground(new Color(230,230,240));
		
		//attaching methods for EastPanel
		this.add(buttonPanel, BorderLayout.NORTH);
		this.add(dateText, BorderLayout.CENTER);
		this.add(textArea, BorderLayout.SOUTH);
	}
	
	@Override
	public void stateChanged(ChangeEvent arg0) 
	{
		dateText.setText(fileDateFormat.format(model.getCalendar().getTime()));
		String result = "";
		for(Event e : model.getEvents())
		{
			if(e.getStartTime().get(e.getStartTime().YEAR)==model.getCalendar().get(model.getCalendar().YEAR) 
			&& e.getStartTime().get(e.getStartTime().MONTH)==model.getCalendar().get(model.getCalendar().MONTH) 
			&& e.getStartTime().get(e.getStartTime().DATE)==model.getCalendar().get(model.getCalendar().DATE))
			{
				result+=e.toString();
			}
		}
		textArea.setText(result);
	}
}

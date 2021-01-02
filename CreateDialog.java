import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class CreateDialog extends JDialog{
	
	private CalendarModel model;
	
	private DateFormat dateFormat;
	private DateFormat timeFormat;
	private JTextField startTime;
	private JTextField endTime;
	
	public CreateDialog(CalendarModel model)
	{
		this.model = model;
		dateFormat =  new SimpleDateFormat("MM/dd/yy");
		timeFormat = new SimpleDateFormat("HH mm");
		
		this.setSize(400,120);
		this.setLayout(new BorderLayout());
		
		
		JTextArea titleArea = new JTextArea(2,5);
		titleArea.setBorder(new LineBorder(Color.BLACK));
		titleArea.setText("Untitled Event");
		
		
		JTextArea description = new JTextArea();
		description.setEditable(false);
		description.setBackground(Color.WHITE);
		description.setBorder(BorderFactory.createLoweredBevelBorder());
		description.setText("Date(mm/dd/yy)    From(HH:mm)        To(HH:mm)");
		
		JTextField date = new JTextField(15);
		String dateString = dateFormat.format(model.getCalendar().getTime());
		date.setText(dateString);
		date.setEditable(false);
		
		startTime = new JTextField(3);
		startTime.setText("10:00");
		
		endTime = new JTextField(3);
		endTime.setText("18:00");
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1,4,20,5));
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						GregorianCalendar startCal = new GregorianCalendar(model.getCalendar().get(model.getCalendar().YEAR),
								model.getCalendar().get(model.getCalendar().MONTH),
								model.getCalendar().get(model.getCalendar().DATE),
								Integer.parseInt(startTime.getText().substring(0, 2)),
								Integer.parseInt(startTime.getText().substring(3, 4)));
						GregorianCalendar endCal = new GregorianCalendar(model.getCalendar().get(model.getCalendar().YEAR),
								model.getCalendar().get(model.getCalendar().MONTH),
								model.getCalendar().get(model.getCalendar().DATE),
								Integer.parseInt(endTime.getText().substring(0, 2)),
								Integer.parseInt(endTime.getText().substring(3, 4)));
						Event event = new Event(titleArea.getText(), startCal, endCal);
						if(model.eventAdded(event))
							JOptionPane.showMessageDialog(null, "Event saved!");
					}
				});
		
		southPanel.add(date);
		southPanel.add(startTime);
		southPanel.add(endTime);
		southPanel.add(saveButton);

		this.add(titleArea, BorderLayout.NORTH);
		this.add(description, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

}

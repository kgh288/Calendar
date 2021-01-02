import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import javax.swing.*;

public class SimpleCalendar {
	
	public static void main(String[] args) throws ParseException, IOException
	{
		//initialize model
		CalendarModel model = new CalendarModel(new File("D:\\Eclipse_Workspace\\cs151_hw4\\event.txt"));
		
		//initialize the Frame
		JFrame frame = new JFrame();
		frame.setTitle("Simple Calendar");
		frame.setSize(1000,400);
		frame.setLayout(new BorderLayout());
		
		frame.add(model.getWestPanel(), BorderLayout.WEST);
		frame.add(model.getEastPanel(), BorderLayout.EAST);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

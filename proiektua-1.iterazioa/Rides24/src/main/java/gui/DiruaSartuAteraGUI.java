
package gui;

import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Driver;
import domain.Ride;
import domain.Rider;
import domain.User;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class DiruaSartuAteraGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private EntityManager db;
	private Driver driver;
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;


	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JScrollPane scrollPaneEvents = new JScrollPane();

	private List<Date> datesWithEventsCurrentMonth;
	private JTextField Zenbatekoa2;
	
	private JLabel ErrorLabel;


	public DiruaSartuAteraGUI(User user) {

		getContentPane().setLayout(null);

		JLabel Zenbatekoa = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartuAteraGUI.Zenbatekoa")); //$NON-NLS-1$ //$NON-NLS-2$
		Zenbatekoa.setBounds(64, 70, 155, 17);
		getContentPane().add(Zenbatekoa);

		Zenbatekoa2 = new JTextField();
		Zenbatekoa2.setBounds(229, 68, 86, 20);
		getContentPane().add(Zenbatekoa2);
		Zenbatekoa2.setColumns(10);
		JRadioButton Sartu = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartuAteraGUI.Sartu")); //$NON-NLS-1$ //$NON-NLS-2$
		Sartu.setBounds(98, 128, 126, 23);
		getContentPane().add(Sartu);

		JRadioButton Atera = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartuAteraGUI.Atera")); //$NON-NLS-1$ //$NON-NLS-2$
		Atera.setBounds(226, 128, 155, 23);
		getContentPane().add(Atera);
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		
		JButton Konfirmatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartuAteraGUI.Konfirmatu")); //$NON-NLS-1$ //$NON-NLS-2$
		Konfirmatu.setBounds(169, 179, 89, 23);
		getContentPane().add(Konfirmatu);
		Konfirmatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				String zenbatekoa=Zenbatekoa2.getText();
				ErrorLabel.setText(field_Errors()); 
				if(ErrorLabel.getText().toString().equals("")) {
				if (Atera.isSelected()) {
					if (user instanceof Rider) {
						facade.diruaAteraRider(Float.parseFloat(zenbatekoa),user.getEmail());
					}else {
						facade.diruaAteraDriver(Float.parseFloat(zenbatekoa),user.getEmail());
					}}
				else if(Sartu.isSelected()){
					if (user instanceof Rider) {
						facade.diruaSartuRider(Float.parseFloat(zenbatekoa),user.getEmail());
					}
					else {
						facade.diruaSartuDriver(Float.parseFloat(zenbatekoa),user.getEmail());
					}
				}
			}
				else
					Zenbatekoa2.setText("");
			}
		});
		



		buttonGroup.add(Atera);
		buttonGroup.add(Sartu);

		
		ErrorLabel = new JLabel();
		ErrorLabel.setBounds(90, 222, 262, 28);
		getContentPane().add(ErrorLabel);
		ErrorLabel.setForeground(Color.red);

	}
	public String field_Errors() {
		String msg ="";
		float i = 0;
		try {	
		i = Float.parseFloat(Zenbatekoa2.getText());
			if (i <= 0) {
				msg = ResourceBundle.getBundle("Etiquetas").getString("DiruaSartuAteraGUI.LowNumberError");	
			}
		}catch (NumberFormatException e) {
			msg = ResourceBundle.getBundle("Etiquetas").getString("DiruaSartuAteraGUI.NumberError");
		}
		return msg;
	}
}
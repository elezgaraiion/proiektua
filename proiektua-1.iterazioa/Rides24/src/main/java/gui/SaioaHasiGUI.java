package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Driver;
import domain.Ride;
import domain.Rider;
import domain.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;


public class SaioaHasiGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField emailText;
	private JTextField psswdText;
	private JLabel errorLabel;

	
	public SaioaHasiGUI()
	{
		this.setSize(new Dimension(700, 500));
		
		getContentPane().setLayout(null);
		
		emailText = new JTextField();
		emailText.setBounds(335, 100, 96, 19);
		
		getContentPane().add(emailText);
		emailText.setColumns(10);
		
		psswdText = new JPasswordField();
		psswdText.setBounds(335, 167, 96, 19);
		
		getContentPane().add(psswdText);
		psswdText.setColumns(10);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(145, 103, 96, 13);
		lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("SaioaHasiGUI.Name"));
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel();
		lblNewLabel_1.setBounds(145, 171, 96, 13);
		lblNewLabel_1.setText(ResourceBundle.getBundle("Etiquetas").getString("SaioaHasiGUI.PW"));
		getContentPane().add(lblNewLabel_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(249, 240, 139, 22);
		getContentPane().add(comboBox);
		 DefaultComboBoxModel erabiltzaileMota = new DefaultComboBoxModel(new String[]{ResourceBundle.getBundle("Etiquetas").getString("ErregistratuGUI.Driver"),
		        ResourceBundle.getBundle("Etiquetas").getString("ErregistratuGUI.Rider")});
		comboBox.setModel(erabiltzaileMota);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setText(ResourceBundle.getBundle("Etiquetas").getString("SaioaHasiGUI.Button"));
		btnNewButton.setBounds(262, 337, 139, 21);
		getContentPane().add(btnNewButton);
		
		errorLabel = new JLabel(); 
		errorLabel.setBounds(124, 383, 404, 19);
		getContentPane().add(errorLabel);
		errorLabel.setForeground(Color.red);
		
		btnNewButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				errorLabel.setText(field_Errors());
				if(errorLabel.getText().toString().equals("")) {
				if(facade.emailaEtaPasswordaBadaude(emailText.getText().toString(),psswdText.getText().toString(),comboBox.getSelectedItem().toString())) {
					
				if(comboBox.getSelectedItem().toString().equals("Driver")) {
					Driver u;
					 u= facade.driverBadago(emailText.getText().toString());
					 System.out.println(u.toString());
				JFrame a = new DriverInterfaceGUI(u);
				a.setVisible(true);
				}
				else if(comboBox.getSelectedItem().toString().equals("Rider")) {
					Rider l;
					l= facade.riderBadago(emailText.getText().toString());
				JFrame a = new RiderInterfaceGUI(l);
				a.setVisible(true);
				}
				
				}else {
					errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("SaioaHasiGUI.ezdago"));
					psswdText.setText("");
				}
				}
			}
			});
	}
public String field_Errors() {
			if ((emailText.getText().length()==0) || (psswdText.getText().length()==0))
				return ResourceBundle.getBundle("Etiquetas").getString("SaioaHasiGUI.ErrorQuery");
			else 
				return "";

}
	}

	

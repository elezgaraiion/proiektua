package gui;

import javax.swing.*;



import domain.Driver;
import domain.Rider;
import domain.User;
import businessLogic.BLFacade;



import java.awt.Color;

import java.awt.Font;

import java.awt.GridLayout;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import java.util.Locale;

import java.util.ResourceBundle;



import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;





public class ErregistratuGUI extends JFrame {


   private Driver driver;




private JPanel jContentPane = null;

private JButton jButtonCreateQuery = null;

private JButton jButtonQueryQueries = null;

private JTextField fieldOrigin=new JTextField();

private JTextField fieldDestination=new JTextField();

private JComboBox comboBox;
 DefaultComboBoxModel erabiltzaileMota = new DefaultComboBoxModel(new String[]{ResourceBundle.getBundle("Etiquetas").getString("ErregistratuGUI.Driver"),
		ResourceBundle.getBundle("Etiquetas").getString("ErregistratuGUI.Rider")});

 

protected JLabel jLabelSelectOptionIzena;

private final ButtonGroup buttonGroup = new ButtonGroup();


private JTextField nameText;
private JTextField SurnameText;
private JPasswordField psswdText;
private JTextField emailaText;
private JTextField phoneText;
private JLabel ErrorLabel;

/**

* This is the default constructor

*/

public ErregistratuGUI() {


this.setSize(495, 290);
jLabelSelectOptionIzena = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErregistratuGUI.IzenaJarri"));
jLabelSelectOptionIzena.setBounds(10, 10, 182, 27);
jLabelSelectOptionIzena.setFont(new Font("Tahoma", Font.BOLD, 13));
jLabelSelectOptionIzena.setForeground(Color.BLACK);
jLabelSelectOptionIzena.setHorizontalAlignment(SwingConstants.CENTER);


jContentPane = new JPanel();
jContentPane.setLayout(null);
jContentPane.add(jLabelSelectOptionIzena);





setContentPane(jContentPane);





 

JLabel lblAbizena = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErregistratuGUI.lblAbizena")); //$NON-NLS-1$ //$NON-NLS-2$
lblAbizena.setHorizontalAlignment(SwingConstants.CENTER);
lblAbizena.setForeground(Color.BLACK);
lblAbizena.setFont(new Font("Tahoma", Font.BOLD, 13));
lblAbizena.setBounds(10, 45, 182, 27);
jContentPane.add(lblAbizena);


JLabel lblPasahitza = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErregistratuGUI.lblPasahitza")); //$NON-NLS-1$ //$NON-NLS-2$
lblPasahitza.setHorizontalAlignment(SwingConstants.CENTER);
lblPasahitza.setForeground(Color.BLACK);
lblPasahitza.setFont(new Font("Tahoma", Font.BOLD, 13));
lblPasahitza.setBounds(10, 82, 182, 27);
jContentPane.add(lblPasahitza);


JLabel lblErabiltzaileMota = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErregistratuGUI.lblErabiltzaileMota")); //$NON-NLS-1$ //$NON-NLS-2$
lblErabiltzaileMota.setHorizontalAlignment(SwingConstants.CENTER);
lblErabiltzaileMota.setForeground(Color.BLACK);
lblErabiltzaileMota.setFont(new Font("Tahoma", Font.BOLD, 13));
lblErabiltzaileMota.setBounds(10, 119, 182, 27);
jContentPane.add(lblErabiltzaileMota);


JLabel lblEmaila = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErregistratuGUI.lblEmaila")); //$NON-NLS-1$ //$NON-NLS-2$
lblEmaila.setHorizontalAlignment(SwingConstants.CENTER);
lblEmaila.setForeground(Color.BLACK);
lblEmaila.setFont(new Font("Tahoma", Font.BOLD, 13));
lblEmaila.setBounds(10, 151, 182, 27);
jContentPane.add(lblEmaila);


JLabel lblTelefonoZenbakia = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErregistratuGUI.lblTelefonoZenbakia")); //$NON-NLS-1$ //$NON-NLS-2$
lblTelefonoZenbakia.setHorizontalAlignment(SwingConstants.CENTER);
lblTelefonoZenbakia.setForeground(Color.BLACK);
lblTelefonoZenbakia.setFont(new Font("Tahoma", Font.BOLD, 13));
lblTelefonoZenbakia.setBounds(10, 186, 182, 27);
jContentPane.add(lblTelefonoZenbakia);



JComboBox comboBox = new JComboBox();
comboBox.setBounds(275, 123, 119, 21);
jContentPane.add(comboBox);
comboBox.setModel(erabiltzaileMota);

nameText = new JTextField();
nameText.setColumns(10);
nameText.setBounds(264, 12, 130, 26);
jContentPane.add(nameText);


SurnameText = new JTextField();
SurnameText.setColumns(10);
SurnameText.setBounds(264, 47, 130, 26);
jContentPane.add(SurnameText);


psswdText = new JPasswordField();
psswdText.setColumns(10);
psswdText.setBounds(264, 84, 130, 26);
jContentPane.add(psswdText);


emailaText = new JTextField();
emailaText.setColumns(10);
emailaText.setBounds(264, 153, 130, 26);
jContentPane.add(emailaText);


phoneText = new JTextField();
phoneText.setColumns(10);
phoneText.setBounds(264, 188, 130, 26);
jContentPane.add(phoneText);


ErrorLabel = new JLabel();
ErrorLabel.setBounds(20, 228, 234, 12);
jContentPane.add(ErrorLabel);
ErrorLabel.setForeground(Color.red);

JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErregistratuGUI.btnNewButton")); //$NON-NLS-1$ //$NON-NLS-2$
btnNewButton.setBounds(226, 222, 146, 21);
jContentPane.add(btnNewButton);
btnNewButton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
	BLFacade facade = MainGUI.getBusinessLogic();
	User u;
	ErrorLabel.setText(field_Errors()); 
	if (field_Errors()=="") {
		if(comboBox.getSelectedItem().toString().equals("Rider")) {
		 u=new Rider(emailaText.getText().toString(),nameText.getText().toString() + SurnameText.getText().toString(),psswdText.getText().toString(),0,0,phoneText.getText().toString());
		}
		else {
		 u=new Driver(emailaText.getText().toString(),nameText.getText().toString() + SurnameText.getText().toString(),psswdText.getText().toString(),0,phoneText.getText().toString());
		}
			if(facade.erregistratu(u)) {
			if(comboBox.getSelectedItem().toString().equals("Driver")) {
				JFrame a = new DriverInterfaceGUI((Driver)u);
				a.setVisible(true);
			}
			else if(comboBox.getSelectedItem().toString().equals("Rider")) {
				JFrame a = new RiderInterfaceGUI((Rider)u);
				a.setVisible(true);
			}
			}

		}
	}

});
}
public String field_Errors() {
	String msg = "";
	 
		 if(nameText.getText().length()==0 || SurnameText.getText().length()==0 || psswdText.getText().length()==0 
				 || emailaText.getText().length()==0 || phoneText.getText().length()==0) {
			 msg = ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorQuery");
		 }else 
			 if(nameText.getText().length()>20 || SurnameText.getText().length()>20 || psswdText.getText().length()>20 
			 || emailaText.getText().length()>30 ) {
				 msg = ResourceBundle.getBundle("Etiquetas").getString("ErregistratuGUI.ErrorMaxLength");
		 }else
			 try {
				int telefonoa = Integer.parseInt(phoneText.getText());
				
			 }catch (NumberFormatException e) {
				 msg = ResourceBundle.getBundle("Etiquetas").getString("ErregistratuGUI.ErrorNumber");
			 }
			  	 
	 		 return msg;	 
	 }


}
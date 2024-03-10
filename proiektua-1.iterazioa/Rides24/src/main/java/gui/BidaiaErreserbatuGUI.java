
package gui;


import java.util.*;





import javax.swing.*;






import java.awt.*;

import java.awt.event.*;





import businessLogic.BLFacade;



import domain.Ride;
import domain.Rider;

public class BidaiaErreserbatuGUI extends JFrame {

	private static final long serialVersionUID = 1L;


	private JButton btnNewButton;
	private JLabel dateLabel;
	private JLabel departLabel;
	private JLabel arrivalLabel;
	private JLabel bidaikoDataLabel;
	private JLabel bidaikoDepartLabel;
	private JLabel bidaikoArrivalLabel;
	private JSpinner spinner;
	private JLabel seatsLabel;
	private JLabel totalPriceLabel;
	private JLabel driverLabel;
	private JLabel availableSeatsLabel;
	private JLabel bidaikoDriverLabel;
	private JLabel bidaikoAvailableSeatsLabel;
	private JLabel ErrorLabel;
	private JLabel lblNewLabel;
	private JTextField textField;



	public BidaiaErreserbatuGUI(Ride ride,Rider rider) {



		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.CreateRide"));







		dateLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BidaiaErreserbatuGUI.dateLabel"));
		dateLabel.setBounds(21, 58, 75, 24);
		getContentPane().add(dateLabel);


		departLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BidaiaErreserbatuGUI.departLabel"));
		departLabel.setBounds(21, 92, 75, 21);
		getContentPane().add(departLabel);


		arrivalLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BidaiaErreserbatuGUI.arrivalLabel"));
		arrivalLabel.setBounds(21, 124, 75, 24);
		getContentPane().add(arrivalLabel);


		bidaikoDataLabel = new JLabel();
		bidaikoDataLabel.setBounds(91, 59, 296, 24);
		getContentPane().add(bidaikoDataLabel);
		bidaikoDataLabel.setText(ride.getDate().toString());


		bidaikoDepartLabel = new JLabel();
		bidaikoDepartLabel.setBounds(126, 93, 156, 21);
		getContentPane().add(bidaikoDepartLabel);
		bidaikoDepartLabel.setText(ride.getFrom().toString());

		bidaikoArrivalLabel = new JLabel();
		bidaikoArrivalLabel.setBounds(123, 124, 159, 24);
		getContentPane().add(bidaikoArrivalLabel);
		bidaikoArrivalLabel.setText(ride.getTo().toString());

		spinner = new JSpinner();
		spinner.setBounds(243, 182, 30, 20);
		getContentPane().add(spinner);


		seatsLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BidaiaErreserbatuGUI.seatsLabel"));
		seatsLabel.setBounds(172, 183, 61, 16);
		getContentPane().add(seatsLabel);


		totalPriceLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BidaiaErreserbatuGUI.totalPriceLabel"));
		totalPriceLabel.setBounds(317, 183, 70, 16);
		getContentPane().add(totalPriceLabel);


		driverLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BidaiaErreserbatuGUI.driverLabel"));
		driverLabel.setBounds(292, 84, 80, 19);
		getContentPane().add(driverLabel);


		availableSeatsLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BidaiaErreserbatuGUI.availableSeatsLabel"));
		availableSeatsLabel.setBounds(292, 113, 95, 17);
		getContentPane().add(availableSeatsLabel);


		bidaikoDriverLabel = new JLabel();
		bidaikoDriverLabel.setBounds(401, 84, 122, 19);
		getContentPane().add(bidaikoDriverLabel);
		bidaikoDriverLabel.setText(ride.getDriver().getName());

		bidaikoAvailableSeatsLabel = new JLabel();
		bidaikoAvailableSeatsLabel.setBounds(401, 111, 156, 20);
		getContentPane().add(bidaikoAvailableSeatsLabel);
		bidaikoAvailableSeatsLabel.setText(String.valueOf(ride.getnPlaces()));
		
		JLabel prezioaText = new JLabel(); 
		prezioaText.setBounds(418, 185, 116, 14);
		getContentPane().add(prezioaText);
		prezioaText.setText(String.valueOf(ride.getPrice()));


		ErrorLabel = new JLabel(); 
        ErrorLabel.setBounds(76, 306, 439, 14);
        getContentPane().add(ErrorLabel);
        ErrorLabel.setForeground(Color.red);
        
        lblNewLabel = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
        lblNewLabel.setBounds(76, 216, 174, 14);
        getContentPane().add(lblNewLabel);
        lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("BidaiaErreserbatuGUI.sartukodea"));
        
        textField = new JTextField();
        textField.setBounds(327, 213, 188, 20);
        getContentPane().add(textField);
        textField.setColumns(10);

 
        btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BidaiaErreserbatuGUI.btnNewButton"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ErrorLabel.setText(field_Errors(ride));
				BLFacade facade=MainGUI.getBusinessLogic();
				facade.makeOffer(ride,rider,(int)spinner.getValue(),textField.getText());
			}
		});
		  btnNewButton.setBounds(201, 241, 139, 54);
    	  getContentPane().add(btnNewButton);


	}
	public String field_Errors(Ride ride) {
	    String msg = "";
	    
	    if ((int)spinner.getValue() < 1) {
	        msg = ResourceBundle.getBundle("Etiquetas").getString("BidaiaErreserbatu.ValueError");
	    } else {
	        if ((int) spinner.getValue() > ride.getnPlaces()) {
	            msg = ResourceBundle.getBundle("Etiquetas").getString("BidaiaErreserbatu.SeatsValueError");
	        }
	    }
	    return msg;
	}
}

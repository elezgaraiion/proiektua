package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Driver;
import domain.Rider;
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


public class RiderInterfaceGUI extends JFrame {
	
    private Driver driver;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JButton jButtonQueryQueries = null;
	private JButton jButtonBalance;

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel jLabelSelectOption;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton jButtonQueryRides;
	
	/**
	 * This is the default constructor
	 */
	public RiderInterfaceGUI(Rider rider) {
		super();

		//driver=d;
		
		// this.setSize(271, 295);
		this.setSize(495, 539);
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jLabelSelectOption.setBounds(0, 1, 481, 125);
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		
		jButtonBalance = new JButton();
		jButtonBalance.setBounds(0, 205, 481, 85);
		jButtonBalance.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Dirua"));
		jButtonBalance.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new DiruaSartuAteraGUI(rider);

				a.setVisible(true);
			}
		});
		
		jContentPane = new JPanel();
		jContentPane.setLayout(null);
		jContentPane.add(jLabelSelectOption);
		jContentPane.add(jButtonBalance);
		
		
		setContentPane(jContentPane);
		
		jButtonQueryRides = new JButton();
		jButtonQueryRides.setBounds(0, 130, 481, 77);
		jButtonQueryRides.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		jContentPane.add(jButtonQueryRides);
		jButtonQueryRides.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new FindRidesGUI(1,rider);
				a.setVisible(true);
			}
			});
		//setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle") + " - driver :"+driver.getName());
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
	}
} // @jve:decl-index=0:visual-constraint="0,0"



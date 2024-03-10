package gui;

import javax.swing.*;

import domain.Driver;
import domain.Eskaera;
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
import java.awt.Rectangle;
import java.awt.BorderLayout;


public class EskaerakGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel ErrorLabel;
	
      public EskaerakGUI(Driver driver) {
    	  getContentPane().setLayout(null);

    	  BLFacade facade = MainGUI.getBusinessLogic();
    	  JComboBox<Eskaera> comboBox = new JComboBox<Eskaera>();
    	  comboBox.setBounds(36, 92, 603, 21);
    	  getContentPane().add(comboBox);
    	  DefaultComboBoxModel<Eskaera> model = new DefaultComboBoxModel<Eskaera>();
    	  for(Eskaera esk: facade.eskaerakSartu(driver)) {
    	  model.addElement(esk);
    	  }
    	  comboBox.setModel(model);
    	 
    	  JLabel lblNewLabel =new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EskaerakGUI.Riderrak"));
    	  lblNewLabel.setBounds(36, 25, 159, 30);
    	  getContentPane().add(lblNewLabel);

    	  JRadioButton onartu = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("EskaerakGUI.Onartu"));
    	  buttonGroup.add(onartu);
    	  onartu.setBounds(150, 173, 103, 21);
    	  getContentPane().add(onartu);

    	  JRadioButton ezeztatu = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("EskaerakGUI.Ez_onartu")); //$NON-NLS-1$ //$NON-NLS-2$
    	  buttonGroup.add(ezeztatu);
    	  ezeztatu.setBounds(374, 173, 103, 21);
    	  getContentPane().add(ezeztatu);

    	  ErrorLabel = new JLabel();
  		ErrorLabel.setBounds(90, 222, 262, 28);
  		getContentPane().add(ErrorLabel);
  		ErrorLabel.setForeground(Color.red);
  		 if(comboBox.getItemCount()==0) 
	  			ErrorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("EskaerakGUI.hutsa"));
    	  JButton Konfirmatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EskaerakGUI.Konfirmatu")); //$NON-NLS-1$ //$NON-NLS-2$
    	  
    	  Konfirmatu.addActionListener(new ActionListener() {
    	  	public void actionPerformed(ActionEvent e) {
    	  		boolean aurkitua=false;
    	  		BLFacade facade = MainGUI.getBusinessLogic();
    	  		Eskaera eskaera=(Eskaera)comboBox.getSelectedItem();
    	  		
    	  		if (onartu.isSelected()) {
    	  				facade.bidaiaOnartu(eskaera); 
    	  				DefaultComboBoxModel<Eskaera> model = (DefaultComboBoxModel<Eskaera>) comboBox.getModel();
        	  			int selectedIndex = comboBox.getSelectedIndex();
        	  			model.removeElementAt(selectedIndex);
    	  		}else if(ezeztatu.isSelected()){
    	  			facade.bidaiaEzeztatu(eskaera);
    	  			DefaultComboBoxModel<Eskaera> model = (DefaultComboBoxModel<Eskaera>) comboBox.getModel();
    	  			int selectedIndex = comboBox.getSelectedIndex();
    	  			model.removeElementAt(selectedIndex);
    	  			
    	  		}else {
    	  			ErrorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("EskaerakGUI.sakatu"));
    	  		}
    	  		
    	  	}
    	  });
    	  Konfirmatu.setBounds(272, 232, 85, 21);
    	  getContentPane().add(Konfirmatu);
      }
     
}
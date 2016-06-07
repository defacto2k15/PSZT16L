package View;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JTextField;

import Controller.Controller;
import Model.LinguisticAttributes;
import Model.FuzzySets.FoodQualityLinguisticValues;
import sun.swing.JLightweightFrame;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;

public class MainWindow implements IView{

	private JFrame frame;
	private JTextField generationValue1;
	private JTextField generationValue2;
	private JLabel generationLabel1;
	private JSlider generationSlider2;
	private JLabel generationLabel2;
	private JSlider generationSlider1;
	private ViewStateInfo viewStateInfo;
	private JLabel lblWartoWejciowa;
	private JTextField charmValue;
	private JTextField ServiceQualityValue;
	private JTextField FoodQualityValue;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewStateInfo stateInfo = new ViewStateInfo();
					Controller controller = new Controller();
					MainWindow window = new MainWindow(stateInfo, controller);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow(ViewStateInfo viewStateInfo, Controller controller) {
		initialize(viewStateInfo, controller);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(ViewStateInfo viewStateInfo, Controller controller) {
		this.viewStateInfo = viewStateInfo;
		frame = new JFrame();
		frame.setBounds(100, 100, 724, 427);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JComboBox generatorComboBox = new JComboBox();
		generatorComboBox.setBounds(12, 12, 200, 24);
		frame.getContentPane().add(generatorComboBox);
		generatorComboBox.addItemListener((l)->{
			controller.generatorComboBoxChanged(generatorComboBox.getSelectedIndex());
		});

		
		generationValue1 = new JTextField();
		generationValue1.setBounds(169, 59, 43, 19);
		frame.getContentPane().add(generationValue1);
		generationValue1.setColumns(10);
		
		generationLabel1 = new JLabel("New label");
		generationLabel1.setBounds(12, 45, 200, 15);
		frame.getContentPane().add(generationLabel1);
		
		generationSlider2 = new JSlider();
		generationSlider2.setBounds(12, 107, 150, 16);
		frame.getContentPane().add(generationSlider2);
		
		generationValue2 = new JTextField();
		generationValue2.setColumns(10);
		generationValue2.setBounds(169, 104, 43, 19);
		frame.getContentPane().add(generationValue2);
		
		generationLabel2 = new JLabel("New label");
		generationLabel2.setBounds(12, 90, 206, 15);
		frame.getContentPane().add(generationLabel2);
		
		
		generationSlider1 = new JSlider();
		generationSlider1.setBounds(12, 62, 150, 16);
		frame.getContentPane().add(generationSlider1);
		generationSlider1.setMinimum(0);
		generationSlider1.setMaximum(100);
		generationSlider1.setValue(50);
		generationSlider1.addChangeListener((l)-> {
			JSlider source = (JSlider)l.getSource();
			if( !source.getValueIsAdjusting()){
				int val = (int)source.getValue();
				controller.generationSlider1Change(val);
			}
		});
		
		generationSlider2.addChangeListener((l)-> {
			JSlider source = (JSlider)l.getSource();
			if( !source.getValueIsAdjusting()){
				int val = (int)source.getValue();
				controller.generationSlider2Change(val);
			}
		});
		
		controller.setView(this); // TODO UGLY HACK
		controller.setStateInfo(viewStateInfo);
		
		List<DistributionSettingProperties> distributionPropeties = viewStateInfo.getDistributionSettingProperties();
		generatorComboBox.addItem(distributionPropeties.get(0).getTitle());
		generatorComboBox.addItem(distributionPropeties.get(1).getTitle());
		generationValue1.setEditable(false);	
		generationValue2.setEditable(false);	
		
		lblWartoWejciowa = new JLabel("Wartość wejściowa");
		lblWartoWejciowa.setFont(new Font("Dialog", Font.BOLD, 16));
		lblWartoWejciowa.setHorizontalAlignment(SwingConstants.CENTER);
		lblWartoWejciowa.setHorizontalTextPosition(SwingConstants.CENTER);
		lblWartoWejciowa.setBounds(12, 177, 189, 36);
		frame.getContentPane().add(lblWartoWejciowa);
		
		JButton btnNewButton = new JButton("Generuj!");
		btnNewButton.setBounds(12, 135, 200, 36);
		frame.getContentPane().add(btnNewButton);
		//------------------- TERA wartości crisp
		
		ServiceQualityValue = new JTextField();
		ServiceQualityValue.setColumns(10);
		ServiceQualityValue.setBounds(169, 272, 43, 19);
		ServiceQualityValue.setEditable(false);
		frame.getContentPane().add(ServiceQualityValue);
		
		FoodQualityValue = new JTextField();
		FoodQualityValue.setColumns(10);
		FoodQualityValue.setBounds(169, 317, 43, 19);
		FoodQualityValue.setEditable(false);
		frame.getContentPane().add(FoodQualityValue);
		
		charmValue = new JTextField();
		charmValue.setColumns(10);
		charmValue.setBounds(169, 227, 43, 19);
		charmValue.setEditable(false);
		frame.getContentPane().add(charmValue);
		
		
		
		JSlider charmSlider = new JSlider();
		charmSlider.setBounds(12, 230, 150, 16);
		frame.getContentPane().add(charmSlider);
		charmSlider.addChangeListener((l)->{
			charmValue.setText( new Integer(charmSlider.getValue()).toString()+"%");
			controller.attributeValueSliderChanged(LinguisticAttributes.Charm, charmSlider.getValue());
		});
		
		JLabel lblUrokKelnerki = new JLabel("Urok kelnerki");
		lblUrokKelnerki.setBounds(12, 213, 206, 15);
		frame.getContentPane().add(lblUrokKelnerki);
		

		
		JSlider serviceQualitySlider = new JSlider();
		serviceQualitySlider.setBounds(12, 275, 150, 16);
		frame.getContentPane().add(serviceQualitySlider);
		serviceQualitySlider.addChangeListener((l)->{
			ServiceQualityValue.setText( new Integer(serviceQualitySlider.getValue()).toString()+"%");
			controller.attributeValueSliderChanged(LinguisticAttributes.ServiceQuality, serviceQualitySlider.getValue());
		});
		
		JLabel ServiceQualityLabel = new JLabel("Jakość obsługi");
		ServiceQualityLabel.setBounds(12, 258, 206, 15);
		frame.getContentPane().add(ServiceQualityLabel);
		

		
		JSlider foodQualitySlider = new JSlider();
		foodQualitySlider.setBounds(12, 320, 150, 16);
		frame.getContentPane().add(foodQualitySlider);
		foodQualitySlider.addChangeListener((l)->{
			FoodQualityValue.setText( new Integer(foodQualitySlider.getValue()).toString() + "%");
			controller.attributeValueSliderChanged(LinguisticAttributes.FoodQuality, foodQualitySlider.getValue());
		});
			
		
		JLabel FoodQualityLabel = new JLabel("Jakość jedzenia");
		FoodQualityLabel.setBounds(12, 303, 206, 15);
		frame.getContentPane().add(FoodQualityLabel);
		
		controller.setShouldUpdateView(true);
		update();
	}
	
	@Override
	public void update(){
		DistributionSettingProperties activeDistribution  = viewStateInfo.getActiveDistribution();
		if( viewStateInfo.getActiveDistribution().isFirstSliderActive()){
			generationSlider1.setEnabled(true);
			generationSlider1.setValue( activeDistribution.getValue1());
			generationValue1.setText( new Integer(activeDistribution.getValue1()).toString());
			generationLabel1.setText(activeDistribution.getValue1Title());
		} else {
			generationSlider1.setEnabled(false);
			generationLabel1.setText("----");
		}
		if( viewStateInfo.getActiveDistribution().isSecondSliderActive()){
			generationSlider2.setEnabled(true);
			generationSlider2.setValue( activeDistribution.getValue2());
			generationValue2.setText( new Integer(activeDistribution.getValue2()).toString());
			generationLabel2.setText(activeDistribution.getValue2Title());
		} else {
			generationSlider2.setEnabled(false);
			generationLabel2.setText("----");
		}
		
		ServiceQualityValue.setText(
				new Integer( 
						viewStateInfo.getLinguisticAttributeCrispValue(LinguisticAttributes.ServiceQuality)).toString());
		FoodQualityValue.setText(
				new Integer( 
						viewStateInfo.getLinguisticAttributeCrispValue(LinguisticAttributes.FoodQuality)).toString());
		charmValue.setText(
				new Integer( 
						viewStateInfo.getLinguisticAttributeCrispValue(LinguisticAttributes.Charm)).toString());
	}
}

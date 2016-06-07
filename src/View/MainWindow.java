package View;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JTextField;

import Controller.Controller;
import Model.DefuzzyficationMethod;
import Model.LinguisticAttributes;
import Model.Model;
import Model.CrispValues.ConstantCrispValuesProvider;
import Model.CrispValues.CrispValue;
import Model.CrispValues.CrispValuesDatabase;
import Model.CrispValues.ICrispValuesProvider;
import Model.FuzzySets.FoodQualityLinguisticValues;
import sun.swing.JLightweightFrame;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;

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
	private JTable fuzzySetsValuesTable;
	private JPanel panel;
	private JTable rulesTable;
	private JPanel panel_2;
	private JPanel panel_3;
	private JLabel lblDefuzyfikator;
	private JTextField tipValueField;
	
	private Map<DefuzzyficationMethod, JRadioButton> defuzzyficationMethodsButtons = new HashMap<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Model model = new Model();
					model.setCrispInputs(new CrispValue(0.44f), new CrispValue(0.44f), new CrispValue(0.11f));
					ViewStateInfo stateInfo = new ViewStateInfo(model);
					stateInfo.setInputFuzzySets(model.getInputFuzzySets());
					Controller controller = new Controller(model);
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
		frame.setBounds(100, 100, 831, 741);
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
		
		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Wartości atrybutów", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(5, 333, 310, 241);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(7, 17, 300, 217);
		panel_2.add(panel);
		panel.setLayout(new BorderLayout());
		
		fuzzySetsValuesTable = new JTable();
		panel.add(fuzzySetsValuesTable, BorderLayout.CENTER);
		panel.add(fuzzySetsValuesTable.getTableHeader(), BorderLayout.NORTH);
		fuzzySetsValuesTable.setModel( viewStateInfo.getFuzzySetsValuesTableModel());
		
		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Wyniki działania reguł", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(7, 575, 803, 126);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 17, 793, 104);
		panel_3.add(panel_1);
		panel_1.setLayout(new BorderLayout());
		
		rulesTable = new JTable();
		panel_1.add(rulesTable, BorderLayout.CENTER);
		rulesTable.setModel(viewStateInfo.getRulesTableModel());
		
		lblDefuzyfikator = new JLabel("Defuzyfikator");
		lblDefuzyfikator.setFont(new Font("Dialog", Font.BOLD, 18));
		lblDefuzyfikator.setBounds(333, 347, 150, 55);
		frame.getContentPane().add(lblDefuzyfikator);
		
		JRadioButton method1Rdbt = new JRadioButton("Metoda pierwszego maksimum");
		method1Rdbt.setBounds(323, 400, 251, 23);
		frame.getContentPane().add(method1Rdbt);
		
		JRadioButton method2Rdbt = new JRadioButton("Metoda ostatniego maksimum");
		method2Rdbt.setBounds(323, 425, 251, 23);
		frame.getContentPane().add(method2Rdbt);
		
		JRadioButton method3Rdbt = new JRadioButton("Metoda środka maksimum");
		method3Rdbt.setBounds(323, 450, 251, 23);
		frame.getContentPane().add(method3Rdbt);
		
		JRadioButton method4Rdbt = new JRadioButton("Metoda środka ciężkości");
		method4Rdbt.setBounds(323, 475, 251, 23);
		frame.getContentPane().add(method4Rdbt);
		
		defuzzyficationMethodsButtons.put(DefuzzyficationMethod.FirstMaximum, method1Rdbt);
		defuzzyficationMethodsButtons.put(DefuzzyficationMethod.LastMaximum, method2Rdbt);
		defuzzyficationMethodsButtons.put(DefuzzyficationMethod.CenterOfMaximum, method3Rdbt);
		defuzzyficationMethodsButtons.put(DefuzzyficationMethod.COG, method4Rdbt);
		
		for( Entry<DefuzzyficationMethod, JRadioButton> pair : defuzzyficationMethodsButtons.entrySet()){
			pair.getValue().addActionListener((l)->{
				controller.defuzzyficationMethodButtonWasClicked(pair.getKey());
			});
		}
		
		
		JLabel lblWartoWyjciowa = new JLabel("Wartość wyjściowa");
		lblWartoWyjciowa.setFont(new Font("Dialog", Font.BOLD, 19));
		lblWartoWyjciowa.setBounds(588, 380, 229, 69);
		frame.getContentPane().add(lblWartoWyjciowa);
		
		JLabel lblNapiwekWynosi = new JLabel("Napiwek wynosi");
		lblNapiwekWynosi.setBounds(588, 479, 115, 15);
		frame.getContentPane().add(lblNapiwekWynosi);
		
		tipValueField = new JTextField();
		tipValueField.setEditable(false);
		tipValueField.setFont(new Font("Dialog", Font.BOLD, 19));
		tipValueField.setText("17%");
		tipValueField.setBounds(718, 457, 60, 60);
		frame.getContentPane().add(tipValueField);
		tipValueField.setColumns(10);
		
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
		
		for( Entry<DefuzzyficationMethod, JRadioButton> pair : defuzzyficationMethodsButtons.entrySet()){
			if( viewStateInfo.getCurrentDefuzzyficationMethod() == pair.getKey()){
				pair.getValue().setSelected(true);
			} else {
				pair.getValue().setSelected(false);
			}
		}	
		
		tipValueField.setText(viewStateInfo.getCurrentTip()+"%");
	}
}

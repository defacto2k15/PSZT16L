package View;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JTextField;

import Controller.Controller;
import sun.swing.JLightweightFrame;

import javax.swing.JLabel;

public class MainWindow implements IView{

	private JFrame frame;
	private JTextField generationValue1;
	private JTextField generationValue2;
	private JLabel generationLabel1;
	private JSlider generationSlider2;
	private JLabel generationLabel2;
	private JSlider generationSlider1;
	private ViewStateInfo viewStateInfo;

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
		frame.setBounds(100, 100, 450, 300);
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
		generationLabel1.setBounds(12, 45, 121, 15);
		frame.getContentPane().add(generationLabel1);
		
		generationSlider2 = new JSlider();
		generationSlider2.setBounds(12, 107, 150, 16);
		frame.getContentPane().add(generationSlider2);
		
		generationValue2 = new JTextField();
		generationValue2.setColumns(10);
		generationValue2.setBounds(169, 104, 43, 19);
		frame.getContentPane().add(generationValue2);
		
		generationLabel2 = new JLabel("New label");
		generationLabel2.setBounds(12, 90, 121, 15);
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
	}
}

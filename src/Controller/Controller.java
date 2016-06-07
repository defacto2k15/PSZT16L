package Controller;

import com.sun.glass.ui.View;

import Model.LinguisticAttributes;
import Model.Model;
import View.IView;
import View.ViewStateInfo;

public class Controller {
	ViewStateInfo stateInfo;
	IView view;
	private boolean shouldUpdateView = false;
	private Model model;
	
	public Controller(Model model) {
		this.model = model;
	}

	public void generationSlider1Change(int val) {
		stateInfo.getActiveDistribution().setValue1(val);
		updateView();
	}

	public void generationSlider2Change(int val) {
		stateInfo.getActiveDistribution().setValue2(val);
		updateView();
	}

	public void setStateInfo(ViewStateInfo stateInfo) {
		this.stateInfo = stateInfo;
	}

	public void setView(IView view) {
		this.view = view;
	}

	public void generatorComboBoxChanged(int selectedIndex) {
		stateInfo.setActiveDistribution(selectedIndex);
		updateView();
	}
	
	public void attributeValueSliderChanged( LinguisticAttributes attribute, int newValue){
		stateInfo.setLinguisticAttributeCrispValue(attribute, newValue);
	}
	
	
	public void setShouldUpdateView(boolean shouldUpdateView) {
		this.shouldUpdateView = shouldUpdateView;
	}

	private void updateView(){
		if( shouldUpdateView  ){
			view.update();
		}
	}
}

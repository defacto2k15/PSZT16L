package Controller;

import com.sun.glass.ui.View;

import View.IView;
import View.ViewStateInfo;

public class Controller {
	ViewStateInfo stateInfo;
	IView view;
	
	public void generationSlider1Change(int val) {
		stateInfo.getActiveDistribution().setValue1(val);
		view.update();
	}

	public void generationSlider2Change(int val) {
		stateInfo.getActiveDistribution().setValue2(val);
		view.update();
	}

	public void setStateInfo(ViewStateInfo stateInfo) {
		this.stateInfo = stateInfo;
	}

	public void setView(IView view) {
		this.view = view;
	}

	public void generatorComboBoxChanged(int selectedIndex) {
		stateInfo.setActiveDistribution(selectedIndex);
		view.update();
	}
}

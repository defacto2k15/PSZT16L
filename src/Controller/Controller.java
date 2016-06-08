package Controller;

import java.util.List;
import java.util.function.Supplier;

import com.sun.glass.ui.View;

import Model.DefuzzyficationMethod;
import Model.LinguisticAttributes;
import Model.Model;
import Model.CrispValues.CrispValue;
import Model.FuzzySets.InputFuzzySet;
import Model.LineralFunctions.Diagram;
import View.IView;
import View.ViewStateInfo;

public class Controller {
	ViewStateInfo stateInfo;
	IView view;
	private boolean shouldUpdateView = false;
	private Model model;
	private Supplier<Diagram> diagramProducerMethod;
	
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
		try {
			model.setCrispInputs(
					new CrispValue (stateInfo.getLinguisticAttributeCrispValue(LinguisticAttributes.Charm)),
					new CrispValue (stateInfo.getLinguisticAttributeCrispValue(LinguisticAttributes.FoodQuality)),
					new CrispValue (stateInfo.getLinguisticAttributeCrispValue(LinguisticAttributes.ServiceQuality)));
		} catch (Exception e) {
			System.err.println("E13");
			e.printStackTrace();
		}
		model.regenerate();
		updateView();
	}
	
	
	public void setShouldUpdateView(boolean shouldUpdateView) {
		this.shouldUpdateView = shouldUpdateView;
	}
	
	public void defuzzyficationMethodButtonWasClicked(DefuzzyficationMethod method) {
		stateInfo.setCurrentDefuzzyficationMethod(method);
		model.setDefuzzyficationMethod(method);
		model.regenerate();
		try {
			stateInfo.setCurrentTip((int)model.getTipPercent().getPercent());
		} catch (Exception e) {
			System.err.println("E34");
			e.printStackTrace();
		}
		diagramProducerMethod = ()->{ return model.getDefuzyficationDiagram(); };
		updateView();
	}
	


	private void updateView(){
		try {
			stateInfo.setCurrentTip((int)model.getTipPercent().getPercent());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if( diagramProducerMethod != null ){
			stateInfo.setCurrentDiagram(diagramProducerMethod.get());
		}
		if( shouldUpdateView  ){
			view.update();
		}
	}

	public void generateButtonClicked(int expected, int distribution) {
		try {
			if( stateInfo.getActiveDistributionIndex() == 0){
				model.generateCrispValuesUsingGaussianDistribution(new CrispValue(expected), (float)distribution);
			} else {
				model.generateCrispValuesUsingUniformDistribution(new CrispValue(1), new CrispValue(99));
			}
		} catch (Exception e) {
			System.err.println("E15");
			e.printStackTrace();
		}
		
		model.regenerate();
		
		stateInfo.setLinguisticAttributeCrispValue(
				LinguisticAttributes.Charm, (int)model.getCharmCrispValue().getValue());
		stateInfo.setLinguisticAttributeCrispValue(
				LinguisticAttributes.FoodQuality, (int)model.getFoodQualityCrispValue().getValue());
		stateInfo.setLinguisticAttributeCrispValue(
				LinguisticAttributes.ServiceQuality, (int)model.getServiceQualityCrispValue().getValue());
		try {
			stateInfo.setCurrentTip((int)model.getTipPercent().getPercent());
		} catch (Exception e) {
			System.err.println("E33");
			e.printStackTrace();
		}

		updateView();
	}

	public void fuzzySetsValuesTableRowIsSelected(int selectedRow) {
		List<InputFuzzySet> inputSets = model.getInputFuzzySets();
		diagramProducerMethod = ()->{ return model.getFuzzyficationDiagramForAttribute(inputSets.get(selectedRow).getLinguisticAttribute()); };
		updateView();
	}

	public void rulesTableRowIsSelected(int selectedRow) {
		if( selectedRow == -1 ){
			System.out.println("M101 strange");
		} else {
			diagramProducerMethod = ()->{ return model.getRules().get(selectedRow).getDiagram();  };
			updateView();
		}
	}
}

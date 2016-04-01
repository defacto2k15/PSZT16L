package Model;

import java.util.List;

import Model.FuzzySets.InputFuzzySet;
import Model.FuzzySets.TipFuzzySet;
import Model.LineralFunctions.Diagram;
import Model.Rules.Rule;

public interface IModel {
	// CrispInput to jest ta wartość bezwzględna atrybutu
	void setCrispInputs(CrispValue crispCharm, CrispValue crispFoodQuality, CrispValue crispServiceQuality);
	void generateCrispValuesUsingGaussianDistribution( CrispValue expectedValue /*wartosc oczekiwana*/, float standardDeviation);
	void generateCrispValuesUsingUniformDistribution( CrispValue min, CrispValue max);
	
	CrispValue getCharmCrispValue();
	CrispValue getFoodQualityCrispValue();
	CrispValue getServiceQualityCrispValue();
	
	Diagram getFuzzyficationDiagramForAttribute( LinguisticAttributes attribute );
	
	List<InputFuzzySet> getInputFuzzySets();
	
	List<Rule> getRules();
	
	void setDefuzzyficationMethod( DefuzzyficationMethod method);
	Diagram getDefuzyficationDiagram();
	
	TipPercentage getTipPercent();
	
	void regenerate();
	
}

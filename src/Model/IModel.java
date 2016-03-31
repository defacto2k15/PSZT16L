package Model;

public interface IModel {
	// CrispInput to jest ta wartość bezwzględna atrybutu
	void setCrispInputs(CrispValue crispCharm, CrispValue crispFoodQuality, CrispValue crispServiceQuality);
	CrispValue getCrispValue( LiteralAttributes attribute);
	
	void generateCrispValuesUsingGaussianDistribution( CrispValue expectedValue /*wartosc oczekiwana*/, float standardDeviation);
	void generateCrispValuesUsingUniformDistribution( CrispValue min, CrispValue max);
	
	void setFuzzyficationFunction();
	
	
	void setDefuzzyficationMethod( DefuzzyficationMethod method);
	
	void calculate(); // przelicza napiwek
	TipPercentage getTipPercent();
	
}

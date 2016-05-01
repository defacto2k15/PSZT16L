package tests;
import org.junit.Assert;
import org.junit.Test;

import Model.CrispValue;
import Model.Model;
import junit.*;

public class ModelTests {
	Model model = new Model();
	
	@Test
	public void canSetAndGetCrispValues() throws Exception{

		model.setCrispInputs(new CrispValue(30), new CrispValue(40), new CrispValue(50));
		
		Assert.assertEquals(model.getCharmCrispValue(), new CrispValue(30));
		Assert.assertEquals(model.getFoodQualityCrispValue(), new CrispValue(40));
		Assert.assertEquals(model.getServiceQualityCrispValue(), new CrispValue(50));
	}
}

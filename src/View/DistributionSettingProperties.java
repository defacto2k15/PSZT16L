package View;

public class DistributionSettingProperties {
	private String title;
	private String value1Title;
	private String value2Title;
	private int value1;
	private int value2;

	public DistributionSettingProperties(String title, String value1Title, String value2Title) {
		this.title = title;
		this.value1Title = value1Title;
		this.value2Title = value2Title;
	}

	public String getTitle() {
		return title;
	}

	public String getValue1Title() {
		return value1Title;
	}

	public String getValue2Title() {
		return value2Title;
	}
	
	boolean isFirstSliderActive(){
		return value1Title != null;
	}
	
	boolean isSecondSliderActive(){
		return value2Title != null;
	}
	
	public static DistributionSettingProperties getGaussDistributionSettings(){
		return new DistributionSettingProperties("Rozkład Faussa", "Wartość oczekiwana", "Wariancja");
	}
	
	public static DistributionSettingProperties getUniformDistributionSettings(){
		return new DistributionSettingProperties("Rozkład jednorodny", null, null);
	}

	public int getValue1() {
		return value1;
	}

	public void setValue1(int value1) {
		this.value1 = value1;
	}

	public int getValue2() {
		return value2;
	}

	public void setValue2(int value2) {
		this.value2 = value2;
	}
}

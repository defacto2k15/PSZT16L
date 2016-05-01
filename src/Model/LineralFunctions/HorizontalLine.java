package Model.LineralFunctions;

public class HorizontalLine {
	private float yPos;
	private String lineDescription;
	
	public HorizontalLine(float yPos, String lineDescription) {
		super();
		this.yPos = yPos;
		this.lineDescription = lineDescription;
	}

	public float getyPos() {
		return yPos;
	}

	public String getLineDescription() {
		return lineDescription;
	}
	
	
}

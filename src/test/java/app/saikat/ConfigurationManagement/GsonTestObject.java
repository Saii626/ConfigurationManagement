package app.saikat.ConfigurationManagement;

public class GsonTestObject {

	private String t1;
	private int t2;

	public String getT1() {
		return t1;
	}

	public int getT2() {
		return t2;
	}

	public GsonTestObject() {
		this.t1 = "Saikat";
		this.t2 = 10;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GsonTestObject) {
			GsonTestObject testObj = (GsonTestObject) obj;

			return testObj.t1.equals(t1) && testObj.t2 == t2;
		}

		return false;
	}
}
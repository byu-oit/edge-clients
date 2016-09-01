package edu.byu.edge.coreIdentity.domain;

/**
 * Created by Scott Hutchings on 8/30/2016.
 */
public class AddressBlock {
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String addressLine4;
	private String addressLine5;

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getAddressLine4() {
		return addressLine4;
	}

	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
	}

	public String getAddressLine5() {
		return addressLine5;
	}

	public void setAddressLine5(String addressLine5) {
		this.addressLine5 = addressLine5;
	}

	public String toString() {
		String result = addressLine1;
		if (addressLine2 != null && !addressLine2.isEmpty()) {
			result += "\n" + addressLine2;
		}
		if (addressLine3 != null && !addressLine3.isEmpty()) {
			result += "\n" + addressLine3;
		}
		if (addressLine4 != null && !addressLine4.isEmpty()) {
			result += "\n" + addressLine4;
		}
		if (addressLine5 != null && !addressLine5.isEmpty()) {
			result += "\n" + addressLine5;
		}
		return result;
	}
}

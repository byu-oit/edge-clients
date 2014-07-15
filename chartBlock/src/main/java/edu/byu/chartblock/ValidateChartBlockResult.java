package edu.byu.chartblock;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 7/2/2014
 * Time: 4:50 PM
 */
public class ValidateChartBlockResult {

	private boolean successful;
	private Map<String, String> fields;

	public ValidateChartBlockResult() {

	}

	public ValidateChartBlockResult(boolean b, Map<String, String> o) {
		this.successful = b;
		this.fields = o;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public Map<String, String> getFields() {
		return fields;
	}

	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}
}

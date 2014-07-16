package edu.byu.chartblock;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 7/2/2014
 * Time: 2:12 PM
 */
public interface AccountCodeValidation {
	/**
	 * Validate the account code
	 *
	 * @param accountCode Format must be XXXXXXXX-XXXX-XXXXX
	 */
	ValidateChartBlockResult getAccount(String accountCode);
}
